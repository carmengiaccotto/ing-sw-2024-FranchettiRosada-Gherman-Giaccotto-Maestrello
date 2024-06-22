package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ClientListener extends Thread {
    private ClientController clientController;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private NumRequiredPlayersResponse numRequiredPlayersResponse;
    private final Object numRequiredPlayersResponseLockObject = new Object();
    private CheckUniqueNickNameResponse checkUniqueNickNameResponse;
    private final Object checkUniqueNickNameResponseLockObject = new Object();
    private DisplayAvailableGamesResponse displayAvailableGamesResponse;
    private final Object displayAvailableGamesResponseLockObject = new Object();
    public GetAvailableColorsResponse getAvailableColorsResponse;
    private final Object getAvailableColorsResponseLockObject = new Object();


    public ClientListener(ObjectOutputStream oos, ObjectInputStream ois, ClientController clientController) {
        this.outputStream = oos;
        this.inputStream = ois;
        this.clientController = clientController;
    }

    private void sendMessage(GenericMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public void run() {
        try {
            while (true) {
                Object message = inputStream.readObject();
                if (message instanceof ChoosePawnColorMessage) {
                    clientController.ChoosePawnColor();
                } else if (message instanceof NumRequiredPlayersResponse) {
                    synchronized (numRequiredPlayersResponseLockObject) {
                        numRequiredPlayersResponse = (NumRequiredPlayersResponse) message;
                        numRequiredPlayersResponseLockObject.notify();
                    }
                } else if (message instanceof CheckUniqueNickNameResponse) {
                     synchronized (checkUniqueNickNameResponseLockObject){
                        checkUniqueNickNameResponse = (CheckUniqueNickNameResponse) message;
                         checkUniqueNickNameResponseLockObject.notify();
                    }
                } else if (message instanceof DisplayAvailableGamesResponse) {
                    synchronized (displayAvailableGamesResponseLockObject) {
                        displayAvailableGamesResponse = (DisplayAvailableGamesResponse) message;
                        displayAvailableGamesResponseLockObject.notify();
                    }
                }
                else if (message instanceof GetAvailableColorsResponse) {
                    synchronized (getAvailableColorsResponseLockObject) {
                        getAvailableColorsResponse = (GetAvailableColorsResponse) message;
                        getAvailableColorsResponseLockObject.notify();
                    }
                }
                else if (message instanceof UpdateMessage) {
                    clientController.sendUpdateMessage(((UpdateMessage) message).getMessage());
                }
                else if (message instanceof GetNickNameMessage) {
                    String nickname = clientController.getNickname();
                    sendMessage(new GetNickNameResponse(nickname));
                } else if(message instanceof WhatDoIDoNowMessage) {
                    clientController.WhatDoIDoNow(((WhatDoIDoNowMessage)message).getDoThis());
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public NumRequiredPlayersResponse getNumRequiredPlayersResponse() {
        synchronized (numRequiredPlayersResponseLockObject){
        try {
            numRequiredPlayersResponseLockObject.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        return numRequiredPlayersResponse;
    }

    public CheckUniqueNickNameResponse getCheckUniqueNickNameResponse() {
        synchronized (checkUniqueNickNameResponseLockObject) {
            try {
                checkUniqueNickNameResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return checkUniqueNickNameResponse;
    }

    public DisplayAvailableGamesResponse getDisplayAvailableGamesResponse() {
        synchronized (displayAvailableGamesResponseLockObject) {
        try {
            displayAvailableGamesResponseLockObject.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        return displayAvailableGamesResponse;
    }

    public GetAvailableColorsResponse getAvailableColorsResponse(){
        synchronized (getAvailableColorsResponseLockObject){
            try {
                getAvailableColorsResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getAvailableColorsResponse;
    }
        }

package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
    public ExtractInitialCardResponse initialCardResponse;
    private final Object initialCardResponseLockObject = new Object();
    public GetPlayersResponse getPlayerResponse;
    private final Object getPlayerResponseLockObject = new Object();
    public GetModelResponse getModelResponse;
    private final Object getModelresponseLockObject = new Object();
    private CreateGameResponse createGameResponse;
    private final Object createGameResponseLockObject = new Object();


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
                GenericMessage message = (GenericMessage) inputStream.readObject();
                Thread thread = new Thread(() -> {
                    if (message instanceof ChoosePawnColorMessage) {
                        try {
                            clientController.ChoosePawnColor();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof NumRequiredPlayersResponse) {
                        synchronized (numRequiredPlayersResponseLockObject) {
                            numRequiredPlayersResponse = (NumRequiredPlayersResponse) message;
                            numRequiredPlayersResponseLockObject.notify();
                        }
                    } else if (message instanceof CheckUniqueNickNameResponse) {
                        synchronized (checkUniqueNickNameResponseLockObject) {
                            checkUniqueNickNameResponse = (CheckUniqueNickNameResponse) message;
                            checkUniqueNickNameResponseLockObject.notify();
                        }
                    } else if (message instanceof DisplayAvailableGamesResponse) {
                        synchronized (displayAvailableGamesResponseLockObject) {
                            displayAvailableGamesResponse = (DisplayAvailableGamesResponse) message;
                            displayAvailableGamesResponseLockObject.notify();
                        }
                    } else if (message instanceof GetAvailableColorsResponse) {
                        synchronized (getAvailableColorsResponseLockObject) {
                            getAvailableColorsResponse = (GetAvailableColorsResponse) message;
                            getAvailableColorsResponseLockObject.notify();
                        }
                    } else if (message instanceof UpdateMessage) {
                        try {
                            clientController.sendUpdateMessage(((UpdateMessage) message).getMessage());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetNickNameMessage) {
                        try {
                            String nickname = clientController.getNickname();
                            sendMessage(new GetNickNameResponse(nickname));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(message instanceof GetPlayerMessage) {
                        try {
                            Player player = clientController.getPlayer();
                            sendMessage(new GetPlayerResponse(player));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof WhatDoIDoNowMessage) {
                        try {
                            clientController.WhatDoIDoNow(((WhatDoIDoNowMessage) message).getDoThis());
                            sendMessage(new WhatDoIDoNowResponse());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetPlayersResponse) {
                        synchronized (getPlayerResponseLockObject) {
                            getPlayerResponse = (GetPlayersResponse) message;
                            getPlayerResponseLockObject.notify();
                        }
                    } else if (message instanceof ExtractInitialCardResponse) {
                        synchronized (initialCardResponseLockObject) {
                            initialCardResponse = (ExtractInitialCardResponse) message;
                            initialCardResponseLockObject.notify();
                        }
                    } else if (message instanceof CreateGameResponse) {
                        synchronized (createGameResponseLockObject) {
                            createGameResponse = (CreateGameResponse) message;
                            createGameResponseLockObject.notify();
                        }
                    } else if (message instanceof GetModelResponse) {
                        synchronized (getModelresponseLockObject) {
                            getModelResponse = (GetModelResponse) message;
                            getModelresponseLockObject.notify();
                        }
                    }
                });
                thread.start();
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

    public ExtractInitialCardResponse getInitialCardResponse(){
        synchronized (initialCardResponseLockObject){
            try {
                initialCardResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return initialCardResponse;
    }

    public GetPlayersResponse getPlayersResponse(){
        synchronized (getPlayerResponseLockObject){
            try {
                getPlayerResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getPlayerResponse;
    }

    public GetModelResponse getModelResponse(){
        synchronized (getModelresponseLockObject){
            try {
                getModelresponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getModelResponse;
    }

    public CreateGameResponse createGameResponse() {
        synchronized (createGameResponseLockObject) {
            try {
                createGameResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return createGameResponse;
    }
}

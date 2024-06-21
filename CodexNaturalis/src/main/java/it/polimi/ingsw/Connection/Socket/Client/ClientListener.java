package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

class ClientListener extends Thread {
    private Socket socket;
    private ClientController clientController;
    private ObjectInputStream inputStream;
    private NumRequiredPlayersResponse numRequiredPlayersResponse;
    private CheckUniqueNickNameResponse checkUniqueNickNameResponse;
    private DisplayAvailableGamesResponse displayAvailableGamesResponse;

    public ClientListener(Socket socket, ClientController clientController) {
        this.socket = socket;
        this.clientController = clientController;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Object message = inputStream.readObject();
                if (message instanceof ChoosePawnColorMessage) {
                    clientController.ChoosePawnColor();
                } else if (message instanceof NumRequiredPlayersResponse) {
                    synchronized (this) {
                        numRequiredPlayersResponse = (NumRequiredPlayersResponse) message;
                        numRequiredPlayersResponse.notify();
                    }
                } else if (message instanceof CheckUniqueNickNameMessage) {
                    synchronized (this) {
                        checkUniqueNickNameResponse = (CheckUniqueNickNameResponse) message;
                        checkUniqueNickNameResponse.notify();
                    }
                } else if (message instanceof DisplayAvailableGamesResponse) {
                    synchronized (this) {
                        displayAvailableGamesResponse = (DisplayAvailableGamesResponse) message;
                        displayAvailableGamesResponse.notify();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized NumRequiredPlayersResponse getNumRequiredPlayersResponse() {
        try {
            numRequiredPlayersResponse.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return numRequiredPlayersResponse;
    }

    public CheckUniqueNickNameResponse getCheckUniqueNickNameResponse() {
        try {
            checkUniqueNickNameResponse.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return checkUniqueNickNameResponse;
    }

    public DisplayAvailableGamesResponse getDisplayAvailableGamesResponse() {
        try {
            displayAvailableGamesResponse.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return displayAvailableGamesResponse;
    }
}

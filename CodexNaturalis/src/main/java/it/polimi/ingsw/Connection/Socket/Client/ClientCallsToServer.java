package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class ClientCallsToServer implements MainControllerInterface {
    private final ClientControllerInterface clientController;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ClientListener listener;

    public ClientCallsToServer(Socket socket, ClientControllerInterface clientController) {
        this.clientController = clientController;
    }

    public void connect(){
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            //System.out.println(socket.getInputStream());
            this.ois= new ObjectInputStream(socket.getInputStream());

            listener = new ClientListener(socket, (ClientController) clientController);
            listener.start();

            clientController.connect();

//            Thread thread = new Thread(serverHandler);
//            thread.start();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendMessage(GenericMessage message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        try {
            sendMessage(new NumRequiredPlayersMessage());
            NumRequiredPlayersResponse response = listener.getNumRequiredPlayersResponse();
            return response.getPlayers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param client
     * @throws RemoteException
     */
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new ConnectMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param name
     * @return
     * @throws RemoteException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException, IOException, ClassNotFoundException {
        sendMessage(new CheckUniqueNickNameMessage(name));
        CheckUniqueNickNameResponse response = listener.getCheckUniqueNickNameResponse();
        return response.getIsUnique();
    }

    /**
     * @param client
     * @param GameID
     */
    @Override
    public void joinGame(ClientControllerInterface client, int GameID) {
        try {
            sendMessage(new JoinGameMessage(GameID));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<Integer, ArrayList<String>> DisplayAvailableGames() throws RemoteException {
        try {
            sendMessage(new DisplayAvailableGamesMessage());
            DisplayAvailableGamesResponse response = listener.getDisplayAvailableGamesResponse();
            return response.getAvailableGames();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param client
     * @param maxNumberOfPlayers
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface createGame(ClientControllerInterface client, int maxNumberOfPlayers) throws RemoteException {
        try {
            sendMessage(new CreateGameMessage(maxNumberOfPlayers));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return  null;
    }

    /**
     * @param game
     * @param client
     * @throws RemoteException
     */
    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new NotifyGamePlayerJoinedMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param name
     * @throws RemoteException
     */
    @Override
    public void addNickname(String name) throws RemoteException {
        try {
            sendMessage(new AddNicknameMessage(name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

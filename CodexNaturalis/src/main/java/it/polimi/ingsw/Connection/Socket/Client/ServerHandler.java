package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.GenericMessage;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Game.GameListener;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ServerHandler implements Runnable, MainControllerInterface, GameControllerInterface {

    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientControllerInterface clientController;

    public ServerHandler(Socket s) throws IOException {
        this.server = s;
        this.out = new ObjectOutputStream(server.getOutputStream());
        this.in = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public void run() {
        String s;
        GenericMessage message;
        while(true) {
            try {
                GenericMessage messagge = (GenericMessage) in.readObject();

                switch (messagge.getMessage()) {
                    case "ChooseNickname":
                        s = clientController.ChooseNickname();
                        message = new GenericMessage("ChooseNickname");
                        message.setObject(s);
                        sendMessage(message);
                        break;
                    case "SendUpdateMessage":
                        String m = (String) messagge.getObject();
                        clientController.sendUpdateMessage(m);
                        break;
                    case "GetNickname":
                        s = clientController.getNickname();
                        message = new GenericMessage("GetNickname");
                        message.setObject(s);
                        sendMessage(message);
                        break;
                    case "SetNickname":
                        String l = (String) messagge.getObject();
                        clientController.setNickname(l);
                        break;
                    case "JoinOrCreateGame":
                        clientController.JoinOrCreateGame();
                        break;
                    case "GetGameToJoin":
                        //ArrayList<GameControllerInterface> availableGames = (ArrayList<GameControllerInterface>) messagge.getObject();
                        //clientController.getGameToJoin((ArrayList<GameControllerInterface>) messagge.getObject());
                        break;
                    case "SetGame" :
                        GameControllerInterface game = (GameControllerInterface) messagge.getObject();
                        clientController.setGame(game);
                        break;

                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void setClientController(ClientControllerInterface clientController){this.clientController = clientController;}

    public void sendMessage(GenericMessage m) throws IOException {
        out.writeObject(m);
        out.flush();
    }

    @Override
    public GameListener getListener() throws RemoteException {
        return null;
    }

    @Override
    public int getNumPlayers() throws RemoteException {
        return 0;
    }

    @Override
    public void addPlayer(ClientControllerInterface client) throws RemoteException {

    }

    @Override
    public GameStatus getStatus() throws RemoteException {
        return null;
    }

    @Override
    public void setStatus(GameStatus status) throws RemoteException {

    }

    @Override
    public void CheckMaxNumPlayerReached() throws RemoteException {

    }

    @Override
    public List<PawnColor> AvailableColors() throws RemoteException {
        return null;
    }


    @Override
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {

    }

    @Override
    public int getId() throws RemoteException {
        return 0;
    }

    @Override
    public void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException{

    }

    @Override
    public PlayGround getModel() throws RemoteException {
        return null;
    }

    @Override
    public void setModel(PlayGround model) throws RemoteException {

    }

    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {

    }

    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {

    }

    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException, IOException, ClassNotFoundException {
        return false;
    }


    @Override
    public GameControllerInterface joinGame(ClientControllerInterface client, int GameID) {
        GenericMessage message = new GenericMessage("JoinGame");
        message.setObject(GameID);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<GameControllerInterface> DisplayAvailableGames() throws RemoteException {
        return null;
    }


    @Override
    public GameControllerInterface createGame(ClientControllerInterface client, int n) {
        GenericMessage message = new GenericMessage("CreateGame");
        message.setObject(n);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {

    }

    @Override
    public HashSet<String> getNicknames() throws RemoteException {
        return null;
    }

    @Override
    public void addNickname(String name) throws RemoteException {

    }

}

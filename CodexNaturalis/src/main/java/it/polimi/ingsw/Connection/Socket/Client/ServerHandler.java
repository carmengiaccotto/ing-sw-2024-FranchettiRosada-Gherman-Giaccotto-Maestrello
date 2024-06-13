package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.GenericMessage;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Game.GameListener;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        try {
            clientController.setServer(this);
            clientController.setGame(this);
            clientController.connect();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
        GenericMessage message = new GenericMessage("NotifyNewPlayerJoined");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException {
        return null;
    }

    @Override
    public InitialCard extractInitialCard() throws RemoteException {
        return null;
    }

    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {
        GenericMessage message = new GenericMessage("RemoveAvailableColor");
        message.setObject(color);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        GenericMessage message = new GenericMessage("NumRequiredPlayers");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            GenericMessage response = (GenericMessage) in.readObject();
            ArrayList<Pair<Integer, Integer>> num = (ArrayList<Pair<Integer, Integer>>) response.getObject();
            return num;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        GenericMessage message = new GenericMessage("Connect");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException{
        boolean ok = false;
        GenericMessage message = new GenericMessage("CheckUniqueNickName");
        message.setObject(name);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            GenericMessage receiveMessage = (GenericMessage) in.readObject();
            if (receiveMessage.getMessage().equals("CheckUniqueNickName2")){
                ok = (boolean) receiveMessage.getObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ok;
    }


    @Override
    public void joinGame(ClientControllerInterface client, int GameID) {
        GenericMessage message = new GenericMessage("JoinGame");
        message.setObject(GameID);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, ArrayList<String>> DisplayAvailableGames() throws RemoteException {
        GenericMessage message = new GenericMessage("DisplayAvailableGames");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GenericMessage receiveMessage = null;
        try {
            receiveMessage = (GenericMessage) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, ArrayList<String>> games = (Map<Integer, ArrayList<String>>) receiveMessage.getObject();
        return games;
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
        GenericMessage message = new GenericMessage("NotifyGamePlayerJoined");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GenericMessage mess = null;
        try {
            mess = (GenericMessage) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String m = (String) mess.getObject();
        clientController.sendUpdateMessage(m);
    }



    @Override
    public void addNickname(String name) throws RemoteException {
        GenericMessage message = new GenericMessage("AddNickname");
        message.setObject(name);
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PawnColor> getAvailableColors() throws RemoteException{
        List<PawnColor> color = null;
        GenericMessage message = new GenericMessage("GetAvailableColors");
        try {
            sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            GenericMessage response = (GenericMessage) in.readObject();
            color = (List<PawnColor>) response.getObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return color;

    }
}

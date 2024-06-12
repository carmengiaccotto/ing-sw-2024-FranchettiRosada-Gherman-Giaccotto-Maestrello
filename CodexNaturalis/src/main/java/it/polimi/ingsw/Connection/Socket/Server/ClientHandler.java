package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.GenericMessage;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClientHandler implements Runnable, ClientControllerInterface, Serializable {

    private transient Socket clientSocket;
    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    //GameController associato al gioco
    private GameControllerInterface gameController;
    private MainControllerInterface mainController;


    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
    }

@Override
    public void run() {
    try {
        while (true) {
            try {
                GenericMessage message = (GenericMessage) in.readObject();

                switch (message.getMessage()) {
                    case "Connect":
                        mainController.connect(this);
                        break;
                    case "CheckUniqueNickName":
                        String name = (String) message.getObject();
                        Boolean ok = mainController.checkUniqueNickName(name);
                        GenericMessage m = new GenericMessage("CheckUniqueNickName2");
                        m.setObject(ok);
                        sendMessage(m);
                        break;
                    case "AddNickname":
                        String name2 = (String) message.getObject();
                        mainController.addNickname(name2);
                        break;
                    case "CreateGame":
                        int n = (int) message.getObject();
                        mainController.createGame(this, n);
                        break;
                    case "NotifyNewPlayerJoined":
                        gameController.NotifyNewPlayerJoined(this);
                        break;
                    case "DisplayAvailableGames":
                        Map<Integer, ArrayList<String>> gameAvailable = mainController.DisplayAvailableGames();
                        GenericMessage mes = new GenericMessage("DisplayAvailableGames");
                        mes.setObject(gameAvailable);
                        sendMessage(mes);
                        break;
                    case "JoinGame":
                        int ID = (int) message.getObject();
                        mainController.joinGame(this, ID);
                        break;
                    case "NumRequiredPlayers":
                        ArrayList<Pair<Integer, Integer>> num = mainController.numRequiredPlayers();
                        GenericMessage mess = new GenericMessage("NumRequiredPlayers");
                        mess.setObject(num);
                        sendMessage(mess);
                        break;
                    case "NotifyGamePlayerJoined":
                        mainController.NotifyGamePlayerJoined(this.gameController, this);
                        break;
                    case "GetAvailableColors":
                        List<PawnColor> colors = gameController.getAvailableColors();
                        GenericMessage r = new GenericMessage("GetAvailableColors");
                        r.setObject(colors);
                        sendMessage(r);
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    } catch (RuntimeException e) {
        throw new RuntimeException(e);
    }
}
    public void sendMessage(GenericMessage m) throws IOException {
        out.writeObject(m);
        out.flush();
    }

    public void setMainController(MainControllerInterface mainController){
        this.mainController = mainController;
    }

    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {

    }

    @Override
    public void setView(UserInterface view) throws RemoteException {

    }

    @Override
    public void disconnect() throws RemoteException {

    }

    @Override
    public void ChoosePawnColor() throws RemoteException {

    }

    @Override
    public void JoinGame() throws RemoteException {

    }

    @Override
    public String getNickname() throws RemoteException {
//        GenericMessage message = new GenericMessage("GetNickname");
//        try {
//            sendMessage(message);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        GenericMessage recived = null;
//        try {
//            recived = (GenericMessage) in.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        String name = (String) recived.getObject();
//        return name;
        return null;
    }

    @Override
    public void setNickname(String nickname) throws RemoteException {

    }

    @Override
    public void JoinOrCreateGame() throws RemoteException {

    }

    @Override
    public String ChooseNickname() throws IOException, ClassNotFoundException {
       return null;
    }

    @Override
    public void newGameSetUp() throws RemoteException {

    }

    @Override
    public void Wait() throws RemoteException {

    }

    @Override
    public PawnColor getPawnColor() throws RemoteException {
        return null;
    }

    @Override
    public void updatePlayers(List<ClientControllerInterface> players) throws RemoteException {

    }

    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {

    }

    @Override
    public Player getPlayer() throws RemoteException {
        return null;
    }

    @Override
    public void showBoardAndPlayAreas(PlayGround m) throws RemoteException {

    }

    @Override
    public PlayCard chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    @Override
    public SideOfCard chooseCardToPlay() throws RemoteException {
        return null;
    }

    @Override
    public void receiveCommand() throws RemoteException {

    }

    @Override
    public String chooseSideInitialCard(InitialCard c) throws RemoteException {
        return null;
    }

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) throws RemoteException {
        return 0;
    }

    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
        GenericMessage mess = new GenericMessage("SendUpdateMessage");
        mess.setObject(message);
        try {
            sendMessage(mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect() throws RemoteException {

    }

    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {

    }


    @Override
    public int getScore() throws RemoteException {
        return 0;
    }

    @Override
    public int getRound() throws RemoteException {
        return 0;
    }

    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        this.gameController = game;

    }

    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return null;
    }

    @Override
    public void JoinLobby() throws RemoteException {

    }
}

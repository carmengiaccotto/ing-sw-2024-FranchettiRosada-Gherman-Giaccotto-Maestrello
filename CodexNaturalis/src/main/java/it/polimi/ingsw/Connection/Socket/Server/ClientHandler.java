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
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Runnable, ClientControllerInterface {

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

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
                        GameControllerInterface c = mainController.createGame(this, n);
                        GenericMessage mess = new GenericMessage("CreateGame");
                        mess.setObject(c);
                        sendMessage(mess);
                        break;
                    case "NotifyGamePlayerJoined":
                        GameControllerInterface game = (GameControllerInterface) message.getObject();
                        mainController.NotifyGamePlayerJoined(game, this);
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

    }

    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return null;
    }

    @Override
    public void JoinLobby() throws RemoteException {

    }
}

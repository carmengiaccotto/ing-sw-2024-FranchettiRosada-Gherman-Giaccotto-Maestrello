package it.polimi.ingsw.Connection.Socket;

import it.polimi.ingsw.Connection.Socket.ClientToServerMessage.ChooseNickNameMessageServer;
import it.polimi.ingsw.Connection.Socket.ServerToClientMessage.ServerToClientMessage;
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

//Classe che risiede sul server e che è associata a ciscun client per gestire le sue richieste socket.
public class ClientHandler extends Thread implements ClientControllerInterface {

    //Socket associata al client
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //GameController associato al gioco
    private GameControllerInterface gameController;

    //MainController associato ai giochi
    private MainControllerInterface mainController;

    //Istanza di GameListener che contiene la lista dei giocatori da notificare
    //private GameListener gameListeners;

    //nickname associato alla socket client
    private String nick = null;

    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        //gameListeners = new GameListener ();
    }

    public void interruptThread() {
        this.interrupt();
    }

    public void run() {
        try {
            mainController.connect(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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
        ServerToClientMessage message = new ServerToClientMessage("ChooseNickname");
        //ServerToClientMessage message = new ChooseNickNameMessageServer("Lucrezia");
        out.writeObject(message);
        out.flush();
        ChooseNickNameMessageServer receivedMessage = (ChooseNickNameMessageServer) in.readObject();

        return receivedMessage.getNickname();
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

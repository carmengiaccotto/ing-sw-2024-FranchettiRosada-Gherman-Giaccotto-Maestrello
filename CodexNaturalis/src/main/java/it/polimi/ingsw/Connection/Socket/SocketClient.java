package it.polimi.ingsw.Connection.Socket;

import it.polimi.ingsw.Connection.Socket.ClientToServerMessage.ChooseNickNameMessageServer;
import it.polimi.ingsw.Connection.Socket.ClientToServerMessage.ClientToControllerMessage;
import it.polimi.ingsw.Connection.Socket.ClientToServerMessage.ClientToServerMessage;
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

public class SocketClient implements ClientControllerInterface {

    private Socket server;
    private ClientControllerInterface controller;
    //private Scanner i;
    //private PrintWriter o;
    //private String messaggio;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    //nickname del client
    private String nickname;

    private MainControllerInterface mainController;


    public void connect(){
        try {
            Socket server = new Socket(SocketServer.SERVERIP, SocketServer.SERVERPORT);
            System.out.println("Connected client");

            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());

            //leggo dal server se mi manda qualcosa
            ClientToServerMessage messagge = (ClientToServerMessage) in.readObject();

            //messaggio.execute

            switch(messagge.getMessage()){
                case "ChooseNickname":
                    String s = ChooseNickname();
                    ChooseNickNameMessageServer message = new ChooseNickNameMessageServer(s);
                    out.writeObject(message);
                    out.flush();

            }

            //in = new Scanner(server.getInputStream());
            //out = new PrintWriter(server.getOutputStream());

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void setController(ClientControllerInterface clientController){
        this.controller = clientController;
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

    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {

    }

    @Override
    public void setView(UserInterface view) throws RemoteException {
        controller.setView(view);
    }

    public void disconnect() {

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
        String name = controller.ChooseNickname();
        return name;
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

    public void sendMessage(ClientToControllerMessage m){


    }


//     public void receiveMessage(ControllerToClientMessages m){
//
//
//     }
}

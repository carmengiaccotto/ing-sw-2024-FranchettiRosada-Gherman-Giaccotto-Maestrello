package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.GenericMessage;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
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
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;


public class ClientHandler implements Runnable, ClientControllerInterface, Serializable {

    private transient Socket clientSocket;
    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;
    private GameControllerInterface gameController;
    private MainControllerInterface mainController;
    public String nickName;


    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
    }

    @Override
    public void run() {
        
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
        return this.nickName;
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
    public PlayGround chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
//        GenericMessage mess = new GenericMessage("SendUpdateMessage");
//        mess.setObject(message);
//        try {
//            sendMessage(mess);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
    public void setRound(int round) throws RemoteException {

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

    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {

    }

    private void listenToServer() throws IOException {
        String message;
        while ((message = in.readLine()) != null) {
            handleServerMessage(message);
        }
    }

    private void handleServerMessage(String message) {
    }
}

package it.polimi.ingsw.Connection.Socket.Client;


import it.polimi.ingsw.Connection.Socket.Server.SocketServer;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class SocketClient extends Thread implements ClientControllerInterface {
    private ClientController controller;

    public void connect(String ipAddress){
        try {
            Socket socket = new Socket(ipAddress, SocketServer.SERVERPORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ClientCallsToServer server = new ClientCallsToServer(oos, ois, controller);
            controller.setServer(server);
            controller.setGame(server);
            server.connect(ipAddress);

//            serverHandler = new ServerHandler(server);
//            serverHandler.setClientController(controller);
//            controller.setServer(serverHandler);
//            System.out.println("Connected client");
//
//            Thread thread = new Thread(serverHandler);
//            thread.start();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setController(ClientController clientController){
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
    public void setRound(int round) throws RemoteException {

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
    public Object WhatDoIDoNow(String doThis) throws RemoteException {

        return null;
    }

    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        controller.setServer(server);
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
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {

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

    }
}

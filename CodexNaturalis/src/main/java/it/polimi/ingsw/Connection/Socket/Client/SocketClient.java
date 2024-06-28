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

/**
 * This class represents a client in a socket-based connection.
 * It implements the ClientControllerInterface and extends the Thread class.
 * It is responsible for managing the connection to the server and handling the client-side game logic.
 */
public class SocketClient extends Thread implements ClientControllerInterface {
    /**
     * Instance of ClientController. This controller is responsible for managing the client-side logic of the game.
     * It is used to handle game events, manage game state, and interact with the server.
     * It is set via the setController method and used in various methods throughout the class.
     */
    private ClientController controller;

    /**
     * This method is used to establish a connection to the server.
     * It creates a new socket with the given IP address and the server port.
     * It also sets up the input and output streams for the socket.
     * Then, it creates a new ClientCallsToServer object and sets it as the server for the controller.
     * Finally, it calls the connect method on the server object.
     *
     * @param ipAddress The IP address of the server.
     */
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

    /**
     * This method is used to set the controller for this client.
     *
     * @param clientController The controller to be set.
     */
    public void setController(ClientController clientController){
        this.controller = clientController;
    }

    /**
     * This method is used to add a card to the client's hand.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param card The card to be added to the hand.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
    }

    /**
     * This method is used to get the score of the client.
     * The current implementation returns 0 and needs to be filled out.
     *
     * @return The score of the client.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public int getScore() throws RemoteException {
        return 0;
    }

    /**
     * This method is used to get the current round.
     * The current implementation returns 0 and needs to be filled out.
     *
     * @return The current round.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public int getRound() throws RemoteException {
        return 0;
    }

    /**
     * This method is used to set the current round.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param round The round to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setRound(int round) throws RemoteException {

    }

    /**
     * This method is used to set the game for this client.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param game The game to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {

    }

    /**
     * This method is used to get the personal objective card of the client.
     * The current implementation returns null and needs to be filled out.
     *
     * @return The personal objective card of the client.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return null;
    }

    /**
     * This method is used to join a lobby.
     * The implementation is currently empty and needs to be filled out.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void JoinLobby() throws RemoteException {

    }

    /**
     * This method is used to handle the next action the client should take.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param doThis The action the client should take.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public Object WhatDoIDoNow(String doThis) throws RemoteException {

        return null;
    }

    /**
     * This method is used to set the server for this client.
     *
     * @param server The server to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        controller.setServer(server);
    }

    /**
     * This method is used to set the view for this client.
     *
     * @param view The view to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setView(UserInterface view) throws RemoteException {
        controller.setView(view);
    }

    /**
     * This method is used to disconnect the client from the server.
     * The implementation is currently empty and needs to be filled out.
     */
    public void disconnect() {

    }

    /**
     * This method is used to choose the color of the pawn for the client.
     * The implementation is currently empty and needs to be filled out.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void ChoosePawnColor() throws RemoteException {

    }

    /**
     * This method is used to join a game.
     * The implementation is currently empty and needs to be filled out.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void JoinGame() throws RemoteException {

    }

    /**
     * This method is used to get the nickname of the client.
     * The current implementation returns null and needs to be filled out.
     * @return The nickname of the client.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    /**
     * This method is used to set the nickname for the client.
     * The implementation is currently empty and needs to be filled out.
     * @param nickname The nickname to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setNickname(String nickname) throws RemoteException {

    }

    /**
     * This method is used to join or create a game.
     * The implementation is currently empty and needs to be filled out.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void JoinOrCreateGame() throws RemoteException {

    }

    /**
     * This method is used to display the available colors for the pawns.
     * The implementation is currently empty and needs to be filled out.
     * @param availableColors The list of available colors.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {

    }

    /**
     * This method is used to choose a nickname for the client.
     * The current implementation returns null and needs to be filled out.
     * @return The chosen nickname.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    @Override
    public String ChooseNickname() throws IOException, ClassNotFoundException {
        return null;
    }

    /**
     * This method is used to set up a new game.
     * The implementation is currently empty and needs to be filled out.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void newGameSetUp() throws RemoteException {

    }

    /**
     * This method is used to make the client wait.
     * The implementation is currently empty and needs to be filled out.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void Wait() throws RemoteException {

    }

    /**
     * This method is used to get the color of the pawn for the client.
     * The current implementation returns null and needs to be filled out.
     * @return The color of the pawn.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public PawnColor getPawnColor() throws RemoteException {
        return null;
    }

    /**
     * This method is used to set the personal objective card for the client.
     * The implementation is currently empty and needs to be filled out.
     * @param objectiveCard The personal objective card to be set.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {

    }

    /**
     * This method is used to get the player object for the client.
     * The current implementation returns null and needs to be filled out.
     * @return The player object.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public Player getPlayer() throws RemoteException {
        return null;
    }

    /**
     * This method is used to display the board and play areas to the client.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param m The PlayGround object representing the board and play areas.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void showBoardAndPlayAreas(PlayGround m) throws RemoteException {

    }

    /**
     * This method is used to choose a card to draw from the board.
     * The current implementation returns null and needs to be filled out.
     *
     * @param m The PlayGround object representing the board and play areas.
     * @return The PlayGround object after the card has been drawn.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public PlayGround chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    /**
     * This method is used to send an update message to the client.
     * The implementation is currently empty and needs to be filled out.
     *
     * @param message The update message to be sent.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void sendUpdateMessage(String message) throws RemoteException {

    }
}


/**
 * This package contains the classes related to the RMI (Remote Method Invocation) connection.
 */

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes

import it.polimi.ingsw.Controller.Client.ClientController;
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
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The RMIClient class extends the UnicastRemoteObject and implements the ClientControllerInterface.
 * It represents a client in the RMI connection.
 */

public class RMIClient extends UnicastRemoteObject implements Serializable, ClientControllerInterface {
    private MainControllerInterface server;
    private ClientControllerInterface controller;
    private Registry registry;


    /**
     * The constructor for the RMIClient class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIClient() throws RemoteException {
        //this.server=server;
        this.controller=new ClientController();
    }

    /**
     * Method to set the controller for the client.
     * @param clientController The controller to be set.
     */
    public void setController(ClientControllerInterface clientController){
        this.controller = clientController;
    }

    /**
     * Method to set the server for the client.
     * @param server The server to be set.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        controller.setServer(server);
    }

    /**
     * Method that sets the view of the player to the chosen type. Uses ClientController implementation
     * @param view  GUI or TUI
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setView(UserInterface view) throws RemoteException {
        controller.setView(view);
    }

    /**
     * Method to disconnect the client.
     * @throws RemoteException if the remote object cannot be created
     */
    public void disconnect() throws RemoteException {

    }

    /**
     * Method that allows the player to choose its pawn color from a list of available ones. Uses ClientController implementation.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void ChoosePawnColor() throws RemoteException {
        controller.ChoosePawnColor();
    }


    /**
     * Method that allows the player to choose which game to join from a list of Games created by other players.
     * Uses ClientController Implementation
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void JoinGame() throws RemoteException {
        controller.JoinGame();

    }

    /**
     * Method to get the nickname of the player.
     * @return String representing the nickname of the player.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public String getNickname() throws RemoteException {
        return controller.getNickname();
    }

    /**
     * Method to set the nickname of the player.
     * @param nickname The nickname to be set.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setNickname(String nickname) throws RemoteException {
        controller.setNickname(nickname);
    }

    /**
     * Method that allows the player to join or create a game.
     * Uses ClientController Implementation
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void JoinOrCreateGame() throws RemoteException {
        controller.JoinOrCreateGame();

    }

    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {

    }

    /**
     * Method that allows the player to choose a nickname.
     * @return String representing the chosen nickname.
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @Override
    public String ChooseNickname() throws IOException, ClassNotFoundException {
        return controller.ChooseNickname();

    }

    /**
     * Method to set up a new game.
     * Uses ClientController Implementation
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void newGameSetUp() throws RemoteException {
        controller.newGameSetUp();

    }


    /**
     * Method to make the player wait.
     * Uses ClientController Implementation
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void Wait() throws RemoteException {
        controller.Wait();

    }

    /**
     * Method to get the color of the player's pawn.
     * @return PawnColor representing the color of the player's pawn.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public PawnColor getPawnColor() throws RemoteException {
        return controller.getPawnColor();
    }

    /**
     * Method to set the personal objective card of the player.
     * @param objectiveCard The objective card to be set.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {
        controller.setPersonalObjectiveCard(objectiveCard);

    }

    /**
     * Method to get the player.
     * @return Player representing the player.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public Player getPlayer() throws RemoteException {
        return controller.getPlayer();
    }

    /**
     * Method to show the board and play areas.
     * @param m The playground to be shown.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void showBoardAndPlayAreas(PlayGround m) throws RemoteException {

    }

    /**
     * Method that allows the player to choose a card to draw.
     * @param m The playground from which the card is to be drawn.
     * @return PlayGround representing the updated playground after the card is drawn.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public PlayGround chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    /**
     * Method to send an update message.
     * @param message The message to be sent.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
        controller.sendUpdateMessage(message);
    }

    /**
     * Method to connect to the server.
     * @param ipAddress The IP address of the server.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public  void connect(String ipAddress) throws RemoteException{
        boolean connected = false;
        int attempt = 1;

        while (!connected && attempt <= 4) {
            try {
                Thread connectionThread = new Thread(() -> {
                    // ...
                    try {
                        registry = LocateRegistry.getRegistry(ipAddress, 8346);
                        Object obj = registry.lookup("rmi://" + ipAddress + ":8344/CodexNaturalis");
                        Class<?>[] interfaces = obj.getClass().getInterfaces();
                        server = (MainControllerInterface) obj;
                        setServer(server);

                    } catch (Exception e) {
                        System.out.println("[ERROR] Connecting to server: \n\tClient exception: " + e + "\n");
                    }
                });
                connectionThread.start();
                connectionThread.join(1000);

                if (server != null) {
                    connected = true;
                    controller.setServer(server);
                    controller.connect(ipAddress); // Moved inside the if block
                } else {
                    System.out.println("[#" + attempt + "]Waiting to reconnect to Server on port: '" + 8346 + "' with name: '" + "CodexNaturalis" + "'");
                    attempt++;
                    if (attempt <= 4) {
                        System.out.println("Retrying...");
                        Thread.sleep(1000);
                    } else {
                        System.out.println("Give up!");
                        System.exit(-1);
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Adds a card to the player's hand.
     * @param card The card to be added to the player's hand.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
        controller.addCardToHand(card);
    }

    /**
     * Gets the player's score.
     * @return int representing the player's score.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public int getScore() throws RemoteException {
        return controller.getScore();
    }

    /**
     * Gets the current round of the game.
     * @return int representing the current round of the game.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public int getRound() throws RemoteException
    {
        return controller.getRound();
    }

    /**
     * Sets the current round of the game.
     * @param round The round to be set.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setRound(int round) throws RemoteException {
        controller.setRound(round);
    }

    /**
     * Sets the game controller.
     * @param game The game controller to be set.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        controller.setGame(game);
    }

    /**
     * Gets the player's personal objective card.
     * @return ObjectiveCard representing the player's personal objective card.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return controller.getPersonalObjectiveCard();
    }

    /**
     * Makes the player join the lobby.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void JoinLobby() throws RemoteException {
        controller.JoinLobby();
    }

    /**
     * This method is used to instruct the client on what to do next.
     * It delegates the instruction to the client controller.
     *
     * @param doThis A string representing the instruction for the client.
     * @throws RemoteException if the remote object cannot be created
     */
    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {
        controller.WhatDoIDoNow(doThis);
    }


}

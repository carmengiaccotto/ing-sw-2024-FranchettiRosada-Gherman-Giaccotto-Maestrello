package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Connection.Socket.Client.ClientListener;
import it.polimi.ingsw.Connection.Socket.Messages.DisconnectMessage;
import it.polimi.ingsw.Connection.Socket.Messages.GenericMessage;
import it.polimi.ingsw.Connection.Socket.Messages.UpdatePlayersMessage;
import it.polimi.ingsw.Connection.Socket.Messages.UpdatePlayersResponse;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the GameListener class and is specifically designed for handling game events over a socket connection.
 * It contains methods for updating players about the game state, sending game actions, and disconnecting players.
 * It uses an ObjectOutputStream to send messages and a ClientListener to receive messages.
 *
 * The class has two main components:
 * - ObjectOutputStream: used to write primitive data types and graphs of Java objects to an OutputStream.
 * - ClientListener: used to listen for incoming messages from the server and perform appropriate actions based on the message type.
 *
 * The class provides several methods for game management:
 * - updatePlayers: updates all players with a given message or the current state of the playground.
 * - disconnectPlayers: disconnects all players from the game.
 * - getPlayers: retrieves the list of players in the game.
 * - setPlayers: sets the list of players in the game.
 * - sendGameAction: sends a game action to all players.
 */
public class GameListenerForClientSocket extends GameListener {
    /**
     * The ObjectOutputStream instance used to write primitive data types and graphs of Java objects to an OutputStream.
     * The objects can be read (reconstituted) using an ObjectInputStream.
     * Persistent storage of objects can be accomplished by using a file for the stream.
     */
    private final ObjectOutputStream oos;

    /**
     * The ClientListener instance used to listen for incoming messages from the server.
     * It is responsible for handling the received messages and performing the appropriate actions based on the message type.
     */
    private final ClientListener listener;

    /**
     * Constructor for the GameListenerForClientSocket class.
     * Initializes the ObjectOutputStream and ClientListener instances.
     *
     * @param oos      The ObjectOutputStream instance used to write primitive data types and graphs of Java objects to an OutputStream.
     * @param listener The ClientListener instance used to listen for incoming messages from the server.
     */
    public GameListenerForClientSocket(ObjectOutputStream oos, ClientListener listener) {
        this.oos = oos;
        this.listener = listener;
    }

    /**
     * Sends a GenericMessage object to the server.
     * This method is synchronized on the ObjectOutputStream instance to prevent concurrent access issues.
     * The ObjectOutputStream's writeObject method is used to send the message, and its flush method is used to ensure that the message is actually sent.
     *
     * @param message The GenericMessage object to be sent to the server.
     * @throws IOException If an I/O error occurs while writing or flushing the ObjectOutputStream.
     */
    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (oos) {
            oos.writeObject(message);
            oos.flush();
        }
    }

    /**
     * This method is used to update all players except the current player with a given message.
     * It sends an UpdatePlayersMessage to the server, which then broadcasts the message to all other players.
     * The method is synchronized on the ObjectOutputStream instance to prevent concurrent access issues.
     * The ObjectOutputStream's writeObject method is used to send the message, and its flush method is used to ensure that the message is actually sent.
     * If an IOException occurs during the sending of the message, it is caught and a RuntimeException is thrown instead.
     *
     * @param message The message to be sent to all players.
     * @param currentPlayer The current player who should not receive the update.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void updatePlayers(String message, ClientControllerInterface currentPlayer) throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            if (!Objects.equals(player.getNickname(), currentPlayer.getNickname())) {
//                player.sendUpdateMessage(message);
//            }
//        }
        try {
            sendMessage(new UpdatePlayersMessage(message, currentPlayer.getNickname()));
            UpdatePlayersResponse response = listener.getUpdatePlayersResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an update message to all players.
     * This method iterates through each player in the game and sends them the provided message.
     *
     * @param message The message to be sent to all players.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void updatePlayers(String message) throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            player.sendUpdateMessage(message);
//
//        }
        try {
            sendMessage(new UpdatePlayersMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates all players with the current state of the playground.
     * This method iterates through each player in the game and sends them the current state of the playground.
     *
     * @param model The current state of the playground.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void updatePlayers(PlayGround model) {
//        for (ClientControllerInterface player : players) {
//            try {
//                player.showBoardAndPlayAreas(model);
//            } catch (RemoteException e) {
//                System.out.println("Error during call to showBoardAndPlayAreas: " + e.getMessage());
//            }
//        }
        try {
            sendMessage(new UpdatePlayersMessage(model));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disconnects all players from the game.
     * This method iterates through each player in the game and calls their disconnect method.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void disconnectPlayers() throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            player.disconnect();
//        }
        try {
            sendMessage(new DisconnectMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return A list of ClientControllerInterface objects representing the players.
     */
    @Override
    public List<ClientControllerInterface> getPlayers() {
//        if(players != null)
//            return players;
//        else {
//            System.out.println("Warning: Players list is null");
//            return  null;
//        }
        return new ArrayList<>();
    }

    /**
     * Sets the list of players in the game.
     * This method is currently commented out and does not perform any action.
     *
     * @param players A list of ClientControllerInterface objects representing the players to be set.
     */
    @Override
    public void setPlayers(List<ClientControllerInterface> players) {
//        this.players = players;
    }

    /**
     * Sends a game action to all players.
     * This method is currently commented out and does not perform any action.
     * In its intended implementation, it would iterate through each player in the game and call their WhatDoIDoNow method with the provided action.
     *
     * @param action The action to be sent to all players.
     */
    @Override
    public void sendGameAction(String action){
//        for(ClientControllerInterface c: getPlayers()) {
//            try {
//                c.WhatDoIDoNow(action);
//            } catch (RemoteException e) {
//                System.out.println("Unable to reach clients");
//            }
//        }
    }

}

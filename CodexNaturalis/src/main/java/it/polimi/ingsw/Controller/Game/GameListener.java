package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for managing the game listeners.
 * It implements Serializable interface for object serialization.
 */
public class GameListener implements Serializable {

    /**
     * A list of ClientControllerInterface objects representing the players in the game.
     * This list is used to manage the players' state and actions during the game.
     * It is initialized as an empty ArrayList at the start of the game.
     */
    private List<ClientControllerInterface> players = new ArrayList<>();

//    /**
//     * Sends an update message to all players except the current player.
//     * This method iterates through each player in the game and sends them the provided message.
//     *
//     * @param message The message to be sent to all players.
//     * @param currentPlayer The player who should not receive the message.
//     * @throws RemoteException If a remote or network communication error occurs.
//     */
//    public void updatePlayers(String message, ClientControllerInterface currentPlayer) throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            if (!Objects.equals(player.getNickname(), currentPlayer.getNickname())) {
//                player.sendUpdateMessage(message);
//            }
//        }
//    }
//
//    /**
//     * Sends an update message to all players.
//     * This method iterates through each player in the game and sends them the provided message.
//     *
//     * @param message The message to be sent to all players.
//     * @throws RemoteException If a remote or network communication error occurs.
//     */
//    public void updatePlayers(String message) throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            player.sendUpdateMessage(message);
//
//        }
//    }
//
//    /**
//     * Updates all players with the current state of the playground.
//     * This method iterates through each player in the game and sends them the current state of the playground.
//     *
//     * @param model The current state of the playground.
//     */
//    public void updatePlayers(PlayGround model) throws RemoteException{
//        for (ClientControllerInterface player : players) {
//            try {
//                player.showBoardAndPlayAreas(model);
//            } catch (RemoteException e) {
//                System.out.println("Error during call to showBoardAndPlayAreas: " + e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * Disconnects all players from the game.
//     * This method iterates through each player in the game and calls their disconnect method.
//     * This is typically used when the game is over or if the game server is shutting down.
//     *
//     * @throws RemoteException If a remote or network communication error occurs.
//     */
//    public void disconnectPlayers() throws RemoteException {
//        for (ClientControllerInterface player : players) {
//            player.disconnect();
//        }
//    }

    /**
     * Retrieves the list of players in the game.
     * This method returns a list of ClientControllerInterface objects representing the players.
     * If the players list is null, a warning message is printed and null is returned.
     *
     * @return A list of ClientControllerInterface objects representing the players. If the players list is null, null is returned.
     */
    public List<ClientControllerInterface> getPlayers() {
        if(players != null)
            return players;
        else {
            System.out.println("Warning: Players list is null");
            return  null;
        }
    }

    /**
     * Sets the list of players in the game.
     * This method takes a list of ClientControllerInterface objects representing the players and assigns it to the players field.
     *
     * @param players A list of ClientControllerInterface objects representing the players.
     */
    public void setPlayers(List<ClientControllerInterface> players) {
        this.players = players;
    }

//    /**
//     * Sends a game action to all players.
//     * This method iterates through each player in the game and calls their WhatDoIDoNow method with the provided action.
//     * If a RemoteException occurs during the call to WhatDoIDoNow, it prints a message indicating that the clients could not be reached.
//     *
//     * @param action The game action to be sent to all players. This is a string representing the game action.
//     */
//    public void sendGameAction(String action){
//        for(ClientControllerInterface c: getPlayers()) {
//            try {
//                c.WhatDoIDoNow(action);
//            } catch (RemoteException e) {
//                System.out.println("Unable to reach clients");
//            }
//        }
//    }
}
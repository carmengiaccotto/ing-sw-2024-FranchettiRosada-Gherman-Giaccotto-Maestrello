package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameListener implements Serializable {

    private List<ClientControllerInterface> players = new ArrayList<>();

    public void updatePlayers(String message, ClientControllerInterface currentPlayer) throws RemoteException {
        for (ClientControllerInterface player : players) {
            if (!Objects.equals(player.getNickname(), currentPlayer.getNickname())) {
                player.sendUpdateMessage(message);
            }
        }
    }

    /**
     * Sends an update message to all players.
     * This method iterates through each player in the game and sends them the provided message.
     *
     * @param message The message to be sent to all players.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void updatePlayers(String message) throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.sendUpdateMessage(message);

        }
    }

    /**
     * Updates all players with the current state of the playground.
     * This method iterates through each player in the game and sends them the current state of the playground.
     *
     * @param model The current state of the playground.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void updatePlayers(PlayGround model) {
        for (ClientControllerInterface player : players) {
            try {
                player.showBoardAndPlayAreas(model);
            } catch (RemoteException e) {
                System.out.println("Error during call to showBoardAndPlayAreas: " + e.getMessage());
            }
        }
    }

    /**
     * Disconnects all players from the game.
     * This method iterates through each player in the game and calls their disconnect method.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void disconnectPlayers() throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.disconnect();
        }
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return A list of ClientControllerInterface objects representing the players.
     */
    public List<ClientControllerInterface> getPlayers() {
        if(players != null)
            return players;
        else {
            System.out.println("Warning: Players list is null");
            return  null;
        }
    }

    public void sendGameAction(String action){
        for(ClientControllerInterface c: getPlayers()) {
            try {
                c.WhatDoIDoNow(action);
            } catch (RemoteException e) {
                System.out.println("Unable to reach clients");
            }
        }
    }
}
package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameListener implements Serializable {

    private List<ClientControllerInterface> players = new ArrayList<>();

    public void updatePlayers(ClientControllerInterface newPlayer) throws RemoteException {
        for (ClientControllerInterface player : players) {
            if (player != newPlayer) {
                player.sendUpdateMessage(newPlayer.getNickname() + " joined the game.");
                //player.addOpponent(newPlayer);
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
    public void updatePlayers(PlayGround model) throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.showBoardAndPlayAreas(model);
        }
    }

    /**
     * Updates all players with the current scores.
     * This method iterates through each player in the game, constructs a message with their nicknames and scores,
     * and sends this message to all players.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void updatePlayers() throws RemoteException {
        StringBuilder scores = new StringBuilder();
        for (ClientControllerInterface player : players) {
            scores.append(player.getNickname()).append(" : ").append(player.getPlayer().getScore()).append("\n");
        }
        String scoresMessage = scores.toString();
        for (ClientControllerInterface player : players) {
            player.sendUpdateMessage(scoresMessage);
        }
    }

    /**
     * Updates all players with the game results.
     * This method iterates through each player in the game and sends them a message with the winners.
     * If there is only one winner, it sends a message with the winner's nickname.
     * If there is a tie, it sends a message with the nicknames of all the winners.
     *
     * @param winners The list of winners.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void updatePlayers(List<ClientControllerInterface> winners) throws RemoteException {
        for (ClientControllerInterface player : players) {
            if (winners.size() == 1) {
                player.sendUpdateMessage("The winner is: " + winners.get(0).getNickname());
            } else {
                StringBuilder winList = new StringBuilder();
                for (ClientControllerInterface winner : winners) {
                    winList.append(winner.getNickname()).append("\n");
                }
                String winListMessage = winList.toString();
                player.sendUpdateMessage("Tie between the following players: " + winListMessage);
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
        return players;
    }
}
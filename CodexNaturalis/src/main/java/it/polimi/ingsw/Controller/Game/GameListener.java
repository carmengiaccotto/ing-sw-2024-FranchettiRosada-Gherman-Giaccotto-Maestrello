package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameListener {

    private List<ClientControllerInterface> players = new ArrayList<>();

    public void updatePlayers(String message) throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.sendUpdateMessage(message);
        }
    }

    public void updatePlayers(PlayGround model) throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.showBoardAndPlayAreas(model);
        }
    }

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

    public void disconnectPlayers() throws RemoteException {
        for (ClientControllerInterface player : players) {
            player.disconnect();
        }
    }

    public List<ClientControllerInterface> getPlayers() {
        return players;
    }
}

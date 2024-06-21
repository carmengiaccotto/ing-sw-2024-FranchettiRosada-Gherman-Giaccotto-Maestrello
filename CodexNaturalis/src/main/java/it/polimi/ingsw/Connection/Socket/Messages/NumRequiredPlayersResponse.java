package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Pair;
import java.util.ArrayList;

public class NumRequiredPlayersResponse extends GenericMessage{
    private ArrayList<Pair<Integer, Integer>> players;

    public NumRequiredPlayersResponse(ArrayList<Pair<Integer, Integer>> players) {
        this.players = players;
    }

    public ArrayList<Pair<Integer, Integer>> getPlayers() {
        return players;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Pair;
import java.util.ArrayList;

/**
 * This class extends the GenericMessage class and is used to represent a number of required players response.
 * It contains a list of pairs of integers, where each pair represents a player.
 */
public class NumRequiredPlayersResponse extends GenericMessage{
    // A list of pairs of integers, where each pair represents a player.
    private final ArrayList<Pair<Integer, Integer>> players;

    /**
     * Constructs a new NumRequiredPlayersResponse with the specified list of players.
     * @param players the list of players
     */
    public NumRequiredPlayersResponse(ArrayList<Pair<Integer, Integer>> players) {
        this.players = players;
    }

    /**
     * Returns the list of players.
     * @return the list of players
     */
    public ArrayList<Pair<Integer, Integer>> getPlayers() {
        return players;
    }
}
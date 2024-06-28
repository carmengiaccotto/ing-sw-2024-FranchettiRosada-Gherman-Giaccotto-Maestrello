package it.polimi.ingsw.Connection.Socket.Messages;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class extends the GenericMessage class and is used to represent a display available games response message.
 * It contains a map that represents the available games. The map's keys are the game IDs and the values are lists of player names.
 */
public class DisplayAvailableGamesResponse extends GenericMessage{
    // A map where the key is the game ID and the value is a list of player names.
    private final Map<Integer, ArrayList<String>> availableGames;

    /**
     * This constructor creates a new DisplayAvailableGamesResponse with the given map of available games.
     *
     * @param availableGames A map where the key is the game ID and the value is a list of player names.
     */
    public DisplayAvailableGamesResponse(Map<Integer, ArrayList<String>> availableGames){
        this.availableGames = availableGames;
    }

    /**
     * This method is used to get the map of available games.
     *
     * @return A map where the key is the game ID and the value is a list of player names.
     */
    public Map<Integer, ArrayList<String>> getAvailableGames(){
        return availableGames;
    }
}

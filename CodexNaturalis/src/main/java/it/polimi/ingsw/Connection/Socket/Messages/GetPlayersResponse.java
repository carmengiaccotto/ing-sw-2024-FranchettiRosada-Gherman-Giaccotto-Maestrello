package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Connection.Socket.Messages.Objects.PlayerForSocket;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;

/**
 * This class extends the GenericMessage class and is used to represent a get players response message.
 * It contains an ArrayList of PlayerForSocket objects representing the players.
 */
public class GetPlayersResponse extends GenericMessage {
    /**
    * The ArrayList of PlayerForSocket objects representing the players.
     */
    private final ArrayList<PlayerForSocket> playersForSocket;

    /**
     * This constructor creates a new GetPlayersResponse with the given ArrayList of Player objects.
     * It converts each Player object in the ArrayList to a PlayerForSocket object and adds it to the playersForSocket ArrayList.
     *
     * @param players The ArrayList of Player objects representing the players.
     */
    public GetPlayersResponse(ArrayList<Player> players) {
        playersForSocket = new ArrayList<>();
        for (Player p: players) {
            playersForSocket.add(new PlayerForSocket(p));
        }
    }

    /**
     * This method is used to get the ArrayList of Player objects representing the players.
     * It sets the play area for each player and then returns the ArrayList of players.
     *
     * @return The ArrayList of Player objects representing the players.
     */
    public ArrayList<Player> getPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        for (PlayerForSocket p: playersForSocket) {
            p.setPlayArea(new PlayArea(p.getCardsOnAreaForSocket(), p.getSymbolsForSocket()));
            players.add(p);
        }
        return players;
    }
}

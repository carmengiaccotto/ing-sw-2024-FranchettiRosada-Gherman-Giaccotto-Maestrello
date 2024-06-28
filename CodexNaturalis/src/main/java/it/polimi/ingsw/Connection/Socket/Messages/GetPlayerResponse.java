package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Connection.Socket.Messages.Objects.PlayerForSocket;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.Player;

/**
 * This class extends the GenericMessage class and is used to represent a get player response message.
 * It contains a PlayerForSocket object representing the player.
 */
public class GetPlayerResponse extends GenericMessage {
    /**
    * The PlayerForSocket object representing the player.
     */
    private final PlayerForSocket player;

    /**
     * This constructor creates a new GetPlayerResponse with the given Player object.
     * It converts the Player object to a PlayerForSocket object.
     *
     * @param player The Player object representing the player.
     */
    public GetPlayerResponse(Player player) {
        this.player = new PlayerForSocket(player);
    }

    /**
     * This method is used to get the Player object representing the player.
     * It sets the play area for the player and then returns the player.
     *
     * @return The Player object representing the player.
     */
    public Player getPlayer() {
        player.setPlayArea(new PlayArea(player.getCardsOnAreaForSocket(), player.getSymbolsForSocket()));
        return player;
    }
}

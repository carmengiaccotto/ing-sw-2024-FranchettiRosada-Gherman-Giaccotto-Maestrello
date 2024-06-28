package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

/**
 * This class extends the GenericMessage class and is used to represent a show board and play areas message.
 * It contains a PlayGround object representing the game's play area.
 */
public class ShowBoardAndPlayAreasMessage extends GenericMessage {
    // The game's play area.
    private final PlayGround playGround;

    /**
     * Constructs a new ShowBoardAndPlayAreasMessage with the specified play area.
     * @param playGround the game's play area
     */
    public ShowBoardAndPlayAreasMessage(PlayGround playGround) {
        this.playGround = playGround;
    }

    /**
     * Returns the game's play area.
     * @return the game's play area
     */
    public PlayGround getPlayGround() {
        return playGround;
    }
}
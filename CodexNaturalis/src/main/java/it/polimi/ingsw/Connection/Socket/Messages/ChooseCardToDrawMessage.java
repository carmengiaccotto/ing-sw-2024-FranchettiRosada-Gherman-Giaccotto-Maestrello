package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

/**
 * This class extends the GenericMessage class and is used to represent a message that chooses a card to draw.
 * It contains a PlayGround object that represents the current state of the playground.
 */
public class ChooseCardToDrawMessage extends GenericMessage {
    private final PlayGround playGround;

    /**
     * This constructor creates a new ChooseCardToDrawMessage with the given PlayGround object.
     *
     * @param playGround The PlayGround object representing the current state of the playground.
     */
    public ChooseCardToDrawMessage(PlayGround playGround) {
        this.playGround = playGround;
    }

    /**
     * This method is used to get the PlayGround object representing the current state of the playground.
     *
     * @return The PlayGround object representing the current state of the playground.
     */
    public PlayGround getPlayGround() {
        return playGround;
    }
}
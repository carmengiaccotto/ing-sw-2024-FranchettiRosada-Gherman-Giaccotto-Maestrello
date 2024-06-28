package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;

/**
 * This class extends the GenericMessage class and is used to represent a message that adds a card to the hand.
 * It contains a PlayCard object that represents the card to be added.
 */
public class AddCardToHand extends GenericMessage {
    /**
     * The PlayCard object that represents the card to be added to the hand.
     * This object is used in the AddCardToHand message to specify which card should be added.
     */
    private PlayCard playCard;

    /**
     * This constructor creates a new AddCardToHand message with the given PlayCard object.
     *
     * @param playCard The PlayCard object representing the card to be added.
     */
    public AddCardToHand(PlayCard playCard) {
        super();
        this.playCard = playCard;
    }

    /**
     * This method is used to get the PlayCard object representing the card to be added.
     *
     * @return The PlayCard object representing the card to be added.
     */
    public PlayCard getPlayCard() {
        return playCard;
    }
}
package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.SideOfCard;

/**
 * This class extends the GenericMessage class and is used to represent a response message that chooses a card to play.
 * It contains a SideOfCard object that represents the side of the card chosen.
 */
public class ChooseCardToPlayResponse extends GenericMessage {
    private SideOfCard sideOfCard;

    /**
     * This constructor creates a new ChooseCardToPlayResponse with the given SideOfCard object.
     *
     * @param sideOfCard The SideOfCard object representing the side of the card chosen.
     */
    public ChooseCardToPlayResponse(SideOfCard sideOfCard) {
        this.sideOfCard = sideOfCard;
    }

    /**
     * This method is used to get the SideOfCard object representing the side of the card chosen.
     *
     * @return The SideOfCard object representing the side of the card chosen.
     */
    public SideOfCard getSideOfCard() {
        return sideOfCard;
    }
}
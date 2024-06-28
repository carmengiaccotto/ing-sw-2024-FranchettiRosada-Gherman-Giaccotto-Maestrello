package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;
import java.util.ArrayList;

/**
 * This class extends the GenericMessage class and is used to represent an extract player hand cards response message.
 * It contains an ArrayList of PlayCard objects representing the player's hand cards.
 */
public class ExtractPlayerHandCardsResponse extends GenericMessage {
    /**
    * An ArrayList of PlayCard objects representing the player's hand cards.
     */
    private final ArrayList<PlayCard> playerHandCards;

    /**
     * This constructor creates a new ExtractPlayerHandCardsResponse with the given ArrayList of PlayCard objects.
     *
     * @param playerHandCards An ArrayList of PlayCard objects representing the player's hand cards.
     */
    public ExtractPlayerHandCardsResponse(ArrayList<PlayCard> playerHandCards) {
        this.playerHandCards = playerHandCards;
    }

    /**
     * This method is used to get the ArrayList of PlayCard objects representing the player's hand cards.
     *
     * @return An ArrayList of PlayCard objects representing the player's hand cards.
     */
    public ArrayList<PlayCard> extractPlayerHandCards() {
        return playerHandCards;
    }
}

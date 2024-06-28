package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

import java.util.ArrayList;

/**
 * This class extends the GenericMessage class and is used to represent a get personal objective cards response message.
 * It contains an ArrayList of ObjectiveCard objects representing the personal objective cards.
 */
public class GetPersonalObjectiveCardsResponse extends GenericMessage {
    /**
    * The ArrayList of ObjectiveCard objects representing the personal objective cards.
     */
    private final ArrayList<ObjectiveCard> objectiveCards;

    /**
     * This constructor creates a new GetPersonalObjectiveCardsResponse with the given ArrayList of ObjectiveCard objects.
     *
     * @param objectiveCards The ArrayList of ObjectiveCard objects representing the personal objective cards.
     */
    public GetPersonalObjectiveCardsResponse(ArrayList<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    /**
     * This method is used to get the ArrayList of ObjectiveCard objects representing the personal objective cards.
     *
     * @return The ArrayList of ObjectiveCard objects representing the personal objective cards.
     */
    public ArrayList<ObjectiveCard> getPersonalObjectiveCards() {
        return objectiveCards;
    }
}

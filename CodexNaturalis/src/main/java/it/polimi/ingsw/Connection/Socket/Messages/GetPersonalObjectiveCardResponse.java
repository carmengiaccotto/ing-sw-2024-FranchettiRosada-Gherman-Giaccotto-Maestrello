package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

/**
 * This class extends the GenericMessage class and is used to represent a get personal objective card response message.
 * It contains an ObjectiveCard object representing the personal objective card.
 */
public class GetPersonalObjectiveCardResponse extends GenericMessage{
    // The ObjectiveCard object representing the personal objective card.
    private final ObjectiveCard objectiveCard;

    /**
     * This constructor creates a new GetPersonalObjectiveCardResponse with the given ObjectiveCard object.
     *
     * @param objectiveCard The ObjectiveCard object representing the personal objective card.
     */
    public GetPersonalObjectiveCardResponse(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * This method is used to get the ObjectiveCard object representing the personal objective card.
     *
     * @return The ObjectiveCard object representing the personal objective card.
     */
    public ObjectiveCard getPersonalObjectiveCard() {
        return objectiveCard;
    }
}

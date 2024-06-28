package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

/**
 * This class extends the GenericMessage class and is used to represent a set personal objective card message.
 * It contains an ObjectiveCard object representing the personal objective card.
 */
public class SetPersonalObjectiveCardMessage extends GenericMessage {
    // The personal objective card to be set.
    private  ObjectiveCard objectiveCard;

    /**
     * Constructs a new SetPersonalObjectiveCardMessage with the specified personal objective card.
     * @param objectiveCard the personal objective card to be set
     */
    public SetPersonalObjectiveCardMessage(ObjectiveCard objectiveCard) {
        super();
        this.objectiveCard = objectiveCard;
    }

    /**
     * Returns the personal objective card.
     * @return the personal objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }
}
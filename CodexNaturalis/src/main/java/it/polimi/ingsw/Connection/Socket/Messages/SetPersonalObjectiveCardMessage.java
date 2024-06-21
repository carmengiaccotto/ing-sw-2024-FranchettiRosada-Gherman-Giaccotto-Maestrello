package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

public class SetPersonalObjectiveCardMessage extends GenericMessage {
    private  ObjectiveCard objectiveCard;

    public SetPersonalObjectiveCardMessage(ObjectiveCard objectiveCard) {
        super();
    }

    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

public class GetPersonalObjectiveCardResponse extends GenericMessage{
    private ObjectiveCard objectiveCard;

    public GetPersonalObjectiveCardResponse(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    public ObjectiveCard getPersonalObjectiveCard() {
        return objectiveCard;
    }
}

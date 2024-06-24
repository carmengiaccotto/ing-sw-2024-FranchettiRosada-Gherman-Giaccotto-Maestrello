package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

import java.util.ArrayList;

public class GetPersonalObjectiveCardResponse extends GenericMessage{
    private final ObjectiveCard objectiveCard;

    public GetPersonalObjectiveCardResponse(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    public ObjectiveCard getPersonalObjectiveCard() {
        return objectiveCard;
    }
}

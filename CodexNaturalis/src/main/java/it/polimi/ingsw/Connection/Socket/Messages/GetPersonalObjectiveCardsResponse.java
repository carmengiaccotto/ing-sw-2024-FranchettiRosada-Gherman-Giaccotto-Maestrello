package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;

import java.util.ArrayList;

public class GetPersonalObjectiveCardsResponse extends GenericMessage {
    private final ArrayList<ObjectiveCard> objectiveCards;

    public GetPersonalObjectiveCardsResponse(ArrayList<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    public ArrayList<ObjectiveCard> getPersonalObjectiveCards() {
        return objectiveCards;
    }
}

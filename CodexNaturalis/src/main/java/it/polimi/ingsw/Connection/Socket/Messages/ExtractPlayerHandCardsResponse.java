package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;

import java.util.ArrayList;

public class ExtractPlayerHandCardsResponse extends GenericMessage {
    private final ArrayList<PlayCard> playerHandCards;

    public ExtractPlayerHandCardsResponse(ArrayList<PlayCard> playerHandCards) {
        this.playerHandCards = playerHandCards;
    }

    public ArrayList<PlayCard> extractPlayerHandCards() {
        return playerHandCards;
    }
}

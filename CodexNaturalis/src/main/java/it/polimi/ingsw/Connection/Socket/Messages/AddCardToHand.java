package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;

public class AddCardToHand extends GenericMessage {
    private PlayCard playCard;

    public AddCardToHand(PlayCard playCard) {
        super();
        this.playCard = playCard;
    }

    public PlayCard getPlayCard() {
        return playCard;
    }
}

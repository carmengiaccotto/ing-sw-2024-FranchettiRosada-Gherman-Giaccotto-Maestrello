package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.SideOfCard;

public class ChooseCardToPlayResponse extends GenericMessage {
    private SideOfCard sideOfCard;

    public ChooseCardToPlayResponse(SideOfCard sideOfCard) {
        this.sideOfCard = sideOfCard;
    }

    public SideOfCard getSideOfCard() {
        return sideOfCard;
    }
}

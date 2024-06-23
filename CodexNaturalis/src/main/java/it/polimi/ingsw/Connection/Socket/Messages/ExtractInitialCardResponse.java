package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.InitialCard;

public class ExtractInitialCardResponse extends GenericMessage {
    private InitialCard card;

    public ExtractInitialCardResponse(InitialCard card){
        super();
        this.card = card;
    }

    public InitialCard getCard() {
        return card;
    }

}

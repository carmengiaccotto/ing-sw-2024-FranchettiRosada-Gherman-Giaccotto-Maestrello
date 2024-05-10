package it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.model.PlayGround.Deck;

public class DrawCardFromDeck extends ClientToControllerMessage {
    private Deck deck;

    public DrawCardFromDeck(Deck deck, String senderNickName) {
        super(senderNickName);
        this.deck = deck;
    }


    @Override
    public void execute(GameControllerInterface game) {
        //

    }
}

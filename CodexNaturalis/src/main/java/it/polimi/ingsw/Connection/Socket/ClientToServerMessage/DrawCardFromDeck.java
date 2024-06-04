package it.polimi.ingsw.Connection.Socket.ClientToServerMessage;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.PlayGround.Deck;

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

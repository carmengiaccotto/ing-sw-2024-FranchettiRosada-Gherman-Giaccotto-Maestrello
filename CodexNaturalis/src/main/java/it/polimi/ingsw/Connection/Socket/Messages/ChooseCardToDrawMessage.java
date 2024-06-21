package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

public class ChooseCardToDrawMessage extends GenericMessage {
    private PlayGround playGround;

    public ChooseCardToDrawMessage(PlayGround playGround) {
        super();
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}

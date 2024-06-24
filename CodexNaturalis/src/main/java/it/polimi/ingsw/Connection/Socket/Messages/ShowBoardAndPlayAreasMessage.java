package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

public class ShowBoardAndPlayAreasMessage extends GenericMessage {
    private final PlayGround playGround;

    public ShowBoardAndPlayAreasMessage(PlayGround playGround) {
        this.playGround = playGround;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}

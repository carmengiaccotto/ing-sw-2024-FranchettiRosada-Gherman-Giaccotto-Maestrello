package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

public class ShowBoardAndPlayAreasMessage extends GenericMessage {
    private PlayGround playGround;

    public ShowBoardAndPlayAreasMessage(PlayGround playGround) {
        super();
        this.playGround = playGround;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}

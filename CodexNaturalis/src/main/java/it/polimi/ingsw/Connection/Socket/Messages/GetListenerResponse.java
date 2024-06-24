package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Controller.Game.GameListener;

public class GetListenerResponse extends GenericMessage {
    private final GameListener gameListener;

    public GetListenerResponse(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    public GameListener getGameListener() {
        return gameListener;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Controller.Game.GameListener;

/**
 * This class extends the GenericMessage class and is used to represent a get listener response message.
 * It contains a GameListener object.
 */
public class GetListenerResponse extends GenericMessage {
    // The GameListener object.
    private final GameListener gameListener;

    /**
     * This constructor creates a new GetListenerResponse with the given GameListener object.
     *
     * @param gameListener The GameListener object.
     */
    public GetListenerResponse(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    /**
     * This method is used to get the GameListener object.
     *
     * @return The GameListener object.
     */
    public GameListener getGameListener() {
        return gameListener;
    }
}
package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.GameStatus;

/**
 * This class extends the GenericMessage class and is used to represent a get status response message.
 * It contains a GameStatus object representing the status of the game.
 */
public class GetStatusResponse extends GenericMessage {

    /**
    * The GameStatus object representing the status of the game.
     */
    private final GameStatus status;

    /**
     * This constructor creates a new GetStatusResponse with the given game status.
     *
     * @param status The status of the game.
     */
    public GetStatusResponse(GameStatus status) {
        this.status = status;
    }

    /**
     * This method is used to get the status of the game.
     *
     * @return The status of the game.
     */
    public GameStatus getStatus() {
        return status;
    }
}
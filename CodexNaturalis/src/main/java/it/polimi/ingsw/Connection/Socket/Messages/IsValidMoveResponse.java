package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a valid move response message.
 * It contains a boolean representing whether the move is valid or not.
 */
public class IsValidMoveResponse extends GenericMessage {
    /**
    * The boolean representing whether the move is valid or not.
     */
    private final boolean isValidMove;

    /**
     * This constructor creates a new IsValidMoveResponse with the given validity of the move.
     *
     * @param isValidMove The validity of the move.
     */
    public IsValidMoveResponse(boolean isValidMove) {
        this.isValidMove = isValidMove;
    }

    /**
     * This method is used to get the validity of the move.
     *
     * @return The validity of the move.
     */
    public boolean isValidMove() {
        return isValidMove;
    }
}

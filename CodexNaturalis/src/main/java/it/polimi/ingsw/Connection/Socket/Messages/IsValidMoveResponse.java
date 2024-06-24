package it.polimi.ingsw.Connection.Socket.Messages;

public class IsValidMoveResponse extends GenericMessage {

    private final boolean isValidMove;

    public IsValidMoveResponse(boolean isValidMove) {
        this.isValidMove = isValidMove;
    }

    public boolean isValidMove() {
        return isValidMove;
    }
}

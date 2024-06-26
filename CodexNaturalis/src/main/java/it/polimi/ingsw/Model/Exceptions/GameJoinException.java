package it.polimi.ingsw.Model.Exceptions;

public class GameJoinException extends RuntimeException {
    public GameJoinException(String message, Throwable cause) {
        super(message, cause);
    }
}

package it.polimi.ingsw.Exceptions;

public class GameRunningException extends RuntimeException {
    public GameRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}

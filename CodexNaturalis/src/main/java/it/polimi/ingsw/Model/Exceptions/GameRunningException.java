package it.polimi.ingsw.Model.Exceptions;

public class GameRunningException extends RuntimeException {
    public GameRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}

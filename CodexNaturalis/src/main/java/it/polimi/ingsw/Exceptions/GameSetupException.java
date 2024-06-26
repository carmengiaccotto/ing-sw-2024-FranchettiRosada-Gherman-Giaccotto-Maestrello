package it.polimi.ingsw.Exceptions;

public class GameSetupException extends RuntimeException {
    public GameSetupException(String message, Throwable cause) {
        super(message, cause);
    }
}

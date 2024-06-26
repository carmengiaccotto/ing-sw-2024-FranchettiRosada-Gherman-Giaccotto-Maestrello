package it.polimi.ingsw.Model.Exceptions;

public class GameSetupException extends RuntimeException {
    public GameSetupException(String message, Throwable cause) {
        super(message, cause);
    }
}

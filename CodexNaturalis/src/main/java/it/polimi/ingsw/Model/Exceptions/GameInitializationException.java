package it.polimi.ingsw.Model.Exceptions;

public class GameInitializationException extends RuntimeException {
    public GameInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
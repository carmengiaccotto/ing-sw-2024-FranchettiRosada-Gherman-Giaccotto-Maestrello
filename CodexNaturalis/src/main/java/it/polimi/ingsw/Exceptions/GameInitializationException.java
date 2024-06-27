package it.polimi.ingsw.Exceptions;

public class GameInitializationException extends RuntimeException {
    public GameInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
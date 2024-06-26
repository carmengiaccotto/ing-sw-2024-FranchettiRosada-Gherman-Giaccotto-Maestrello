package it.polimi.ingsw.Exceptions;

public class GameStatusException extends RuntimeException {
    public GameStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}

package it.polimi.ingsw.Exceptions;

public class DeckCreationException extends RuntimeException {
    public DeckCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package it.polimi.ingsw.Model.Exceptions;

public class DeckCreationException extends RuntimeException {
    public DeckCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

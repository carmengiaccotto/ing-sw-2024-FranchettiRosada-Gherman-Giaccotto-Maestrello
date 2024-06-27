package it.polimi.ingsw.Exceptions;

/**
 * This class represents an exception that is thrown when there is an error related to the status of a game.
 * It extends the RuntimeException class, meaning it is an unchecked exception.
 * Unchecked exceptions are exceptions that can be programmatically avoided.
 *
 * The class has a constructor that accepts a message and a cause.
 * The message is a string that contains a detailed description of the exception.
 * The cause is a throwable that caused the exception to be thrown.
 */
public class GameStatusException extends RuntimeException {

    /**
     * This constructor creates a new GameStatusException.
     *
     * @param message A string containing a detailed description of the exception.
     * @param cause A throwable that caused the exception to be thrown.
     */
    public GameStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
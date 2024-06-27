package it.polimi.ingsw.Exceptions;

/**
 * This class represents an exception that is thrown when there is an error related to a game that is currently running.
 * It extends the RuntimeException class, meaning it is an unchecked exception.
 * Unchecked exceptions are exceptions that can be programmatically avoided.
 *
 * The class has a constructor that accepts a message and a cause.
 * The message is a string that contains a detailed description of the exception.
 * The cause is a throwable that caused the exception to be thrown.
 */
public class GameRunningException extends RuntimeException {

    /**
     * This constructor creates a new GameRunningException.
     *
     * @param message A string containing a detailed description of the exception.
     * @param cause A throwable that caused the exception to be thrown.
     */
    public GameRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}

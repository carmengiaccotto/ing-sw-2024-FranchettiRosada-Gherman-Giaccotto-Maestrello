package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent an update message.
 * It contains a String object representing the message.
 */
public class UpdateMessage extends GenericMessage {
    /**
    * The message to be updated.
     */
    private final String message;

    /**
     * Constructs a new UpdateMessage with the specified message.
     * @param message the message to be updated
     */
    public UpdateMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}

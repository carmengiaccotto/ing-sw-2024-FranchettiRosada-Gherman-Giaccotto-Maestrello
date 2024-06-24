package it.polimi.ingsw.Connection.Socket.Messages;

public class UpdateMessage extends GenericMessage {
    private final String message;

    public UpdateMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

public class UpdateMessage extends GenericMessage {
    private String message;

    public UpdateMessage(String message) {
        super();
    }

    public String getMessage() {
        return message;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

public class UpdatePlayersMessage extends GenericMessage {
    private final String message;
    private String nickname;

    public UpdatePlayersMessage(String message) {
        this.message = message;
    }

    public UpdatePlayersMessage(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }
}

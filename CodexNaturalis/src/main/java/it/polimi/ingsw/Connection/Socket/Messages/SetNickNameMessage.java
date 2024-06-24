package it.polimi.ingsw.Connection.Socket.Messages;

public class SetNickNameMessage extends GenericMessage {
    private final String nickname;

    public SetNickNameMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}

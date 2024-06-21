package it.polimi.ingsw.Connection.Socket.Messages;

public class SetNickNameMessage extends GenericMessage {
    private String nickname;

    public SetNickNameMessage(String nickname) {
        super();
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}

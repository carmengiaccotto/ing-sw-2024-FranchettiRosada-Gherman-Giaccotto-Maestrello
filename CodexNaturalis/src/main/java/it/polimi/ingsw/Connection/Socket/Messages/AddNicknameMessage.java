package it.polimi.ingsw.Connection.Socket.Messages;

public class AddNicknameMessage extends GenericMessage {
    private String nickname;
    public AddNicknameMessage(String nickname) {
        super();
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
}

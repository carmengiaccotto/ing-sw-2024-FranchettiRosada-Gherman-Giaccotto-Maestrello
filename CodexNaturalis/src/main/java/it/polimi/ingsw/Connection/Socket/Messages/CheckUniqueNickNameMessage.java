package it.polimi.ingsw.Connection.Socket.Messages;

public class CheckUniqueNickNameMessage extends GenericMessage {
    private String nickname;

    public CheckUniqueNickNameMessage(String nickname) {
        super();
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}

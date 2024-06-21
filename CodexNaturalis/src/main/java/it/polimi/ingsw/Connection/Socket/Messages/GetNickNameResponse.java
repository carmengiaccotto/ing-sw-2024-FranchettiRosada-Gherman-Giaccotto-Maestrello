package it.polimi.ingsw.Connection.Socket.Messages;

public class GetNickNameResponse extends GenericMessage{
    private String nickName;

    public GetNickNameResponse(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}

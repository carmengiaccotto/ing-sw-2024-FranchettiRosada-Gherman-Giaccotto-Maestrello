package it.polimi.ingsw.Connection.Socket.Messages;

public class ChooseNicknameResponse extends GenericMessage{
    private String nickname;

    public ChooseNicknameResponse(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }
}

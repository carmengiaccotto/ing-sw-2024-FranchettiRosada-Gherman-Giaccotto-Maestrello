package it.polimi.ingsw.Connection.Socket.Messages;

import java.io.Serializable;

public class GenericMessage implements Serializable {
    private String nickname;
    private int gameId;

    public String getNickname() {
        return nickname;
    }

    public int getGameId() {
        return gameId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

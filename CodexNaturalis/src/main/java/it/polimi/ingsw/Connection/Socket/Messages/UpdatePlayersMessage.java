package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

public class UpdatePlayersMessage extends GenericMessage {
    private String message;
    private PlayGround playGround;
    private String nickname;

    public UpdatePlayersMessage(String message) {
        this.message = message;
    }

    public UpdatePlayersMessage(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }

    public UpdatePlayersMessage(PlayGround model) {
        this.playGround = model;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
}

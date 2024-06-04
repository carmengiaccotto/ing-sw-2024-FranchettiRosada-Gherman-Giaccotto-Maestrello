package it.polimi.ingsw.Connection.Socket.ClientToServerMessage;

import it.polimi.ingsw.Connection.Socket.ClientActions;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;

public abstract class ClientToControllerMessage {
    private String senderNickName;

    private ClientActions action;

    protected ClientToControllerMessage(String senderNickName) {
        this.senderNickName = senderNickName;
    }


    public abstract void execute(GameControllerInterface game);
}

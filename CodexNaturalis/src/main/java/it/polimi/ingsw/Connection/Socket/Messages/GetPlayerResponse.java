package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.Player;

public class GetPlayerResponse extends GenericMessage {
    private Player player;

    public GetPlayerResponse(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

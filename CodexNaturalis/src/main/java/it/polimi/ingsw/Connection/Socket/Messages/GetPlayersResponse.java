package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;

public class GetPlayersResponse extends GenericMessage {
    private final ArrayList<Player> player;

    public GetPlayersResponse(ArrayList<Player> player) {
        this.player = player;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }
}

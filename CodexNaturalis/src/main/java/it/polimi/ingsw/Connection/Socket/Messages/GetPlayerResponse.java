package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Connection.Socket.Messages.Objects.PlayerForSocket;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.Player;

public class GetPlayerResponse extends GenericMessage {
    private final PlayerForSocket player;

    public GetPlayerResponse(Player player) {
        this.player = new PlayerForSocket(player);
    }

    public Player getPlayer() {
        player.setPlayArea(new PlayArea(player.getCardsOnAreaForSocket(), player.getSymbolsForSocket()));
        return player;
    }
}

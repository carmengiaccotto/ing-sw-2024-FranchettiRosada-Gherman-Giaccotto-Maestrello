package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Connection.Socket.Messages.Objects.PlayerForSocket;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;

public class GetPlayersResponse extends GenericMessage {
    private final ArrayList<PlayerForSocket> playersForSocket;

    public GetPlayersResponse(ArrayList<Player> players) {
        playersForSocket = new ArrayList<>();
        for (Player p: players) {
            playersForSocket.add(new PlayerForSocket(p));
        }
    }

    public ArrayList<Player> getPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        for (PlayerForSocket p: playersForSocket) {
            p.setPlayArea(new PlayArea(p.getCardsOnAreaForSocket(), p.getSymbolsForSocket()));
            players.add(p);
        }
        return players;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

public class JoinGameMessage extends GenericMessage{
    private final int gameId;

    public JoinGameMessage(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}

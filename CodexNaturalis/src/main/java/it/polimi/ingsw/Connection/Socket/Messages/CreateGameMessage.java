package it.polimi.ingsw.Connection.Socket.Messages;

public class CreateGameMessage extends GenericMessage {
    private final int maxNumberOfPlayers;

    public CreateGameMessage(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
}

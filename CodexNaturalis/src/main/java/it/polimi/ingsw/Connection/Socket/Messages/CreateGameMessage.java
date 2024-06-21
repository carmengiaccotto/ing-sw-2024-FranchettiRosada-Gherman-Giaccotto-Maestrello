package it.polimi.ingsw.Connection.Socket.Messages;

public class CreateGameMessage extends GenericMessage {
    private int maxNumberOfPlayers;

    public CreateGameMessage(int maxNumberOfPlayers) {
        super();
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
}

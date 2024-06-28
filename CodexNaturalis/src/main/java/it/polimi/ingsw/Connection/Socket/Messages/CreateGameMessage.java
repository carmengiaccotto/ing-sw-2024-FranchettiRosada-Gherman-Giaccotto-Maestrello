package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a create game message.
 * It contains an integer that represents the maximum number of players for the game.
 */
public class CreateGameMessage extends GenericMessage {
    /**
     * The maximum number of players that can participate in a game.
     * This is a final variable, meaning that once it's set, it cannot be changed.
     * It is set in the constructor of the CreateGameMessage class.
     */
    private final int maxNumberOfPlayers;

    /**
     * This constructor creates a new CreateGameMessage with the given maximum number of players.
     *
     * @param maxNumberOfPlayers The maximum number of players for the game.
     */
    public CreateGameMessage(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    /**
     * This method is used to get the maximum number of players for the game.
     *
     * @return The maximum number of players for the game.
     */
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
}
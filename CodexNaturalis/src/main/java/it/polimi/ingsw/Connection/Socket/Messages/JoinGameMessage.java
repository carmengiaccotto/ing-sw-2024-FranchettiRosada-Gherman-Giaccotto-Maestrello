package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a join game message.
 * It contains an integer representing the ID of the game to join.
 */
public class JoinGameMessage extends GenericMessage{
    // The ID of the game to join.
    private final int gameId;

    /**
     * This constructor creates a new JoinGameMessage with the given game ID.
     *
     * @param gameId The ID of the game to join.
     */
    public JoinGameMessage(int gameId) {
        this.gameId = gameId;
    }

    /**
     * This method is used to get the ID of the game to join.
     *
     * @return The ID of the game to join.
     */
    public int getGameId() {
        return gameId;
    }
}

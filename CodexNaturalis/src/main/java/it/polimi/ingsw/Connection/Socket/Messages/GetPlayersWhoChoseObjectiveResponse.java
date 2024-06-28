package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a get players who chose objective response message.
 * It contains an integer representing the number of players who chose an objective.
 */
public class GetPlayersWhoChoseObjectiveResponse extends GenericMessage {

    /**
    * The integer representing the number of players who chose an objective.
     */
    private final int playersWhoChoseObjective;

    /**
     * This constructor creates a new GetPlayersWhoChoseObjectiveResponse with the given number of players who chose an objective.
     *
     * @param playersWhoChoseObjective The number of players who chose an objective.
     */
    public GetPlayersWhoChoseObjectiveResponse(int playersWhoChoseObjective) {
        this.playersWhoChoseObjective = playersWhoChoseObjective;
    }

    /**
     * This method is used to get the number of players who chose an objective.
     *
     * @return The number of players who chose an objective.
     */
    public int getPlayersWhoChoseObjective() {
        return playersWhoChoseObjective;
    }
}
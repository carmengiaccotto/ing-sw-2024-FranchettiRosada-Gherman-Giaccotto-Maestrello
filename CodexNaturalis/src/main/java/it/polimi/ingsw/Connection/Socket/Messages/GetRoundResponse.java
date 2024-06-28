package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a get round response message.
 * It contains an integer representing the current round.
 */
public class GetRoundResponse extends GenericMessage{

    /**
    * The integer representing the current round.
     */
    private final int round;

    /**
     * This constructor creates a new GetRoundResponse with the given round number.
     *
     * @param round The current round number.
     */
    public GetRoundResponse(int round) {
        this.round = round;
    }

    /**
     * This method is used to get the current round number.
     *
     * @return The current round number.
     */
    public int getRound() {
        return round;
    }
}

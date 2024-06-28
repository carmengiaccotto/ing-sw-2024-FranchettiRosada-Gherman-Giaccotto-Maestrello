package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a get score response message.
 * It contains an integer representing the score.
 */
public class GetScoreResponse extends GenericMessage {

    /**
    * The integer representing the score.
     */
    private final int score;

    /**
     * This constructor creates a new GetScoreResponse with the given score.
     *
     * @param score The score.
     */
    public GetScoreResponse(int score) {
        this.score = score;
    }

    /**
     * This method is used to get the score.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }
}

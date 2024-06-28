package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a final ranking response message.
 * It contains a String representing the final ranking.
 */
public class FinalRankingResponse extends GenericMessage {
    // The final ranking represented as a String.
    private final String finalRanking;

    /**
     * This constructor creates a new FinalRankingResponse with the given final ranking.
     *
     * @param finalRanking The final ranking represented as a String.
     */
    public FinalRankingResponse(String finalRanking) {
        this.finalRanking = finalRanking;
    }

    /**
     * This method is used to get the final ranking represented as a String.
     *
     * @return The final ranking represented as a String.
     */
    public String getFinalRanking() {
        return finalRanking;
    }
}

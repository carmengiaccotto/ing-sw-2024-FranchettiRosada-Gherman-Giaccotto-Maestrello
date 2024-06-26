package it.polimi.ingsw.Connection.Socket.Messages;

public class FinalRankingResponse extends GenericMessage {
    private final String finalRanking;

    public FinalRankingResponse(String finalRanking) {
        this.finalRanking = finalRanking;
    }

    public String getFinalRanking() {
        return finalRanking;
    }
}

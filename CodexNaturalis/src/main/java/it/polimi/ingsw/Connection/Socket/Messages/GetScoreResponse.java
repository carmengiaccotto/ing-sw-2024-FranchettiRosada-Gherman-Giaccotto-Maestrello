package it.polimi.ingsw.Connection.Socket.Messages;

public class GetScoreResponse extends GenericMessage {
    private int score;

    public GetScoreResponse(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

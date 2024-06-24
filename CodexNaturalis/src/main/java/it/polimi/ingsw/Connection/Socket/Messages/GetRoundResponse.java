package it.polimi.ingsw.Connection.Socket.Messages;

public class GetRoundResponse extends GenericMessage{
    private final int round;

    public GetRoundResponse(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }
}

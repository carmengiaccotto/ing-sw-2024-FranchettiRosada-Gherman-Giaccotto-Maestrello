package it.polimi.ingsw.Connection.Socket.Messages;

public class GetRoundResponse extends GenericMessage{
    private int round;

    public GetRoundResponse(int round) {
        super();
        this.round = round;
    }

    public int getRound() {
        return round;
    }
}

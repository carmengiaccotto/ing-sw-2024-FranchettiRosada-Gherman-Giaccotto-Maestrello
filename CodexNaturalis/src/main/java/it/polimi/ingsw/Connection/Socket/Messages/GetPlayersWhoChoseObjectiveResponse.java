package it.polimi.ingsw.Connection.Socket.Messages;

public class GetPlayersWhoChoseObjectiveResponse extends GenericMessage {
    private final int playersWhoChoseObjective;

    public GetPlayersWhoChoseObjectiveResponse(int playersWhoChoseObjective) {
        this.playersWhoChoseObjective = playersWhoChoseObjective;
    }

    public int getPlayersWhoChoseObjective() {
        return playersWhoChoseObjective;
    }
}

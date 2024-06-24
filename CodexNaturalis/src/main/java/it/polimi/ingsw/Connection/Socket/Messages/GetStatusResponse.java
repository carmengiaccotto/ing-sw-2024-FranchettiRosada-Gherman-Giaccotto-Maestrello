package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.GameStatus;

public class GetStatusResponse extends GenericMessage {
    private final GameStatus status;

    public GetStatusResponse(GameStatus status) {
        this.status = status;
    }
    public GameStatus getStatus() {
        return status;
    }

}

package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

public class GetModelResponse extends GenericMessage{

    private final PlayGround model;

    public GetModelResponse(PlayGround model){
        this.model=model;
    }

    public PlayGround getModel() {
        return model;
    }
}

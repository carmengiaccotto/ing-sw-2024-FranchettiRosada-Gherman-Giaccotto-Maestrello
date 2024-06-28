package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

/**
 * This class extends the GenericMessage class and is used to represent a get model response message.
 * It contains a PlayGround object representing the model.
 */
public class GetModelResponse extends GenericMessage{

    /**
    * The PlayGround object representing the model.
     */
    private final PlayGround model;

    /**
     * This constructor creates a new GetModelResponse with the given PlayGround object.
     *
     * @param model The PlayGround object representing the model.
     */
    public GetModelResponse(PlayGround model){
        this.model=model;
    }

    /**
     * This method is used to get the PlayGround object representing the model.
     *
     * @return The PlayGround object representing the model.
     */
    public PlayGround getModel() {
        return model;
    }
}
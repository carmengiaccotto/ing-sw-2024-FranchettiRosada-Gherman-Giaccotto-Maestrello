package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

public class GetPawnColorResponse extends GenericMessage{
    private final PawnColor pawnColor;

    public GetPawnColorResponse(PawnColor pawnColor){
        this.pawnColor = pawnColor;
    }

    public PawnColor getPawnColor(){
        return pawnColor;
    }
}

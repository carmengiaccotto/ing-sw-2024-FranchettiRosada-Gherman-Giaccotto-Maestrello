package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

public class RemoveAvailableColorMessage extends GenericMessage{

    private PawnColor color;

    public RemoveAvailableColorMessage(PawnColor color){
        this.color = color;
    }
    public PawnColor getColor() {
        return color;
    }
}

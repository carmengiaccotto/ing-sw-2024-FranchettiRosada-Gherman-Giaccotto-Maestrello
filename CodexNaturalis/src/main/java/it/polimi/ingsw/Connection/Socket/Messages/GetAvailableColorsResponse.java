package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

import java.util.List;

public class GetAvailableColorsResponse extends GenericMessage{
    private List<PawnColor> availableColors;

    public GetAvailableColorsResponse(List<PawnColor> colors){
        this.availableColors = colors;
    }

    public List<PawnColor> getAvailableColors() {
        return availableColors;
    }

}

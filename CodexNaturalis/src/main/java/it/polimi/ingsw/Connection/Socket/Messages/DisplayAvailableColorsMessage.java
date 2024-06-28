package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

import java.util.List;

public class DisplayAvailableColorsMessage extends GenericMessage {

    private List<PawnColor> colors;

    public DisplayAvailableColorsMessage(List<PawnColor> colors){
        this.colors = colors;
    }

    public List<PawnColor> getColors() {
        return colors;
    }

}

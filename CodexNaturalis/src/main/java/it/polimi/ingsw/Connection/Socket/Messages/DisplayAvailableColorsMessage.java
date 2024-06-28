package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;
import java.util.List;

/**
 * This class represents a message that is used to display the available colors for the pawns.
 * It extends the GenericMessage class.
 */
public class DisplayAvailableColorsMessage extends GenericMessage {

    /**
    * List of available colors for the pawns
     */
    private List<PawnColor> colors;

    /**
     * Constructor for the DisplayAvailableColorsMessage class.
     * @param colors List of available colors for the pawns
     */
    public DisplayAvailableColorsMessage(List<PawnColor> colors){
        this.colors = colors;
    }

    /**
     * Getter for the colors field.
     * @return List of available colors for the pawns
     */
    public List<PawnColor> getColors() {
        return colors;
    }

}
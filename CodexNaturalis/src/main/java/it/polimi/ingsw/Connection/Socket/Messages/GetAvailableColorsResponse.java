package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;
import java.util.List;

/**
 * This class extends the GenericMessage class and is used to represent a get available colors response message.
 * It contains a List of PawnColor objects representing the available colors.
 */
public class GetAvailableColorsResponse extends GenericMessage{
    /**
    * A List of PawnColor objects representing the available colors.
     */
    private final List<PawnColor> availableColors;

    /**
     * This constructor creates a new GetAvailableColorsResponse with the given List of PawnColor objects.
     *
     * @param colors A List of PawnColor objects representing the available colors.
     */
    public GetAvailableColorsResponse(List<PawnColor> colors){
        this.availableColors = colors;
    }

    /**
     * This method is used to get the List of PawnColor objects representing the available colors.
     *
     * @return A List of PawnColor objects representing the available colors.
     */
    public List<PawnColor> getAvailableColors() {
        return availableColors;
    }

}
package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

/**
 * This class extends the GenericMessage class and is used to represent a remove available color message.
 * It contains a PawnColor object representing the color to be removed.
 */
public class RemoveAvailableColorMessage extends GenericMessage{

    // The color to be removed.
    private final PawnColor color;

    /**
     * Constructs a new RemoveAvailableColorMessage with the specified color.
     * @param color the color to be removed
     */
    public RemoveAvailableColorMessage(PawnColor color){
        this.color = color;
    }

    /**
     * Returns the color to be removed.
     * @return the color to be removed
     */
    public PawnColor getColor() {
        return color;
    }
}
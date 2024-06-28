package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Enumerations.PawnColor;

/**
 * This class extends the GenericMessage class and is used to represent a get pawn color response message.
 * It contains a PawnColor object representing the pawn color.
 */
public class GetPawnColorResponse extends GenericMessage{
    // The PawnColor object representing the pawn color.
    private final PawnColor pawnColor;

    /**
     * This constructor creates a new GetPawnColorResponse with the given PawnColor object.
     *
     * @param pawnColor The PawnColor object representing the pawn color.
     */
    public GetPawnColorResponse(PawnColor pawnColor){
        this.pawnColor = pawnColor;
    }

    /**
     * This method is used to get the PawnColor object representing the pawn color.
     *
     * @return The PawnColor object representing the pawn color.
     */
    public PawnColor getPawnColor(){
        return pawnColor;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.InitialCard;

/**
 * This class extends the GenericMessage class and is used to represent an extract initial card response message.
 * It contains an InitialCard object.
 */
public class ExtractInitialCardResponse extends GenericMessage {
    /**
    * The InitialCard object that this message is carrying.
     */
    private final InitialCard card;

    /**
     * This constructor creates a new ExtractInitialCardResponse with the given InitialCard.
     *
     * @param card The InitialCard object that this message will carry.
     */
    public ExtractInitialCardResponse(InitialCard card){
        this.card = card;
    }

    /**
     * This method is used to get the InitialCard object that this message is carrying.
     *
     * @return The InitialCard object that this message is carrying.
     */
    public InitialCard getCard() {
        return card;
    }

}
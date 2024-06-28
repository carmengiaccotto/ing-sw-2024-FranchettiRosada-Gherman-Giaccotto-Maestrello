package it.polimi.ingsw.Model.Cards;


import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.io.Serializable;

/**
 * This class represents a Corner of a card in the game.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each Corner has a symbol, a position, a covered status, a hidden status, a reference to the next Corner, and a reference to the parent card.
 */
public class Corner implements Serializable {

    // Represents the symbol of the corner. It can be null.
private Symbol symbol;

// Represents the position of the corner on the card. It is a value from the CornerPosition enumeration.
private CornerPosition position;

// Represents the covered status of the corner. If true, the corner is covered.
private boolean covered;

// Represents the hidden status of the corner. If true, the corner is hidden.
private boolean hidden;

// Represents the next Corner of the current corner. It can be null.
private Corner nextCorner;

// Represents the parent card of the corner. It is the card that this corner belongs to.
private SideOfCard parentCard;

    /**
     * This is the constructor for the Corner class.
     * It initializes the symbol, hidden, covered, and nextCorner fields with the provided values.
     * If the provided symbol is not null and the corner is hidden, it throws an IllegalArgumentException.
     *
     * @param symbol The symbol of the corner. It can be null.
     * @param hidden The hidden status of the corner. It must be a boolean.
     * @throws IllegalArgumentException If symbol is not null and hidden is true.
     */
    public Corner(Symbol symbol, boolean hidden){
        covered=false;
        nextCorner=null;
        if (hidden && symbol!=null) {
            throw new IllegalArgumentException("You can not have a symbol in a hidden corner");
        }
        else {
            this.symbol = symbol;
            this.hidden = hidden;
        }

    }

    /**
     * This method returns the covered status of the corner.
     *
     * @return The covered status of the corner.
     */
    public boolean isCovered() {
        return covered;
    }

    /**
     * This method returns the hidden status of the corner.
     *
     * @return The hidden status of the corner.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * This method returns the next Corner of the current corner.
     *
     * @return The next Corner of the current corner.
     */
    public Corner getNextCorner() {
        return nextCorner;
    }

    /**
     * This method returns the symbol of the corner.
     *
     * @return The symbol of the corner. It can be null.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * This method sets the covered status of the corner to true.
     */
    public void setCovered() {
        covered = true;
    }

    /**
     * This method sets the next Corner of the current corner.
     *
     * @param nextCorner The next Corner of the current corner.
     */
    public void setNextCorner(Corner nextCorner) {
        this.nextCorner = nextCorner;
    }

    /**
     * This method returns the parent card of the corner.
     *
     * @return The parent card of the corner.
     */
    public SideOfCard getParentCard() {
        return parentCard;
    }

    /**
     * This method sets the parent card of the corner.
     * The parent card is the card that this corner belongs to.
     * This method is typically used during the construction of a card.
     *
     * @param parentCard The card that this corner belongs to.
     */
    public void setParentCard(SideOfCard parentCard) {
        this.parentCard = parentCard;
    }

    /**
     * This method returns the position of the corner on the card.
     * The position is represented as a value from the CornerPosition enumeration.
     *
     * @return The position of the corner on the card.
     */
    public CornerPosition getPosition() {
        return position;
    }

    /**
     * This method sets the position of the corner on the card.
     * The position is represented as a value from the CornerPosition enumeration.
     * This method is typically used during the construction of a card.
     *
     * @param position The position of the corner on the card.
     */
    public void setPosition(CornerPosition position) {
        this.position = position;
    }
}

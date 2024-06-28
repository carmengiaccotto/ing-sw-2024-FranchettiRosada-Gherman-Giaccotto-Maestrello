package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents a side of a card that has already been placed on the PlayArea.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 */
public class SideOfCard implements Serializable {

    /**
     * Matrix of corners of the card.
     */
    private final Corner[][] corners;

    /**
     * Color of the card. It is the same as the parent card's color.
     */
    private CardColors color;

    /**
     * The card that has this SideOfCard as one of its sides.
     */
    private PlayCard parentCard;

    /**
     * Map of symbols of the card. They can be placed in the corner or in the middle.
     */
    private final HashMap<Symbol, Integer> symbols;

    /**
     * This attribute is going to be used when we search for the configuration: a card cannot be counted twice when searching for a specific configuration
     * so the card can be taken into consideration only if this attribute is set to false.
     */
    private boolean InConfiguration;

    /**
     * Row and Column occupied by the card in the PlayArea.
     */
    private Pair<Integer, Integer> positionOnArea;

    /**
     * Class Constructor.
     * No card is in a configuration until DispositionObjectiveCard starts searching, so we initialize it to false.
     * Uses SetCornerPosition to associate the position of the corner in the Card with the position of the corner in Corner Class,
     * declared as a CornerPosition type.
     *
     * @param symbols The symbols of the card.
     * @param corners The corners of the card.
     */
    public SideOfCard(HashMap<Symbol, Integer> symbols, Corner[][] corners) {
        this.symbols = symbols;
        this.positionOnArea = null;
        this.corners=corners;
        if (corners!=null) {
            if (corners[0][0] != null) corners[0][0].setPosition(CornerPosition.TOPLEFT);
            if (corners[0][1] != null) corners[0][1].setPosition(CornerPosition.TOPRIGHT);
            if (corners[1][0] != null) corners[1][0].setPosition(CornerPosition.BOTTOMLEFT);
            if (corners[1][1] != null) corners[1][1].setPosition(CornerPosition.BOTTOMRIGHT);
            for (Corner[] cornerRow : getCorners())
                for (Corner corner : cornerRow)
                    if(corner!=null) corner.setParentCard(this);
        }
        InConfiguration=false;
    }

    /**
     * Getter method for symbols attribute.
     * @return A map that contains the symbol the card has in its corners or in the middle.
     */
    public HashMap<Symbol, Integer> getSymbols() {
        return symbols;
    }

    /**
     * Getter method for color attribute.
     * @return The color of the card. This is needed for DispositionCheck.
     */
    public CardColors getColor() {
        return color;
    }

    /**
     * Setter method for color attribute.
     * @param color The color of the card.
     */
    public void setColor(CardColors color){
        this.color=color;
    }

    /**
     * Getter method for InConfiguration attribute.
     * @return True if the card is already in configuration. This is needed for DispositionCheck.
     */
    public boolean isInConfiguration() {
        return InConfiguration;
    }

    /**
     * Setter method for InConfiguration attribute.
     * @param inConfiguration Boolean parameter. This is needed for DispositionCheck.
     */
    public void setInConfiguration(boolean inConfiguration) {
        InConfiguration = inConfiguration;
    }

    /**
     * Setter method for PositionOnArea attribute.
     * @param positionOnArea The pair of int represent row and column where the player placed the card.
     */
    public void setPositionOnArea(Pair<Integer, Integer> positionOnArea) {
        this.positionOnArea = positionOnArea;
    }

    /**
     * Getter method for corners attribute.
     * @return Matrix of corners.
     */
    public Corner[][] getCorners() {
        return corners;
    }

    /**
     * Getter method for the Corner in a specific position.
     * @param position CornerPosition type.
     * @return Corner that has the specified position.
     * @throws IllegalArgumentException If we try to access a corner that does not exist on the card.
     */
    public Corner getCornerInPosition(CornerPosition position) {
        for (Corner[] row : corners) {//iterate over the rows
            for (Corner corner : row) {//iterate over the columns of the Corners matrix
                if (corner.getPosition().equals(position)) {//check if we found the corner with the specified position
                    return corner;
                }
            }
        }
        throw new IllegalArgumentException("There is no corner in that position");//if we did not find the corner we throw an exception
    }

    /**
     * This method is a getter for the positionOnArea attribute.
     * The positionOnArea attribute represents the position of the card on the play area.
     * The position is represented as a Pair of Integers, where the first integer represents the row and the second represents the column.
     *
     * @return The position of the card on the play area. It is a Pair of Integers, where the first integer represents the row and the second represents the column.
     */
    public Pair<Integer, Integer> getPositionOnArea() {
        return positionOnArea;
    }

    /**
     * This method is a getter for the parentCard attribute.
     * The parentCard attribute represents the card that has this SideOfCard as one of its sides.
     *
     * @return The card that has this SideOfCard as one of its sides.
     */
    public PlayCard getParentCard() {
        return parentCard;
    }

    /**
     * This method is a setter for the parentCard attribute.
     * The parentCard attribute represents the card that has this SideOfCard as one of its sides.
     *
     * @param parentCard The card that has this SideOfCard as one of its sides.
     */
    public void setParentCard(PlayCard parentCard) {
        this.parentCard = parentCard;
    }
}
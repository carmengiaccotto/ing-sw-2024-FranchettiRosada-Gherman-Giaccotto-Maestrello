package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Symbol;

import java.io.Serializable;
import java.util.HashMap;


/** Class that represents the Card that has already been placed on the PlayArea*/

public class SideOfCard implements Serializable {
    /**Matrix of corners */
    private final Corner[][] corners;




    /**Color of the card. Same as the ParentCard's*/
    private CardColors color;



    /**Card that has this SideOfCard as one of its sides*/
    private PlayCard parentCard;




    /**Map of Symbols of the card. They can be placed in the corner or in the middle*/
    private final HashMap<Symbol, Integer> symbols;




    /**This attribute is going to be used when we search for the configuration: a card cannot be counted twice when searching for a specific configuration
     * so the card can be taken into consideration only if this attribute is set to false*/
    private boolean InConfiguration;




    /**Row and Column occupied by the card in the PlayArea*/
    private Pair<Integer, Integer> positionOnArea;



    /**
     * Class Constructor
     * No card is in a configuration until DispositionObjectiveCard starts searching, so we initialize it to false
     * Uses SetCornerPosition to associate the position of the corner in the Card with the position of the corner in Corner Class,
     * declared as a CornerPosition type
     * */

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
     * Getter method for symbols Attribute
     * @return symbols a map that contains the symbol the card has in its corners or in the middle
     * */

    public HashMap<Symbol, Integer> getSymbols() {
        return symbols;
    }





    /**
     * Getter Method for color Attribute
     * @return color the Color of the Card. Needed for DispositionCheck
     * */

    public CardColors getColor() {
        return color;
    }




    /**
     * Setter Method for Color Attribute
     * @param color the color of the card
     * */

    public void setColor(CardColors color){
        this.color=color;
    }




    /**
     * Getter method for InConfiguration method
     * @return true if the card is already in configuration.  Needed for DispositionCheck
     * */

    public boolean isInConfiguration() {
        return InConfiguration;
    }




    /**
     * Setter method for InConfiguration method
     * @param inConfiguration boolean parameter.  Needed for DispositionCheck
     * */

    public void setInConfiguration(boolean inConfiguration) {
        InConfiguration = inConfiguration;
    }




    /**
     * Setter method for PositionOnArea
     * @param positionOnArea the pair of int represent row and column where the player placed the card
     * */

    public void setPositionOnArea(Pair<Integer, Integer> positionOnArea) {
        this.positionOnArea = positionOnArea;
    }





    /**
     * Corners getter method
     * @return corners matrix of corners
     * */

    public Corner[][] getCorners() {
        return corners;
    }




    /**
     * Getter method for the Corner in a specific position
     * @param position CornerPosition type
     * @return corner that has the specified position
     * @throws IllegalArgumentException if we try to access a corner that does not exist on the card
     * */

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
     * Getter method for PositionOnArea
     * @return positionOnArea PairOfInteger: first represents row, second represents column
     * */

    public Pair<Integer, Integer> getPositionOnArea() {
        return positionOnArea;
    }



    /**
     * Getter method for ParentCard attribute
     * @return playCard the card that has this sideOfCard as one of its sides
     * */

    public PlayCard getParentCard() {
        return parentCard;
    }



    /**
     * Setter Method for ParentCard attribute
     * @param parentCard the card that has this sideOfCard as one of its sides
     * */

    public void setParentCard(PlayCard parentCard) {
        this.parentCard = parentCard;
    }
}
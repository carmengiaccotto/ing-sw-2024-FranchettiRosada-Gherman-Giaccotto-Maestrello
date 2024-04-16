package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;


/** Class that represents the Card that has already been placed on the PlayArea*/

public class SideOfCard {
    /**Matrix of corners */ //Does it make sense to keep it as a matrix or do we just make a list?
    private Corner[][] corners;

    private final CardColors color;


    /**Map of Symbols of the card. They can be placed in the corner or in the middle*/
    private final HashMap<Symbol, Integer> symbols;


    /**This attribute is going to be used when we search for the configuration: a card cannot be counted twice when searching for a specific configuration
     * so the card can be taken into consideration only if this attribute is set to false*/
    private boolean InConfiguration;


    /**Row and Column occupied by the card in the PlayArea*/
    private Pair<Integer, Integer> positionOnArea;






    /**Class Constructor
     * No card is in a configuration until DispositionObjectiveCard starts searching, so we initialize it to false
     * Uses SetCornerPosition to associate the position of the corner in the Card with the position of the corner in Corner Class,
     * declared as a CornerPosition type*/
    public SideOfCard(HashMap<Symbol, Integer> symbols, Pair<Integer, Integer> positionOnArea, CardColors color) {
        this.symbols = symbols;
        this.color=color;
        this.positionOnArea = positionOnArea;
        SetCornersPosition();
        SetCornersPosition();
        for(Corner[] cornerRow: getCorners())
            for(Corner corner :cornerRow)
                corner.setParentCard(this);
        InConfiguration=false;
    }



    /**Getter method for symbols Attribute
     * @return symbols a map that contains the symbol the card has in its corners or in the middle */
    public HashMap<Symbol, Integer> getSymbols() {
        return symbols;
    }





    /**Getter Method for color Attribute
     * @return color the Color of the Card. Needed for DispositionCheck*/
    public CardColors getColor() {
        return color;
    }

    public boolean isInConfiguration() {
        return InConfiguration;
    }

    public void setInConfiguration(boolean inConfiguration) {
        InConfiguration = inConfiguration;
    }







    /**setter method for PositionOnArea
     * @param positionOnArea the pair of int represent row and column where the player placed the card*/
    public void setPositionOnArea(Pair<Integer, Integer> positionOnArea) {
        this.positionOnArea = positionOnArea;
    }





    /**Corners getter method
     * @return corners matrix of corners*/
    public Corner[][] getCorners() {
        return corners;
    }




    /**Getter method for the Corner in a specific position
     * @param position CornerPosition type
     * @return corner that has the specified position
     * @throws IllegalArgumentException if we try to access a corner that does not exist on the card */
    public Corner getCornerInPosition(CornerPosition position){
        for (Corner[] row : corners) {
            for (Corner corner : row) {
                if (corner.getPosition().equals(position)) {
                    return corner;
                }
            }
        }
        throw new IllegalArgumentException("There is no corner in that position");
    }




    /**Method tha links the CornerPosition on the card to the attribute in the Corner class.*/
    public void SetCornersPosition(){
        corners[0][0].setPosition(CornerPosition.TOPLEFT);
        corners[0][1].setPosition((CornerPosition.TOPRIGHT));
        corners[1][0].setPosition(CornerPosition.BOTTOMLEFT);
        corners[1][1].setPosition(CornerPosition.BOTTOMRIGHT);
    }












    /**Getter method for PositionOnArea
     * @return positionOnArea PairOfInteger: first represents row, second represents column*/
    public Pair<Integer, Integer> getPositionOnArea() {
        return positionOnArea;
    }




}
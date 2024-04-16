package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;


/** Class that represents the Card that has already been placed on the PlayArea*/

public class SideOfCard {
    /**Matrix of corners */ //Does it make sense to keep it as a matrix or do we just make a list?
    private Corner[][] corners;

    private final Colors color;


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
    public SideOfCard( HashMap<Symbol, Integer> symbols, Pair<Integer, Integer> positionOnArea, Colors color) {
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



    /**Method to get the card that is in a specific CornerPosition compared to the current one
     * @param position the position we want to get
     * @param playArea PlayArea we are currently checking. We do not use it here,
     *                   but need to put the parameter for DispositionCheck purpose
     * @return card the card in the given position*/
    public SideOfCard getNeighbourCard(CornerPosition position, PlayArea playArea){
        return getCornerInPosition(position).getNextCorner().getParentCard();
    }





    /**Method to get the card that is in a specific UpDownPosition compared to the current one
     * Overload method
     * @param position the position we want to get
     * @param playArea PlayArea we are currently checking.
     * @return card the card in the given position*/
    public SideOfCard getNeighbourCard(UpDownPosition position, PlayArea playArea) {
        Pair<Integer, Integer> coordinatesToCheck=position.PositionToCheck(this);
        int rowToCheck=coordinatesToCheck.getFirst();
        int columnToCheck=coordinatesToCheck.getSecond();
        return playArea.getCardInPosition(rowToCheck,columnToCheck);


    }


    /**Getter method for PositionOnArea
     * @return positionOnArea PairOfInteger: first represents row, second represents column*/
    public Pair<Integer, Integer> getPositionOnArea() {
        return positionOnArea;
    }



    /**getter method for symbols
     * @return symbols a map that contains the symbol the card has in its corners or in the middle */
    public HashMap<Symbol, Integer> getSymbols() {
        return symbols;
    }

    public Colors getColor() {
        return color;
    }

    public boolean isInConfiguration() {
        return InConfiguration;
    }

    public void setInConfiguration(boolean inConfiguration) {
        InConfiguration = inConfiguration;
    }
}
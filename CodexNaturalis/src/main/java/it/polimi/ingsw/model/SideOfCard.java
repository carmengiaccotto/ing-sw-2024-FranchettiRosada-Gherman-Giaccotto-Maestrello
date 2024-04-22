package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;


/** Class that represents the Card that has already been placed on the PlayArea*/

public class SideOfCard {
    /**Matrix of corners */
    private Corner[][] corners;

    private CardColors color;


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
    public SideOfCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners) {
        this.symbols = symbols;
        this.color=color;
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




    public SideOfCard mapFromJson(JsonObject jsonObject){
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        JsonObject symbolsObject = jsonObject.getAsJsonObject("symbols");
        for (Symbol symbol : Symbol.values()) {
            JsonElement symbolValue = symbolsObject.get(symbol.name());
            if (symbolValue != null) {
                symbols.put(symbol, symbolValue.getAsInt());
            }
        }
        this.color=CardColors.valueOf(jsonObject.get("color").getAsString());
        Corner[][] corners = new Corner[2][2];
        corners[0][0]= CornerFactory.createCornerFromJson(jsonObject.get("corner1").getAsString());
        corners[0][1]=CornerFactory.createCornerFromJson(jsonObject.get("corner2").getAsString());
        corners[1][0]=CornerFactory.createCornerFromJson(jsonObject.get("corner3").getAsString());
        corners[1][1]=CornerFactory.createCornerFromJson(jsonObject.get("corner4").getAsString());
        return new SideOfCard(symbols, color, corners);

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



    /**Getter method for PositionOnArea
     * @return positionOnArea PairOfInteger: first represents row, second represents column*/
    public Pair<Integer, Integer> getPositionOnArea() {
        return positionOnArea;
    }




}
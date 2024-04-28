package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.CornerFactory;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CornerPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Symbol;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


/** @author Alessia Franchetti-Rosada
 * Class that represents the Card that has already been placed on the PlayArea*/

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




    public SideOfCard mapSideFromJson(JsonObject jsonObject){
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        JsonObject symbolsObject = jsonObject.getAsJsonObject("symbols");
        if (symbolsObject != null) {
            for (Map.Entry<String, JsonElement> entry : symbolsObject.entrySet()) {
                String symbolName = entry.getKey();
                JsonElement symbolValueElement = entry.getValue();
                if (symbolValueElement != null && symbolValueElement.isJsonPrimitive()) {
                    int symbolValue = symbolValueElement.getAsInt();
                    Symbol symbol = Symbol.valueOf(symbolName.toUpperCase());
                    symbols.put(symbol, symbolValue);
                }
            }
        }
        else
            symbols = new HashMap<>();

        JsonElement jsonColorElement = jsonObject.get("color");
        if (jsonColorElement != null && !jsonColorElement.isJsonNull()) {
            this.color = CardColors.valueOf(jsonColorElement.getAsString());
        }
        Corner[][] corners = new Corner[2][2];
        JsonElement corner1Element = jsonObject.get("corner1");
        JsonElement corner2Element = jsonObject.get("corner2");
        JsonElement corner3Element = jsonObject.get("corner3");
        JsonElement corner4Element = jsonObject.get("corner4");

        if (corner1Element != null && !corner1Element.isJsonNull()) {
            corners[0][0] = CornerFactory.createCornerFromJson(corner1Element.getAsString());
        }

        if (corner2Element != null && !corner2Element.isJsonNull()) {
            corners[0][1] = CornerFactory.createCornerFromJson(corner2Element.getAsString());
        }

        if (corner3Element != null && !corner3Element.isJsonNull()) {
            corners[1][0] = CornerFactory.createCornerFromJson(corner3Element.getAsString());
        }

        if (corner4Element != null && !corner4Element.isJsonNull()) {
            corners[1][1] = CornerFactory.createCornerFromJson(corner4Element.getAsString());
        }
        return new SideOfCard(symbols, corners);

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

    /**Setter Method for Color Attribute
     * @param color the color of the card*/
    public void setColor(CardColors color){
        this.color=color;
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
package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/** This subclass represents the Gold Cards and extends the SideOfCard class*/
public class GoldCard extends SideOfCard {
    private final HashMap<Symbol, Integer> requirement;
    private int point;

    /**Class Constructor*/
    public GoldCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners, HashMap<Symbol, Integer> requirement, int point ) {
        super(symbols, color, corners);
        this.requirement=requirement;
        this.point=point;
    }

    public GoldCard mapFromJson(JsonObject jsonObject) {
        SideOfCard sideOfCard = super.mapSideFromJson(jsonObject);
        this.point=jsonObject.get("point").getAsInt();
        JsonObject requirementJson = jsonObject.getAsJsonObject("requirement");
        HashMap<Symbol, Integer> requirementMap = new HashMap<>();
        for (String key : requirementJson.keySet()) {
            Symbol symbol = Symbol.valueOf(key);
            int value = requirementJson.get(key).getAsInt();
            requirementMap.put(symbol, value);
        }
        return new GoldCard(sideOfCard.getSymbols(), sideOfCard.getColor(), sideOfCard.getCorners(), requirementMap,this.point);
    }

    /**
     * getter method for the GoldCard's point
     *
     * @return point
     */
    public int getPoints(){
        return point;
    }



    /**
     * This method checks whether the requirement of the Gold Card is met by the player's play area.
     * If the result is true, the card can be used by the player, otherwise not.
     *
     * @param symbols a map containing symbols and their counts in the player's play area
     * @return true if the requirement is met, false otherwise
     */
    public boolean checkRequirement(Map<Symbol, Integer> symbols){
            for (Symbol key : requirement.keySet()) {
                if (!symbols.containsKey(key))
                    return false;
                else{
                    if(symbols.get(key)<requirement.get(key))
                        return false;
                }
            }
        return true;
    }

    /**Method to calculate the points that the placing of the card gives
     * Override in PointPerCoveredCorner and PointPerVisibleSymbol classes
     * @param point points to be added
     * @return int points to add to Player's score*/
    public int increasePoints (int point){
        return this.point;
    }


    /**
     * getter method for the GoldCard's requirement
     *
     * @return requirement
     */
    public HashMap<Symbol, Integer> getRequirement(){
        return requirement;
    }
}

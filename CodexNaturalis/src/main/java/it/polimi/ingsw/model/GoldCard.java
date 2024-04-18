package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/** @author Alessia Franchetti-Rosada
 * This subclass represents the Gold Cards and extends the SideOfCard class*/
public class GoldCard extends SideOfCard {
    private final HashMap<Symbol, Integer> requirement;
    private int point;

    /**Class Constructor*/
    public GoldCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners,HashMap<Symbol, Integer> requirement,int point ) {
        super(symbols, color, corners);
        this.requirement=requirement;
        this.point=point;
    }

    public GoldCard mapFromJson(JsonObject jsonObject) {
        SideOfCard sideOfCard = super.mapFromJson(jsonObject);
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


    public int increasePoints(int point){
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
        boolean ok = true;

            for (Symbol key : requirement.keySet()) {
                if (symbols.containsKey(key)) {
                    if (symbols.get(key) < requirement.get(key)) {
                        return ok = false;
                    }
                }else{
                    return ok = false;
                }
            }
        return ok;
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

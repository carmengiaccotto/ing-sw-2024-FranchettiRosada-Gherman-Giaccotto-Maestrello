package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/** This subclass represents the Gold Cards and extends the SideOfCard class*/
public class GoldCard extends PlayCard {
    private final HashMap<Symbol, Integer> requirement;
    private int point;

    private boolean typeOfGoal;

    public GoldCard(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point) {
        super(id, front, back, color);
        this.requirement= requirement;
        this.point=point;
    }

    /**Class Constructor*/


    public GoldCard mapFromJson(JsonObject jsonObject) {
        PlayCard playcard = super.mapFromJson(jsonObject);
        JsonObject FrontCard = jsonObject.getAsJsonObject("front");
        this.point=FrontCard.get("point").getAsInt();
        JsonObject requirementJson = FrontCard.getAsJsonObject("requirement");
        HashMap<Symbol, Integer> requirementMap = new HashMap<>();
        for (String key : requirementJson.keySet()) {
            Symbol symbol = Symbol.valueOf(key);
            int value = requirementJson.get(key).getAsInt();
            requirementMap.put(symbol, value);
        }
        this.typeOfGoal=FrontCard.get("coveredCorners").getAsBoolean();
        if(typeOfGoal)
            return new PointPerCoveredCorner(playcard.getIdCard(), playcard.getFront(), playcard.getBack(), playcard.getColor(), requirementMap,this.point);
        else {
            JsonObject JsonGoal = FrontCard.getAsJsonObject("goal");
            HashMap<Symbol, Integer> GoalMap = new HashMap<>();
            for (String key : JsonGoal.keySet()) {
                Symbol symbol = Symbol.valueOf(key);
                int value = JsonGoal.get(key).getAsInt();
                GoalMap.put(symbol, value);
            }
            Symbol goalSymbol=null;
            for (Symbol symbol: GoalMap.keySet()){
                if( GoalMap.get(symbol)==1)
                    goalSymbol=symbol;

            }

            return new PointPerVisibleSymbol(playcard.getIdCard(), playcard.getFront(), playcard.getBack(), playcard.getColor(),requirementMap,this.point, goalSymbol);
        }
    }

    /**
     * getter method for the GoldCard's point
     *
     * @return point
     */
    public int getPoints(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return point;
        else
            return 0;
    }



    /**
     * This method checks whether the requirement of the Gold Card is met by the player's play area.
     * If the result is true, the card can be used by the player, otherwise not.
     *
     * @param symbols a map containing symbols and their counts in the player's play area
     * @return true if the requirement is met, false otherwise
     */
    public boolean checkRequirement(Map<Symbol, Integer> symbols, Side chosenSide){
        if(chosenSide.equals(Side.FRONT)){
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
        else
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

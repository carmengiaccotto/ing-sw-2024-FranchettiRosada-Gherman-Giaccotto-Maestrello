package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/** @author Carmen Giaccotto
 * This subclass represents a specific type of Objective Card which goal is to have certains symbols on the Player's PlayArea.
 * It extends the base ObjetiveCards class and provides additional customization.
 */
public class SymbolObjectiveCard extends ObjectiveCard {

    private final HashMap<Symbol, Integer> goal;

    public SymbolObjectiveCard(int id,ObjectivePoints points, HashMap<Symbol, Integer> goal) {
        super(id,points);
        this.goal = goal;
    }



    public SymbolObjectiveCard mapFromJson(JsonObject jsonObject){
        ObjectiveCard card=super.mapFromJson(jsonObject);
        JsonObject requiredSymbols = jsonObject.getAsJsonObject("RequiredSymbols");
        for (Map.Entry<String, JsonElement> entry : requiredSymbols.entrySet()) {
            Symbol symbol = Symbol.valueOf(entry.getKey());
            int quantity = entry.getValue().getAsInt();
            goal.put(symbol, quantity);
        }
        return new SymbolObjectiveCard(getIdCard(),card.getPoints(), goal);
    }

    public int CheckGoals(Map<Symbol, Integer> symbols) {
        int numberOfGoals = 0;
        boolean ok = true;
        Map<Symbol, Integer> tmp = new HashMap<>(symbols);

        while (true) {
            for (Symbol key : goal.keySet()) {
                if (tmp.containsKey(key)) {
                    if (tmp.get(key) < goal.get(key)) {
                        ok = false;
                        break;
                    } else {
                        tmp.put(key, tmp.get(key) - goal.get(key));
                    }
                }
            }

            if (ok) {
                numberOfGoals++;
            } else {
                break;
            }
        }

        return numberOfGoals;
    } //javaDoc missing

    /**
     * getter method for the ObjectiveCard's goal
     *
     * @return goal
     */
    public HashMap<Symbol, Integer > getGoal(){
         return goal;
    }

}

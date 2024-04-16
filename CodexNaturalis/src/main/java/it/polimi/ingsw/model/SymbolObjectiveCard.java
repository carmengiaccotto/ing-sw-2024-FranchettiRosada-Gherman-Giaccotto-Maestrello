package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/** @author Carmen Giaccotto
 * This subclass represents a specific type of Objective Card which goal is to have certains symbols on the Player's PlayArea.
 * It extends the base ObjetiveCards class and provides additional customization.
 */
public class SymbolObjectiveCard extends ObjectiveCard {

    private final HashMap<Symbol, Integer> goal;

    public SymbolObjectiveCard(ObjectivePoints points, HashMap<Symbol, Integer> goal) {
        super(points);
        this.goal = goal;
    }

    public int Check(Map<Symbol, Integer> symbols) {
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

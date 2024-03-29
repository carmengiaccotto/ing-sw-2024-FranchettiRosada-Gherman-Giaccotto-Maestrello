package it.polimi.ingsw.model;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import it.polimi.ingsw.model.ObjectiveCard;
import java.util.HashMap;
import java.util.Map;

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
    }

    public HashMap<Symbol, Integer > getGoal(){
         return goal;
    }

}

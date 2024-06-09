package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * This subclass represents a specific type of Objective Card which goal is to have certain symbols on the Player's PlayArea.
 * It extends the base ObjectiveCards class and provides additional customization.
 */
public class SymbolObjectiveCard extends ObjectiveCard {

    private final HashMap<Symbol, Integer> goal;



    /**
     * Class Constructor
     * */

    public SymbolObjectiveCard(int id, ObjectivePoints points, HashMap<Symbol, Integer> goal) {
        super(id,points);
        this.goal = goal;
    }



    public int CheckGoals(Map<Symbol, Integer> symbols) {
        int numberOfGoals = 0;// initialize to 0 the number of times the goal has been reached
        boolean ok = true;//initialize to true the boolean variable that will be used to check if the goal has been reached
        Map<Symbol, Integer> tmp = new HashMap<>(symbols);//initialize a temporary map that will be used to check if the goal has been reached

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

    /**
     * getter method for the ObjectiveCard's goal
     *
     * @return goal
     */
    public HashMap<Symbol, Integer > getGoal(){
         return goal;
    }

}

package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * This subclass represents a specific type of Objective Card which goal is to have certain symbols on the Player's PlayArea.
 * It extends the base ObjectiveCards class and provides additional customization.
 * Each SymbolObjectiveCard has a goal, which is a map of symbols to integers.
 */
public class SymbolObjectiveCard extends ObjectiveCard {

    private final HashMap<Symbol, Integer> goal;

    /**
     * Class Constructor.
     * Initializes the card with the given id, points, and goal.
     *
     * @param id The id of the card.
     * @param points The points of the card.
     * @param goal The goal of the card. It is a map of symbols to integers.
     */
    public SymbolObjectiveCard(int id, ObjectivePoints points, HashMap<Symbol, Integer> goal) {
        super(id,points);
        this.goal = goal;
    }

    /**
     * This method checks the goals of the card.
     * It counts the number of times the goal has been reached in the given map of symbols.
     *
     * @param symbols The map of symbols to check the goals against.
     * @return The number of times the goal has been reached.
     */
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
     * This method is a getter for the goal attribute.
     * The goal attribute represents the objective of the card, which is a map of symbols to integers.
     * Each symbol in the map represents a specific goal, and the corresponding integer represents the number of times that goal needs to be reached.
     *
     * @return The goal of the card. It is a map of symbols to integers.
     */
    public HashMap<Symbol, Integer > getGoal(){
         return goal;
    }

}

package it.polimi.ingsw.model;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import it.polimi.ingsw.model.ObjectiveCard;
import java.util.HashMap;

public class SymbolObjectiveCard extends ObjectiveCard {

    private final HashMap<Symbol, Integer> goal;

    public SymbolObjectiveCard(HashMap<Symbol, Integer> goal) {
        this.goal = goal;
    }

    public int Check(){
        int numberOfGoals;

        return numberOfGoals;

    }
    public HashMap<Symbol, Integer > getGoal(){

         return goal;
    }

}

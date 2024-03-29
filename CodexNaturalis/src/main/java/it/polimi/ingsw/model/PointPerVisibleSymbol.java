package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

public class PointPerVisibleSymbol extends GoldCard{
    private final Symbol goldGoal;

    public PointPerVisibleSymbol(HashMap<Symbol, Integer> requirement, int point, Symbol goldGoal) {
        super(requirement, point);
        this.goldGoal = goldGoal;
    }

    public int increasePoints(int point, Symbol goldGoal){
       return PlayArea.getNumSymbols(goldGoal) * point;
    }

    public Symbol getGoldGoal(){
        return goldGoal;
    }
}

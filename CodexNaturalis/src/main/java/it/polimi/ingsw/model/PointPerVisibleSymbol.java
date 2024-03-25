package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

public class PointPerVisibleSymbol extends GoldCard{
    private Symbol symbol;

    public PointPerVisibleSymbol(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color, HashMap<Symbol, Integer> requirement, int point, Symbol symbol) {
        super(symbols, corners, isInConfiguration, color, requirement, point);
        this.symbol = symbol;
    }

    public int increasePoints(int point, Symbol symbol){
       return getNumSymbol(symbol) * point;
    }

    public Symbol getGoldGoal(){
        return symbol;
    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

/** @author Alessia Franchetti-Rosada
 * This subclass represents a specific type of Gold Card that awards points for each goal present in the play area.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerVisibleSymbol extends GoldCard{
    private final Symbol goldGoal;

    public PointPerVisibleSymbol(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, HashMap<Symbol, Integer> requirement, int point, Symbol goldGoal) {
        super(symbols, corners, isInConfiguration, requirement, point);
        this.goldGoal = goldGoal;
    }

    public int increasePoints(int point, Symbol goldGoal){
       return PlayArea.getNumSymbols(goldGoal) * point;
    }
    //to revise + javadoc

    /**
     * getter method for the PointPerVisibleSymbol's goal
     *
     * @return goldGoal
     */
    public Symbol getGoldGoal(){
        return goldGoal;
    }
}

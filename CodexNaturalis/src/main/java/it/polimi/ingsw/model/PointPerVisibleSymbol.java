package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

/** This subclass represents a specific type of Gold Card that awards points for each goal present in the play area.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerVisibleSymbol extends GoldCard{
    private final Symbol goldGoal;

    /**Class Constructor*/
    public PointPerVisibleSymbol(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners, HashMap<Symbol, Integer> requirement, int point, Symbol goldGoal) {
        super(symbols, color, corners, requirement, point);
        this.goldGoal=goldGoal;
    }


    /**Method that returns the actual points that Playing this card provides, by multiplying the defaultPoints of the card
     * and the number of symbols present in the play area of the current player.
     * @param currentPlayer The current player whose points are to be increased.
     * @param point The value by which the points are increased for each matching symbol.
     * @param goldGoal The symbol for which the points are calculated.
     * @return The total points increased based on the number of matching symbols in the player's play area.
     **/
    public int increasePoints(Player currentPlayer, int point, Symbol goldGoal){
       return currentPlayer.getPlayArea().getNumSymbols(goldGoal) * point;
    }

    /**
     * getter method for the PointPerVisibleSymbol's goal
     *
     * @return goldGoal
     */
    public Symbol getGoldGoal(){
        return goldGoal;
    }
}

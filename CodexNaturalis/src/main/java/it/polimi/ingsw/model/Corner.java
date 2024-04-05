package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.Pair;

/** @author Carmen Giaccotto
 * Class that represents a corner on a play card.
 */

public class Corner {

    private Pair position;
    private Symbol symbol;
    private boolean hidden;
    private boolean covered;

    public Corner(Pair position, Symbol symbol, boolean hidden, boolean covered) {
        this.position = position;
        this.symbol = symbol;
        this.hidden = hidden;
        this.covered = covered;
    }

    /**
     * Corner's symbol setter Method
     *
     * @param symbol
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Checks if the corner is hidden.
     *
     * @return true if the corner is hidden, false otherwise
     */
    public boolean isHidden(){
        return hidden;
    }

    /**
     * Checks if the corner is covered by another card.
     *
     * @return true if the corner is covered, false otherwise
     */
    public boolean isCovered(){
        return covered;
    }

    /**
     * getter method for the corner's position inside a card.
     *
     * @return position
     */
    public Pair getPosition(){
       return position;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}





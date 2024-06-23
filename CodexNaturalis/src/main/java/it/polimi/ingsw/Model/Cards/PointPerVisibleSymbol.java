package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;

/** This subclass represents a specific type of Gold Card that awards points for each goal present in the play area.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerVisibleSymbol extends GoldCard {
    private final Symbol goldGoal;

    /**Class Constructor*/

    public PointPerVisibleSymbol(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point, Symbol goldGoal) {
        super(id, front, back, color, requirement, point);
        this.goldGoal=goldGoal;
    }




    /**
     * Method that returns the actual points that Playing this card provides, by multiplying the defaultPoints of the card
     * and the number of symbols present in the play area of the current player.
     *
     * @param playArea playArea we are checking On
     * @return The total points increased based on the number of matching symbols in the player's play area.
     *
     * */

    public int increasePoints(PlayArea playArea){
       return playArea.getNumSymbols(goldGoal) * getPoints(Side.FRONT);
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

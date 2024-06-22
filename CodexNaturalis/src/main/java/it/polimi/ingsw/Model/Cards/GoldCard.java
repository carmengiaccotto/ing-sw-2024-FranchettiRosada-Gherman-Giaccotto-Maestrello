package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Symbol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** This subclass represents the Gold Cards and extends the SideOfCard class*/
public class GoldCard extends PlayCard implements Serializable {
    private final HashMap<Symbol, Integer> requirement;
    private int point;


    /**Class Constructor*/
    public GoldCard(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point) {
        super(id, front, back, color);
        this.requirement= requirement;
        this.point=point;
    }




    /**
     * getter method for the GoldCard's point
     *
     * @return point
     */
    @Override
    public int getPoints(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return point;
        else
            return 0;
    }




    /**
     * This method checks whether the requirement of the Gold Card is met by the player's play area.
     * If the result is true, the card can be used by the player, otherwise not.
     *
     * @param symbols a map containing symbols and their counts in the player's play area
     * @return true if the requirement is met, false otherwise
     */

    public boolean checkRequirement(Map<Symbol, Integer> symbols, Side chosenSide){
        if(chosenSide.equals(Side.FRONT)){//only the front side has a requirement to be placed
            for(Symbol key : requirement.keySet()) {//for each symbol in the requirement map
                if(!symbols.containsKey(key))//if the symbol is not in the play area symbols map return false
                    return false;
                else{
                    if(symbols.get(key)<requirement.get(key))//if the number of symbols in the play area is less than the requirement return false
                        return false;
                }
            }
            return true;//if all the symbols are in the play area and their number is greater or equal to the requirement return true
        }
        else return true;//if the chosen side is the back side return true
    }




    /**Method to calculate the points that the placing of the card gives
     * Override in PointPerCoveredCorner and PointPerVisibleSymbol classes
     * @param point points to be added
     * @return int points to add to Player's score*/

    public int increasePoints (int point) { return this.point; }




    /**
     * Getter method for the GoldCard's requirement
     *
     * @return requirement
     */

    public HashMap<Symbol, Integer> getRequirement(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return requirement;
        else
            return null;
    }
}

package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Gold Card in the game.
 * It extends the PlayCard class and implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each Gold Card has a requirement, which is a map of symbols and their counts, and a point value.
 */
public class GoldCard extends PlayCard implements Serializable {
    private final HashMap<Symbol, Integer> requirement;
    private int point;

    /**
     * Class Constructor.
     * Initializes the card with the given id, front, back, color, requirement, and point.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     * @param requirement The requirement of the card.
     * @param point The point value of the card.
     */
    public GoldCard(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point) {
        super(id, front, back, color);
        this.requirement= requirement;
        this.point=point;
    }

    /**
     * This method returns the point value of the Gold Card.
     *
     * @param chosenSide The chosen side of the card.
     * @return The point value of the card if the chosen side is the front, 0 otherwise.
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
     * @param chosenSide The chosen side of the card.
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

    /**
     * This method calculates the points that the placing of the card gives.
     *
     * @param point points to be added
     * @return The points to add to the player's score.
     */
    public int increasePoints (int point) {
        return this.point;
    }

    /**
     * This method returns the requirement of the Gold Card.
     *
     * @param chosenSide The chosen side of the card.
     * @return The requirement of the card if the chosen side is the front, null otherwise.
     */
    public HashMap<Symbol, Integer> getRequirement(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return requirement;
        else
            return null;
    }
}

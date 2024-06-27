package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;

/**
 * This subclass represents a specific type of Gold Card that awards points for each goal present in the play area.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 * Each PointPerVisibleSymbol card has a goldGoal, which is the symbol that the card awards points for.
 */
public class PointPerVisibleSymbol extends GoldCard {

    private final Symbol goldGoal;

    /**
     * Class Constructor.
     * Initializes the card with the given id, front side, back side, color, requirement, point, and goldGoal.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     * @param requirement The requirement of the card.
     * @param point The point value of the card.
     * @param goldGoal The symbol that the card awards points for.
     */
    public PointPerVisibleSymbol(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point, Symbol goldGoal) {
        super(id, front, back, color, requirement, point);
        this.goldGoal=goldGoal;
    }

    /**
     * This method calculates the points that the player earns when playing this card.
     * The points are calculated by multiplying the default points of the card by the number of symbols present in the player's play area.
     * The symbol that is counted is the one specified by the goldGoal attribute of this card.
     *
     * @param playArea The play area of the current player. This is where we count the number of symbols.
     * @return The total points increased based on the number of matching symbols in the player's play area.
     */
    public int increasePoints(PlayArea playArea){
       return playArea.getNumSymbols(goldGoal) * getPoints(Side.FRONT);
    }

    /**
     * This method returns the symbol that this card awards points for.
     * This symbol is specified by the goldGoal attribute of this card.
     *
     * @return The symbol that this card awards points for.
     */
    public Symbol getGoldGoal(){
        return goldGoal;
    }
}

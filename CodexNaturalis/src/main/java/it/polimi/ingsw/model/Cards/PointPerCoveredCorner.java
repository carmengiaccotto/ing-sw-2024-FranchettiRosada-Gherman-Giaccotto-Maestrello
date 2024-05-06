package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;

import java.util.HashMap;

/**
 * This subclass represents a specific type of Gold Card that awards points for each corner covered by this card.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerCoveredCorner extends GoldCard {


    /**Class Constructor*/
    public PointPerCoveredCorner(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point) {
        super(id, front, back, color, requirement, point);
    }





    /**Method used to get the number of corners the card covers when it is placed.
     * @return coveredCorners obtained by checking which one of the corners of the current card has nextCorner!=null at
     * nextCorner is set when placing the Card on the area and checking the neighbours*/
    public int findCoveredCorners(){
        int coveredCorners=0;
        for(Corner[] cornerRow: this.getFront().getCorners() ){
            for (Corner corner: cornerRow){
                if (corner.getNextCorner()!=null)
                    coveredCorners+=1;
            }
        }
        return coveredCorners;
    }

   /**Method that returns the actual points that Playing this card provides, by multiplying the defaultPoints
    * and the corners the card covers
    * @param point default points
    * @return int the points that are to be added to the Player's score
    **/
    public int increasePoints(int point){
        return findCoveredCorners() * point;
    }

}


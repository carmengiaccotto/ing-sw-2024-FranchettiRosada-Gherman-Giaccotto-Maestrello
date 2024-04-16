package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

/** @author Alessia Franchetti-Rosada
 * This subclass represents a specific type of Gold Card that awards points for each corner covered by this card.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerCoveredCorner extends GoldCard{
    private int coveredCorners;

    public PointPerCoveredCorner(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners, HashMap<Symbol, Integer> requirement, int point) {
        super(symbols, color, corners, requirement, point);
    }


    /**@author Denisa Minodora Gherman
     * Method used to get the number of corners the card covers when it is placed.
     * @return coveredCorners obtained by checking which one of the corners of the current card has nextCorner!=null at
     * nextCorner is set when placing the Card on the area and checking the neighbours*/
    public int findCoveredCorners(){
        int coveredCorners=0;
        for(Corner[] cornerRow: this.getCorners() ){
            for (Corner corner: cornerRow){
                if (corner.getNextCorner()!=null)
                    coveredCorners+=1;
            }
        }
        return coveredCorners;
    }

    //to revise + javadoc
    public int increasePoints(int point, int coveredCorners){
        return coveredCorners * point;
    }

}


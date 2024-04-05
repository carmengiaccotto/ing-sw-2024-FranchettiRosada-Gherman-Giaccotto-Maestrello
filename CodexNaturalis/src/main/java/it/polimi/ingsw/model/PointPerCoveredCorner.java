package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

/** @author Alessia Franchetti-Rosada
 * This subclass represents a specific type of Gold Card that awards points for each corner covered by this card.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerCoveredCorner extends GoldCard{
    private int coveredCorners = 0;

    public PointPerCoveredCorner(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, HashMap<Symbol, Integer> requirement, int point, int coveredCorners) {
        super(symbols, corners, isInConfiguration, requirement, point);
        this.coveredCorners = coveredCorners;
    }

    //to revise + javadoc
    public int findCoveredCorners(){  //to revise
        for (Corner[] corner : corners) {
            for (Corner value : corner) {
                if (value.coverOtherCorner) {
                    coveredCorners++;
                }
            }
        }
        return coveredCorners;
    }

    //to revise + javadoc
    public int increasePoints(int point, int coveredCorners){
        return coveredCorners * point;
    }

}


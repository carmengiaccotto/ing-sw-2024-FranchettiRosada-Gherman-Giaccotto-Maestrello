package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

public class PointPerCoveredCorner extends GoldCard{
    private int coveredCorners = 0;

    public PointPerCoveredCorner(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color, HashMap<Symbol, Integer> requirement, int point, int coveredCorners) {
        super(symbols, corners, isInConfiguration, color, requirement, point);
        this.coveredCorners = coveredCorners;
    }

    public int findCoveredCorners(){
        for (Corner[] corner : corners) {
            for (Corner value : corner) {
                if (value.coverOtherCorner) {
                    coveredCorners++;
                }
            }
        }
        return coveredCorners;
    }

    public int increasePoints(int point, int coveredCorners){
        return coveredCorners * point;
    }

}


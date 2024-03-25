package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

public class PointPerCoveredCorner extends GoldCard{
    private int coveredCorners;

    public PointPerCoveredCorner(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color, HashMap<Symbol, Integer> requirement, int point, int coveredCorners) {
        super(symbols, corners, isInConfiguration, color, requirement, point);
        this.coveredCorners = coveredCorners;
    }

    public int increasePoints(int point, int corners){
        return corners * point;
    }

    public int getCoveredCorners(){    //to revise
        return coveredCorners;
    }

    public void setCoveredCorners(int corners) {   //to revise
        this.corners = corners;
    }

    public int findCoveredCorners(){      //to revise

    }



}


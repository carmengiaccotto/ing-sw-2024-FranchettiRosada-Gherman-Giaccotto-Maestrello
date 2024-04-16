package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;


/** @author Alessia Franchetti-Rosada
 * This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends SideOfCard {
    private final boolean point;


    public ResourceCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners, boolean point) {
        super(symbols, color, corners);
        this.point=point;

    }


    /**
     * getter method for the ResourceCard's point
     *
     * @return point
     */
    public boolean getPoint(){
        return point;
    }
}

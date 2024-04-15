package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Pair;

import java.util.HashMap;


/** @author Alessia Franchetti-Rosada
 * This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends SideOfCard {
    private final boolean point;

    public ResourceCard(HashMap<Symbol, Integer> symbols, Pair<Integer, Integer> positionOnArea, Colors color, boolean point) {
        super(symbols, positionOnArea, color);
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

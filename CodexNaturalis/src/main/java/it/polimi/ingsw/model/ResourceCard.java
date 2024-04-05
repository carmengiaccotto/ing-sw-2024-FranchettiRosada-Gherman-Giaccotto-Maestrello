package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import java.util.HashMap;


/** @author Alessia Franchetti-Rosada
 * This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends SideOfCard {
    private final boolean point;

    public ResourceCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, boolean point) {
        super(symbols, corners, isInConfiguration);
        this.point = point;
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

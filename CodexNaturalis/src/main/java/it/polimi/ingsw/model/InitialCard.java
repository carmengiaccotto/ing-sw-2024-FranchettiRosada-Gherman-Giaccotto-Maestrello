package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import java.util.HashMap;

/** @author Alessia Franchetti-Rosada
 * This subclass represents the Initial Cards and extends the SideOfCard class*/
public class InitialCard extends SideOfCard {

    public InitialCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, CardColors color) {
        super(symbols, corners, isInConfiguration, color);
    }

    public void playInitialCard(){  //to revise + javadoc

    }
}

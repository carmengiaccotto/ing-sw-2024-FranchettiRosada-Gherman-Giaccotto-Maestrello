package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Corner[][];
import java.util.HashMap;

public class GoldCard extends PlayCard{
    private HashMap<Symbol, Integer> requirement;
    private int point;

    public GoldCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color, HashMap<Symbol, Integer> requirement, int point) {
        super(symbols, corners, isInConfiguration, color);
        this.requirement = requirement;
        this.point = point;
    }

    public void play(){        //to revise

    }

    public int getPoints(){
        return point;
    }
    
    public int increasePoints(){
        //to revise
    }
    
    public void checkRequirement(){    //boolean or void?
        
    }

    public HashMap<Symbol, Integer> getRequirement(){
        return requirement;
    }
}

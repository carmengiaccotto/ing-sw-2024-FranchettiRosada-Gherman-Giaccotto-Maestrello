package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class GoldCard extends SideOfCard {
    private final HashMap<Symbol, Integer> requirement;
    private final int point;

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
    
    public void increasePoints(){

    }
    
    public boolean checkRequirement(Map<Symbol, Integer> symbols){
        boolean ok = true;

            for (Symbol key : requirement.keySet()) {
                if (symbols.containsKey(key)) {
                    if (symbols.get(key) < requirement.get(key)) {
                        return ok = false;
                    }
                }else{
                    return ok = false;
                }
            }
        return ok;
    }

    public HashMap<Symbol, Integer> getRequirement(){
        return requirement;
    }
}

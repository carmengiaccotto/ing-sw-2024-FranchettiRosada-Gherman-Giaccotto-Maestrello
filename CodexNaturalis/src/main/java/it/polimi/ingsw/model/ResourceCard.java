package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

public class ResourceCard extends PlayCard{
    private boolean point;

    public ResourceCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color, boolean point) {
        super(symbols, corners, isInConfiguration, color);
        this.point = point;
    }

    public void play(){        //to revise

    }

    public boolean getPoint(){
        return point;
    }
}

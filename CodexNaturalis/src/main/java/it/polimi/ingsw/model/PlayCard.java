package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Card;

import java.util.HashMap;

public class PlayCard extends Card{
    private HashMap<Symbol, Integer> symbols;
    private Corner[][] corners;
    private boolean isInConfiguration;
    private Colors color;

    public PlayCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color) {
        this.symbols = symbols;
        this.corners = corners;
        this.isInConfiguration = isInConfiguration;
        this.color = color;
    }

    public HashMap<Symbol, Integer> getSymbols(){
        return symbols;
    }

    public Corner[][] getCorners(){
        return corners;
    }

    public void play(){        //to revise

    }

    public boolean isInConfig(){
        //to revise
    }

    public boolean resetConfig(){
        isInConfiguration = false;
        return isInConfiguration;
    }
}

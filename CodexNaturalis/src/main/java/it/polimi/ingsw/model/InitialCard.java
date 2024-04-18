package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import com.google.gson.JsonObject;

import java.util.HashMap;

/** @author Alessia Franchetti-Rosada
 * This subclass represents the Initial Cards and extends the SideOfCard class*/
public  class InitialCard extends SideOfCard {

    /**Class Constructor*/
    public InitialCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners) {
        super(symbols, color, corners);
    }

    public InitialCard mapFromJson(JsonObject jsonObject){
       SideOfCard side= super.mapFromJson(jsonObject);
       return new InitialCard(side.getSymbols(),side.getColor(), side.getCorners());
    }
}

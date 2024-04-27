package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import com.google.gson.JsonObject;

import java.util.HashMap;


/** This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends SideOfCard {
    private boolean point;

    /**Class Constructor*/
    public ResourceCard(HashMap<Symbol, Integer> symbols, CardColors color, Corner[][] corners, boolean point) {
        super(symbols, null, corners);
        this.point=point;

    }

    public ResourceCard mapFromJson(JsonObject jsonObject) {
        SideOfCard sideOfCard = super.mapSideFromJson(jsonObject);
        this.point=jsonObject.get("point").getAsBoolean();
        return new ResourceCard(sideOfCard.getSymbols(), sideOfCard.getColor(), sideOfCard.getCorners(),this.point );
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

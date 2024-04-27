package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import com.google.gson.JsonObject;

/** This subclass represents the Initial Cards and extends the SideOfCard class*/
public  class InitialCard extends PlayCard {

    public InitialCard(int id, SideOfCard front, SideOfCard back, CardColors color) {
        super(id, front, back, color);
    }

    /**Class Constructor*/


    public InitialCard mapFromJson(JsonObject jsonObject){
        InitialCard initialCard = (InitialCard) super.mapFromJson(jsonObject);
        return initialCard;
    }


}

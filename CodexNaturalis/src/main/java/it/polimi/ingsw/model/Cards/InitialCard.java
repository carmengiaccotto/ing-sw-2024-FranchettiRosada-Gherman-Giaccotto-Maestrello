package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import com.google.gson.JsonObject;

/** This subclass represents the Initial Cards and extends the SideOfCard class*/
public  class InitialCard extends PlayCard {




    /**Class Constructor*/
    public InitialCard(int id, SideOfCard front, SideOfCard back, CardColors color) {
        super(id, front, back, color);
    }





    public InitialCard mapFromJson(JsonObject jsonObject){
        super.mapFromJson(jsonObject);
        JsonObject frontObject = jsonObject.getAsJsonObject("front");
        if (frontObject != null) {
            this.getFront().mapSideFromJson(frontObject);
        }

        JsonObject backObject = jsonObject.getAsJsonObject("back");
        if (backObject != null) {
            this.getBack().mapSideFromJson(backObject);
        }

        return this;
    }


}

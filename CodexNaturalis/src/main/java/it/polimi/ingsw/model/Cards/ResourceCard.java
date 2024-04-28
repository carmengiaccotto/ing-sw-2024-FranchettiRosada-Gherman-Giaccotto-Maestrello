package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;
import com.google.gson.JsonObject;


/** This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends PlayCard {
    private final boolean FrontPoint;


    /**
     * Class Constructor
     */

    public ResourceCard(int id, SideOfCard front, SideOfCard back, CardColors color, boolean FrontPoint) {
        super(id, front, back, color);
        this.FrontPoint = FrontPoint;

    }




    public ResourceCard mapFromJson(JsonObject jsonObject) {
        PlayCard playCard = super.mapFromJson(jsonObject);
        JsonObject FrontCard = jsonObject.getAsJsonObject("front");
        boolean FrontPoint = FrontCard.get("point").getAsBoolean();
        return new ResourceCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), FrontPoint);


    }


    /**
     * getter method for the ResourceCard's point
     *
     * @return point
     */
    public boolean getPoint(Side chosenSide) {
        if (chosenSide.equals(Side.FRONT))
            return FrontPoint;
        if (chosenSide.equals(Side.BACK))
            return false;
        else
            throw new IllegalArgumentException("Invalid Side Chosen");
    }

}




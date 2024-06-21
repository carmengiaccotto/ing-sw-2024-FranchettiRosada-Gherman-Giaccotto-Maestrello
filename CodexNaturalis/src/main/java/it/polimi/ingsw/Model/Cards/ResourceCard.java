package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;

import java.io.Serializable;


/** This subclass represents the Resource Cards and extends the SideOfCard class*/
public class ResourceCard extends PlayCard implements Serializable {
    private boolean point;

    /**Class Constructor*/
    public ResourceCard(int id, SideOfCard front, SideOfCard back, CardColors color, boolean point) {
        super(id, front, back, color);
        this.point=point;
    }




    /**
     * Getter method for the ResourceCard's point
     *
     * @return point if the card has been played on the Front Side, false otherwise
     */
    @Override
    public int getPoints(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return this.point? 1 : 0;
        else return 0;
    }
}

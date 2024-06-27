package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;

import java.io.Serializable;

/**
 * This class represents a Resource Card in the game.
 * It extends the PlayCard class and implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each Resource Card has a point, which indicates whether the card has been played on the front side.
 */
public class ResourceCard extends PlayCard implements Serializable {

    private boolean point;

    /**
     * Class Constructor.
     * Initializes the card with the given id, front side, back side, color, and point.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     * @param point The point of the card. It indicates whether the card has been played on the front side.
     */
    public ResourceCard(int id, SideOfCard front, SideOfCard back, CardColors color, boolean point) {
        super(id, front, back, color);
        this.point=point;
    }

    /**
     * This method returns the points of the chosen side of the card.
     * If the card has been played on the front side, it returns 1; otherwise, it returns 0.
     *
     * @param chosenSide The chosen side of the card.
     * @return The points of the chosen side of the card.
     */
    @Override
    public int getPoints(Side chosenSide){
        if(chosenSide.equals(Side.FRONT))
            return this.point? 1 : 0;
        else return 0;
    }
}

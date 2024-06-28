package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;

/**
 * This class represents a Play Card in the game.
 * Each card is a Pair of SideOfCard, where the first element is the front and the second element is the back.
 * It extends the Card class.
 */
public class PlayCard extends Card {

    private final SideOfCard Front;
    private final SideOfCard Back;
    private final CardColors color;

    /**
     * Class Constructor.
     * Initializes the card with the given id, front side, back side, and color.
     * It also sets the color and parent card of the front and back sides.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     */
    public PlayCard(int id,SideOfCard front, SideOfCard back, CardColors color) {
        super(id);
        Front = front;
        if(front!=null) {
            front.setColor(color);
            front.setParentCard(this);
        }
        Back = back;
        if(back!=null) {
            back.setColor(color);
            back.setParentCard(this);
        }

        this.color = color;
    }

    /**
     * This method allows the user to choose the side of the card to play.
     *
     * @param sideToPlay The side of the card to play (either {@code Side.FRONT} or {@code Side.BACK}).
     * @return The chosen side of the card.
     */
    public SideOfCard chooseSide(Side sideToPlay){
        if (sideToPlay == Side.FRONT) return Front;
        else{
            return Back;
        }
    }

    /**
     * Getter method for Color attribute.
     *
     * @return The color of the card.
     */
    public CardColors getColor() {
        return color;
    }

    /**
     * Getter method for Front attribute.
     *
     * @return The front side of the card.
     */
    public SideOfCard getFront(){
        return Front;
    }

    /**
     * Getter method for Back attribute.
     *
     * @return The back side of the card.
     */
    public SideOfCard getBack() {
        return Back;
    }

    /**
     * This method returns the points of the chosen side of the card.
     * It is meant to be overridden by subclasses.
     *
     * @param chosenSide The chosen side of the card.
     * @return The points of the chosen side of the card. Returns 0 by default.
     */
    public int getPoints(Side chosenSide){
        return 0;
    }
}



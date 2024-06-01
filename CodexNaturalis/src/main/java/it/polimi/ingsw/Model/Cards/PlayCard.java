package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;

/** Class that represents a play Card of the game. Each card is a Pair of sideOfCard,
 * where the first element is the front and the second element is the back.
 */
public class PlayCard extends Card {

    private SideOfCard Front;
    private  SideOfCard Back;



    private CardColors color;


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
     * Method that allows to choose the side of the card to play.
     *
     * @param sideToPlay the side of the card to play (either {@code Side.FRONT} or {@code Side.BACK})
     * @return the chosen side of the card
     */
    public SideOfCard chooseSide(Side sideToPlay){
        if (sideToPlay == Side.FRONT) return Front;
        else{
            return Back;
        }
    }

  /**Getter Method for Color Attribute
   * @return  color of the card*/
    public CardColors getColor() {
        return color;
    }

    /**Getter Method for Front Attribute
     * @return Front: front side of the Card*/

    public SideOfCard getFront(){
        return Front;
    }

    /**Getter Method for Back Attribute
     * @return Back: back side of the Card*/

    public SideOfCard getBack() {
        return Back;
    }
}



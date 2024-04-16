package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.Optional;

/** @author Carmen Giaccotto
 * Class that represents a play Card of the game. Each card is a Pair of sideOfCard,
 * where the first element is the front and the second element is the back.
 */
public class Card {

    private final SideOfCard Front;
    private final SideOfCard Back;

    private int idCard;

    private final  Optional<CardColors> color;

    public Card( SideOfCard front, SideOfCard back, int idCard, Optional<CardColors> color) {
        Front = front;
        Back = back;
        this.idCard = idCard;
        this.color = color;
    }

    /**
     * getter method for the card's Id.
     *
     * @return idCard
     */
    public int getIdCard() {
        return idCard;
    }

    /**
     * Card's Id setter Method
     *
     * @param idCard
     */
    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    /**
     * Retrieves the pair of sides associated with the card.
     *
     * @return a Pair containing the two sides of the card, front and back.
     */

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

    public Optional<CardColors> getColor() {
        return color;
    }
}



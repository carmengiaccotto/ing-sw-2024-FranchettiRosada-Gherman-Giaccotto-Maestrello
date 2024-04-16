package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.Optional;

/** @author Carmen Giaccotto
 * Class that represents a play Card of the game. Each card is a Pair of sideOfCard,
 * where the first element is the front and the second element is the back.
 */
public class Card {

    private final Pair<SideOfCard, SideOfCard> sides;

    private int idCard;

    private  Optional<CardColors> color;

    public Card(Pair<SideOfCard, SideOfCard> sides, int idCard, Optional<CardColors> color) {
        this.sides = sides;
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
    public Pair<SideOfCard, SideOfCard> getSides() {
        return sides;
    }

    /**
     * Method that allows to choose the side of the card to play.
     *
     * @param sideToPlay the side of the card to play (either {@code Side.FRONT} or {@code Side.BACK})
     * @return the chosen side of the card
     */
    public SideOfCard chooseSide(Side sideToPlay){
        if (sideToPlay == Side.FRONT) return sides.getFirst();
        else{
            return sides.getSecond();
        }
    }

}



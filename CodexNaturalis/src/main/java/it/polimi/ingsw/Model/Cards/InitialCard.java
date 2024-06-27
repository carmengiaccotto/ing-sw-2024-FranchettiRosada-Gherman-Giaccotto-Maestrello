package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;

/**
 * This class represents an Initial Card in the game.
 * It extends the PlayCard class.
 * Each Initial Card has an id, a front side, a back side, and a color.
 */
public  class InitialCard extends PlayCard {

    /**
     * Class Constructor.
     * Initializes the card with the given id, front side, back side, and color.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     */
    public InitialCard(int id, SideOfCard front, SideOfCard back, CardColors color) {
        super(id, front, back, color);
    }
}

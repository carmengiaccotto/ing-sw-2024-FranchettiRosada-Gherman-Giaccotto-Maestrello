package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

/**
 * This interface represents a Position in the game.
 * It provides a method to get the neighbour card of a given card in a given play area.
 */
public interface Position {

    /**
     * This method returns the neighbour card of a given central card in a given play area.
     *
     * @param temporaryCentralCard The central card for which the neighbour card is to be found.
     * @param playArea The play area in which the neighbour card is to be found.
     * @return The neighbour card of the given central card in the given play area.
     */
    SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea);
}

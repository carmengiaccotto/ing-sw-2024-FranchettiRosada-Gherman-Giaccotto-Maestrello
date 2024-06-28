package it.polimi.ingsw.Model.Enumerations;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Position;

/**
 * This enumeration represents the possible positions of a card in the game relative to another card.
 * It implements the Position interface.
 * A card can be either in the UP position or the DOWN position relative to another card.
 */
public enum UpDownPosition implements Position {

    /**
     * Represents the UP position of a card relative to another card.
     * It overrides the getNeighbourCard method from the Position interface.
     */
    UP{

        @Override
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea){
            Pair<Integer, Integer> currentPosition=temporaryCentralCard.getPositionOnArea();
            Pair<Integer, Integer> coordinatesToCheck=new Pair<>(currentPosition.getFirst()-2, currentPosition.getSecond());
            int rowToCheck=coordinatesToCheck.getFirst();
            int columnToCheck=coordinatesToCheck.getSecond();
            return playArea.getCardInPosition(rowToCheck,columnToCheck);
        }
    },

    /**
     * Represents the DOWN position of a card relative to another card.
     * It overrides the getNeighbourCard method from the Position interface.
     */
    DOWN{
        @Override
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            Pair<Integer, Integer> currentPosition=temporaryCentralCard.getPositionOnArea();
            Pair<Integer, Integer> coordinatesToCheck=new Pair<>(currentPosition.getFirst()+2, currentPosition.getSecond());
            int rowToCheck=coordinatesToCheck.getFirst();
            int columnToCheck=coordinatesToCheck.getSecond();
            return playArea.getCardInPosition(rowToCheck,columnToCheck);
        }


    },
    ;

    /**
     * This method is used to get the card that is in a specific UpDownPosition compared to the current one.
     * It is an overloaded method.
     *
     * @param temporaryCentralCard The card whose position we are checking.
     * @param playArea The play area where the cards are placed.
     * @return The card in the given position.
     */
    public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            return temporaryCentralCard;


    }

}

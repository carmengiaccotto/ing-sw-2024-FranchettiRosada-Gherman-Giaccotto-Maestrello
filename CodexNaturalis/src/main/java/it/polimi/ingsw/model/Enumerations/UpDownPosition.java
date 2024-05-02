package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayGround.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Position;

/**Enumeration used for the Dispositions*/
public enum UpDownPosition implements Position {
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



    /**Method to get the card that is in a specific UpDownPosition compared to the current one
     * Overload method
     * @param playArea PlayArea we are currently checking.
     * @return card the card in the given position*/
    public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            return temporaryCentralCard;


    }

}

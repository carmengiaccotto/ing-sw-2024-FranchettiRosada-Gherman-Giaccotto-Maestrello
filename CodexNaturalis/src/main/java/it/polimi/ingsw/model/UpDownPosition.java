package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

/**Enumeration used for the Dispositions*/
public enum UpDownPosition implements Position {
    UP{

        @Override
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
            Pair<Integer, Integer> currentPosition=temporaryCentralCard.getPositionOnArea();
            Pair<Integer, Integer> coordinatesToCheck=new Pair<>(currentPosition.getFirst()-1, currentPosition.getSecond());
            int rowToCheck=coordinatesToCheck.getFirst();
            int columnToCheck=coordinatesToCheck.getSecond();
            return playArea.getCardInPosition(rowToCheck,columnToCheck);
        }



    },
    DOWN{
        @Override
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea) {
            Pair<Integer, Integer> currentPosition=temporaryCentralCard.getPositionOnArea();
            Pair<Integer, Integer> coordinatesToCheck=new Pair<>(currentPosition.getFirst()+1, currentPosition.getSecond());
            int rowToCheck=coordinatesToCheck.getFirst();
            int columnToCheck=coordinatesToCheck.getSecond();
            return playArea.getCardInPosition(rowToCheck,columnToCheck);
        }


    },
    ;



    /**Method to get the card that is in a specific UpDownPosition compared to the current one
     * Overload method
     * @param positionToCheck the position we want to get
     * @param playArea PlayArea we are currently checking.
     * @return card the card in the given position*/
    public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea) {
            return temporaryCentralCard;


    }

}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

/**Enumeration used for the Dispositions*/
public enum UpDownPosition {
    UP{
        @Override
        public Pair<Integer, Integer> PositionToCheck(SideOfCard currentCard){
            Pair<Integer, Integer> currentPosition=currentCard.getPositionOnArea();
            return new Pair<>(currentPosition.getFirst()-1, currentPosition.getSecond());

        }

    },
    DOWN{
        @Override
        public Pair<Integer, Integer> PositionToCheck(SideOfCard currentCard){
            Pair<Integer, Integer> currentPosition=currentCard.getPositionOnArea();
            return new Pair<>(currentPosition.getFirst()+1, currentPosition.getSecond());

        }


    },
    ;


    public Pair<Integer, Integer> PositionToCheck(SideOfCard currentCard){
        return currentCard.getPositionOnArea();
    }

}

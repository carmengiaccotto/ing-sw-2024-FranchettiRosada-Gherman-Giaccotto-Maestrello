package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public enum CornerPosition {
    TOPLEFT{

        public CornerPosition CoverCorners(){
            return BOTTOMRIGHT;
        }

        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
                    currentCard.getPositionOnArea().getSecond()-1);
        }


    },
    BOTTOMLEFT{
        public CornerPosition CoverCorners(){
            return TOPRIGHT;
        }

        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
                    currentCard.getPositionOnArea().getSecond()-1);
        }

    },
    TOPRIGHT{

        public CornerPosition CoverCorners(){
            return BOTTOMLEFT;
        }
        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
                    currentCard.getPositionOnArea().getSecond()+1);
        }
    },

    BOTTOMRIGHT{
        public CornerPosition CoverCorners(){
            return TOPLEFT;
        }

        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
                    currentCard.getPositionOnArea().getSecond()+1);
        }
    },
    ;


    public CornerPosition CoverCorners(){
        return null;
    }
    public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
        return null;
    }
}

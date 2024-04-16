package CodexNaturalis.src.main.java.it.polimi.ingsw.model;


    public enum CornerPosition implements Position {
        TOPLEFT{

            public CornerPosition CoverCorners(){
                return BOTTOMRIGHT;
            }
            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
                return temporaryCentralCard.getCornerInPosition((CornerPosition) positionToCheck).getNextCorner().getParentCard();
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

            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
                return temporaryCentralCard.getCornerInPosition((CornerPosition) positionToCheck).getNextCorner().getParentCard();
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


            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
                return temporaryCentralCard.getCornerInPosition((CornerPosition) positionToCheck).getNextCorner().getParentCard();
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

            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
                return temporaryCentralCard.getCornerInPosition((CornerPosition) positionToCheck).getNextCorner().getParentCard();
            }
        },
        ;


        public CornerPosition CoverCorners(){
            return null;
        }
        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return null;
        }








        /**Method to get the card that is in a specific CornerPosition compared to the current one
         * @param positionToCheck the position we want to get
         * @param playArea PlayArea we are currently checking. We do not use it here,
         *                   but need to put the parameter for DispositionCheck purpose
         * @return card the card in the given position*/
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea){
            return temporaryCentralCard.getCornerInPosition((CornerPosition) positionToCheck).getNextCorner().getParentCard();
        }

    }





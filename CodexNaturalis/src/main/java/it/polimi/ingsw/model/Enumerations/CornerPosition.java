package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Position;

/**CornerPosition Enum*/
    public enum CornerPosition implements Position {
        /**Corner in position [0][0] on the corners matrix in SideOfCard*/
        TOPLEFT{

            public CornerPosition CoverCorners(){
                return BOTTOMRIGHT;
            }
            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
                Corner corner = temporaryCentralCard.getCornerInPosition(this);
                if (corner != null) {
                    Corner nextCorner = corner.getNextCorner();
                    if (nextCorner != null) {
                        SideOfCard parentCard = nextCorner.getParentCard();
                        if (parentCard != null) {
                            return parentCard;
                        }
                    }
                }
                return null;
            }


            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
                return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
                        currentCard.getPositionOnArea().getSecond()-1);
            }


        },
        /**Corner in position [1][0] on the corners matrix in SideOfCard*/
        BOTTOMLEFT{
            public CornerPosition CoverCorners(){
                return TOPRIGHT;
            }

            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
                return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
                        currentCard.getPositionOnArea().getSecond()-1);
            }

            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
                Corner corner = temporaryCentralCard.getCornerInPosition(this);
                if (corner != null) {
                    Corner nextCorner = corner.getNextCorner();
                    if (nextCorner != null) {
                        SideOfCard parentCard = nextCorner.getParentCard();
                        if (parentCard != null) {
                            return parentCard;
                        }
                    }
                }
                return null;
            }

        },
    /**Corner in position [0][1] on the corners matrix in SideOfCard*/
        TOPRIGHT{

            public CornerPosition CoverCorners(){
                return BOTTOMLEFT;
            }
            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
                return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
                        currentCard.getPositionOnArea().getSecond()+1);
            }


        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            Corner corner = temporaryCentralCard.getCornerInPosition(this);
            if (corner != null) {
                Corner nextCorner = corner.getNextCorner();
                if (nextCorner != null) {
                    SideOfCard parentCard = nextCorner.getParentCard();
                    if (parentCard != null) {
                        return parentCard;
                    }
                }
            }
            return null;
        }
        },
    /**Corner in position [1][1] on the corners matrix in SideOfCard*/

        BOTTOMRIGHT{
            public CornerPosition CoverCorners(){
                return TOPLEFT;
            }

            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
                return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
                        currentCard.getPositionOnArea().getSecond()+1);
            }

        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            Corner corner = temporaryCentralCard.getCornerInPosition(this);
            if (corner != null) {
                Corner nextCorner = corner.getNextCorner();
                if (nextCorner != null) {
                    SideOfCard parentCard = nextCorner.getParentCard();
                    if (parentCard != null) {
                        return parentCard;
                    }
                }
            }
            return null;
        }
        },
        ;



        /**CoverCorners method:
         *  A card can not cover any corner of any other card, but only a specific one, else the move is not valid
         * @return CornerPosition only corner on the card that can be covered by the current one without violating
         * the rules of the game*/
        public CornerPosition CoverCorners(){
            return null;
        }

        /**Method that individuates the position of the newly placed card on the area
         * @param currentCard card that is already o the playArea, and is having a corner covered
         * @return coordinates of the newly placed card*/
        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
            return null;
        }



        /**Method to get the card that is in a specific CornerPosition compared to the current one
         * @param playArea PlayArea we are currently checking. We do not use it here,
         *                   but need to put the parameter for DispositionCheck purpose
         * @return card the card in the given position*/
        public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
            Corner corner = temporaryCentralCard.getCornerInPosition(this);
            if (corner != null) {
                Corner nextCorner = corner.getNextCorner();
                if (nextCorner != null) {
                    SideOfCard parentCard = nextCorner.getParentCard();
                    if (parentCard != null) {
                        return parentCard;
                    }
                }
            }
            return null;
        }

    }





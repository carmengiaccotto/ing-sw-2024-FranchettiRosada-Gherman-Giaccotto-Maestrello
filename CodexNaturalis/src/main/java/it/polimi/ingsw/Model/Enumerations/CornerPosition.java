package it.polimi.ingsw.Model.Enumerations;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Position;

/**CornerPosition Enum*/
    public enum CornerPosition implements Position {
        /**Corner in position [0][0] on the corners matrix in SideOfCard*/
        TOPLEFT{

            @Override
            public CornerPosition CoverCorners(){
                return BOTTOMRIGHT;
            }

            @Override
            public SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea) {
                Corner corner = temporaryCentralCard.getCornerInPosition(this); //get TOPLEFT corner on the current card
                if (corner != null) {//check that corner is not null
                    Corner nextCorner = corner.getNextCorner();// get the next corner
                    if (nextCorner != null) {// check that the card has a next corner
                        SideOfCard parentCard = nextCorner.getParentCard();//get the parent card of this corner
                        if (parentCard != null) {//check parent card is not null
                            return parentCard;// return parent card
                        }
                    }
                }
                return null;
            }

            @Override
            public Pair<Integer, Integer> neighborToCheck(int r, int c) {
                return new Pair<>(r - 1, c - 1);
            }

//            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
//                return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
//                        currentCard.getPositionOnArea().getSecond()-1);
//            }


        },
        /**Corner in position [1][0] on the corners matrix in SideOfCard*/
        BOTTOMLEFT{
            @Override
            public CornerPosition CoverCorners(){
                return TOPRIGHT;
            }

//            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
//                return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
//                        currentCard.getPositionOnArea().getSecond()-1);
//            }
            @Override
            public Pair<Integer, Integer> neighborToCheck(int r, int c) {
                return new Pair<>(r + 1, c - 1);
            }

            @Override
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
            @Override
            public CornerPosition CoverCorners(){
                return BOTTOMLEFT;
            }
//            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
//                return new Pair<>(currentCard.getPositionOnArea().getFirst()-1,
//                        currentCard.getPositionOnArea().getSecond()+1);
//            }

        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r - 1, c + 1);
        }

        @Override
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

            @Override
            public CornerPosition CoverCorners(){
                return TOPLEFT;
            }

//            public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
//                return new Pair<>(currentCard.getPositionOnArea().getFirst()+1,
//                        currentCard.getPositionOnArea().getSecond()+1);
//            }

        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r + 1, c + 1);
        }

        @Override
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

//        /**Method that individuates the position of the newly placed card on the area
//         * @param currentCard card that is already o the playArea, and is having a corner covered
//         * @return coordinates of the newly placed card*/
//        public Pair<Integer, Integer> PositionNewCard(SideOfCard currentCard){
//            return null;
//        }

    /**Method that individuates the positions to check after the card is placed, to cover the neighbors' corners
     * @param r row where the new card has been placed
     * @param c column where the new card has been placed
     * @return coordinates of the newly placed card*/
        public Pair<Integer, Integer> neighborToCheck(int r, int c){
            return new Pair<>(r, c);
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





package it.polimi.ingsw.Model.Enumerations;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Position;

/**
 * This enumeration represents the possible positions of a corner on a card in the game.
 * It implements the Position interface.
 * A corner can be in one of the following positions: TOPLEFT, BOTTOMLEFT, TOPRIGHT, BOTTOMRIGHT.
 */
    public enum CornerPosition implements Position {

    /**
     * Represents the TOPLEFT position of a corner on a card.
     * It overrides the CoverCorners, getNeighbourCard, and neighborToCheck methods from the Position interface.
     */
    TOPLEFT {

        /**
         * This method is used to determine the corner that can be covered by the current corner.
         * In this case, the BOTTOMRIGHT corner can be covered by the TOPLEFT corner.
         *
         * @return The CornerPosition that can be covered by the current corner.
         */
        @Override
        public CornerPosition CoverCorners() {
            return BOTTOMRIGHT;
        }

        /**
         * This method is used to get the card that is in a specific CornerPosition compared to the current one.
         * It first retrieves the corner in the current position from the temporaryCentralCard.
         * If the corner exists, it retrieves the next corner and its parent card.
         * If the parent card exists, it returns the parent card.
         *
         * @param temporaryCentralCard The card whose position we are checking.
         * @param playArea The play area where the cards are placed.
         * @return The card in the given position or null if it does not exist.
         */
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

        /**
         * This method is used to determine the positions to check after the card is placed, to cover the neighbors' corners.
         * In this case, it returns the coordinates of the card that is diagonally top-left to the current card.
         *
         * @param r The row where the new card has been placed.
         * @param c The column where the new card has been placed.
         * @return Pair<Integer, Integer> - The coordinates of the card that is diagonally top-left to the current card.
         */
        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r - 1, c - 1);
        }

    },

    /**
     * Represents the BOTTOMLEFT position of a corner on a card.
     * It overrides the CoverCorners, getNeighbourCard, and neighborToCheck methods from the Position interface.
     */
    BOTTOMLEFT {

        /**
         * This method is used to determine the corner that can be covered by the current corner.
         * In this case, the TOPRIGHT corner can cover the BOTTOMLEFT corner.
         *
         * @return The CornerPosition that can be covered by the current corner.
         */
        @Override
        public CornerPosition CoverCorners() {
            return TOPRIGHT;
        }

        /**
         * This method is used to determine the positions to check after the card is placed, to cover the neighbors' corners.
         * In this case, it returns the coordinates of the card that is diagonally bottom-left to the current card.
         *
         * @param r The row where the new card has been placed.
         * @param c The column where the new card has been placed.
         * @return Pair<Integer, Integer> - The coordinates of the card that is diagonally bottom-left to the current card.
         */
        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r + 1, c - 1);
        }

        /**
         * This method is used to get the card that is in a specific CornerPosition compared to the current one.
         * It first retrieves the corner in the current position from the temporaryCentralCard.
         * If the corner exists, it retrieves the next corner and its parent card.
         * If the parent card exists, it returns the parent card.
         *
         * @param temporaryCentralCard The card whose position we are checking.
         * @param playArea The play area where the cards are placed.
         * @return The card in the given position or null if it does not exist.
         */
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

    /**
     * Represents the TOPRIGHT position of a corner on a card.
     * It overrides the CoverCorners, getNeighbourCard, and neighborToCheck methods from the Position interface.
     */
    TOPRIGHT {

        /**
         * This method is used to determine the corner that can be covered by the current corner.
         * In this case, the BOTTOMLEFT corner can be covered by the TOPRIGHT corner.
         *
         * @return The CornerPosition that can be covered by the current corner.
         */
        @Override
        public CornerPosition CoverCorners() {
            return BOTTOMLEFT;
        }

        /**
         * This method is used to determine the positions to check after the card is placed, to cover the neighbors' corners.
         *
         * @param r The row where the new card has been placed.
         * @param c The column where the new card has been placed.
         * @return The coordinates of the newly placed card.
         */
        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r - 1, c + 1);
        }

        /**
         * This method is used to get the card that is in a specific CornerPosition compared to the current one.
         *
         * @param temporaryCentralCard The card whose position we are checking.
         * @param playArea The play area where the cards are placed.
         * @return The card in the given position.
         */
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

    /**
     * Represents the BOTTOMRIGHT position of a corner on a card.
     * It overrides the CoverCorners, getNeighbourCard, and neighborToCheck methods from the Position interface.
     */
    BOTTOMRIGHT {

        /**
         * This method is used to determine the corner that can be covered by the current corner.
         * In this case, the TOPLEFT corner can be covered by the BOTTOMRIGHT corner.
         *
         * @return The CornerPosition that can be covered by the current corner.
         */
        @Override
        public CornerPosition CoverCorners() {
            return TOPLEFT;
        }

        /**
         * This method is used to determine the positions to check after the card is placed, to cover the neighbors' corners.
         *
         * @param r The row where the new card has been placed.
         * @param c The column where the new card has been placed.
         * @return The coordinates of the newly placed card.
         */
        @Override
        public Pair<Integer, Integer> neighborToCheck(int r, int c) {
            return new Pair<>(r + 1, c + 1);
        }

        /**
         * This method is used to get the card that is in a specific CornerPosition compared to the current one.
         *
         * @param temporaryCentralCard The card whose position we are checking.
         * @param playArea The play area where the cards are placed.
         * @return The card in the given position.
         */
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

    /**
     * This abstract method is used to determine which corner of a card can be covered by the current corner.
     * A card cannot cover any corner of any other card, but only a specific one, else the move is not valid.
     *
     * @return CornerPosition - The only corner on the card that can be covered by the current one without violating
     * the rules of the game.
     */
    public abstract CornerPosition CoverCorners();

    /**
     * This abstract method is used to identify the positions to check after the card is placed, in order to cover the neighbors' corners.
     *
     * @param r The row where the new card has been placed.
     * @param c The column where the new card has been placed.
     * @return Pair<Integer, Integer> - The coordinates of the newly placed card.
     */
    public abstract Pair<Integer, Integer> neighborToCheck(int r, int c);

    /**
     * This abstract method is used to get the card that is in a specific CornerPosition compared to the current one.
     *
     * @param temporaryCentralCard The card whose position we are checking.
     * @param playArea The play area we are currently checking. We do not use it here,
     *                 but need to put the parameter for DispositionCheck purpose.
     * @return SideOfCard - The card in the given position.
     */
    public abstract SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea);
}





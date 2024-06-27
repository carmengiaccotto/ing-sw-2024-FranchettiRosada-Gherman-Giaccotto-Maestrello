package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * EdgePositions Class:
 * Purpose: Managing the Edge Cases the Play Area can be in, when the Player is Placing a Card:
 *          Checking if the Player's move needs the PlayArea dimensions to be modified
 *          Modify the PlayArea dimensions if needed
 * Contains: EdgeCases ENUM to keep track of all the only possible edge cases
 *          SetCornersToCheck method
 */

public class EdgePositions {

    /**
     * The EdgeCases enum represents the possible edge cases that the play area can be in.
     * Each enum value represents a different edge case and overrides the ExpandArea and isEdgePosition methods to handle that specific case.
     */
    public enum EdgeCases {

        /**
         * The RowZero enum value represents the case where the player wants to place a card in the first row.
         * It overrides the ExpandArea method to add a new row at the top of the play area and the isEdgePosition method to check if the card is in the first row.
         */
        RowZero{

            /**
             * This method is used to expand the play area by adding a new row at the top.
             * It creates a new ArrayList and fills it with null values, representing empty spaces.
             * Then, it adds this new row to the top of the play area.
             *
             * @param cardsOnArea The current state of the play area.
             */
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                ArrayList<SideOfCard> array = new ArrayList<>();
                for(int i=0; i<cardsOnArea.get(0).size(); i++)
                    array.add(null);
                cardsOnArea.add(0, array);
            }

            /**
             * This method is used to check if the card is being placed in the first row of the play area.
             *
             * @param occupiedPosition The position where the player wants to place the card.
             * @param cardsOnArea The current state of the play area.
             * @return True if the card is being placed in the first row, false otherwise.
             */
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getFirst()==0; //check if the player wants to place the card in the first row
            }
        },

        /**
         * The ColumnZero enum value represents the case where the player wants to place a card in the first column.
         * It overrides the ExpandArea method to add a new column at the left of the play area and the isEdgePosition method to check if the card is in the first column.
         */
        ColumnZero{

            /**
             * This method is used to expand the play area by adding a new column at the left.
             * It iterates over each row in the play area and adds a null value at the beginning, representing an empty space.
             *
             * @param cardsOnArea The current state of the play area.
             */
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea) {
                for (List<SideOfCard> row : cardsOnArea) {
                    row.add(0, null);}

            }

            /**
             * This method is used to check if the card is being placed in the first column of the play area.
             *
             * @param occupiedPosition The position where the player wants to place the card.
             * @param cardsOnArea The current state of the play area.
             * @return True if the card is being placed in the first column, false otherwise.
             */
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getSecond()==0;
            }

        },

        /**
         * The RowMax enum value represents the case where the player wants to place a card in the last row.
         * It overrides the ExpandArea method to add a new row at the bottom of the play area and the isEdgePosition method to check if the card is in the last row.
         */
        RowMax{

            /**
             * This method is used to expand the play area by adding a new row at the bottom.
             * It creates a new ArrayList and fills it with null values, representing empty spaces.
             * Then, it adds this new row to the bottom of the play area.
             *
             * @param cardsOnArea The current state of the play area.
             */
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                List<SideOfCard> newRow = new ArrayList<>();
                int size = cardsOnArea.get(0).size();
                for (int i = 0; i < size; i++) {
                    newRow.add(null);
                }
                cardsOnArea.add(newRow);
            }

            /**
             * This method is used to check if the card is being placed in the last row of the play area.
             *
             * @param occupiedPosition The position where the player wants to place the card.
             * @param cardsOnArea The current state of the play area.
             * @return True if the card is being placed in the last row, false otherwise.
             */
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getFirst()==(cardsOnArea.size()-1);
            }

        },

        /**
         * The ColumnMax enum value represents the case where the player wants to place a card in the last column.
         * It overrides the ExpandArea method to add a new column at the right of the play area and the isEdgePosition method to check if the card is in the last column.
         */
        ColumnMax{

            /**
             * This method is used to expand the play area by adding a new column at the right.
             * It iterates over each row in the play area and adds a null value, representing an empty space.
             *
             * @param cardsOnArea The current state of the play area.
             */
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                for (List<SideOfCard> row : cardsOnArea) {
                    row.add(null);
                }
            }

            /**
             * This method is used to check if the card is being placed in the last column of the play area.
             *
             * @param occupiedPosition The position where the player wants to place the card.
             * @param cardsOnArea The current state of the play area.
             * @return True if the card is being placed in the last column, false otherwise.
             */
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition,List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getSecond()==(cardsOnArea.getFirst().size()-1);
            }

        };

        /**
         * This method is used to expand the play area by adding a new row or column where needed.
         * It is overridden by the elements of the enum.
         *
         * @param cardsOnArea The current state of the play area.
         */
        public void ExpandArea(List<List<SideOfCard>> cardsOnArea) {
        }

        /**
         * This method is used to check if the card is a "Border Card" of the PlayArea.
         * It is overridden by the elements of the enum.
         *
         * @param cardsOnArea The current state of the play area.
         * @param occupiedPosition The position where the player wants to place the card.
         * @return True if the position of the card matches the condition that the enum elements impose, false otherwise.
         */
        public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea) {
            return false;
        }
    }
}

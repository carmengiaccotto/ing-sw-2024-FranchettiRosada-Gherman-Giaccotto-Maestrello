package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;

import java.util.ArrayList;
import java.util.List;


/**EdgePositions Class:
 * Purpose: Managing the Edge Cases the Play Area can be in, when the Player is Placing a Card:
 *          Checking if the Player's move needs the PlayArea dimensions to be modified
 *          Modify the PlayArea dimensions if needed
 * Contains: EdgeCases ENUM to keep track of all the only possible edge cases
 *          SetCornersToCheck method
 *          */

public class EdgePositions {
    public enum EdgeCases {
        RowZero{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                ArrayList<SideOfCard> array = new ArrayList<>();
                for(int i=0; i<cardsOnArea.get(0).size(); i++)
                    array.add(null);
                cardsOnArea.add(0, array);
            }

            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getFirst()==0; //check if the player wants to place the card in the first row
            }


        },
        ColumnZero{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea) {
                for (List<SideOfCard> row : cardsOnArea) {
                    row.add(0, null);}

            }
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getSecond()==0;
            }

        },
        RowMax{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                List<SideOfCard> newRow = new ArrayList<>();
                int size = cardsOnArea.get(0).size();
                for (int i = 0; i < size; i++) {
                    newRow.add(null);
                }
                cardsOnArea.add(newRow);
            }
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getFirst()==(cardsOnArea.size()-1);
            }

        },
        ColumnMax{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                for (List<SideOfCard> row : cardsOnArea) {
                    row.add(null);
                }
            }
            @Override
            public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition,List<List<SideOfCard>> cardsOnArea){
                return occupiedPosition.getSecond()==(cardsOnArea.getFirst().size()-1);
            }

        };





        /**Method that adds a new Row or a new Column where needed.
         * Override by the elements of the enum
         * @param cardsOnArea the PlayArea whose dimensions we need to modify*/
        public void ExpandArea(List<List<SideOfCard>> cardsOnArea) {
        }




        /**Method that checks if the Card is a "Border Card" of the PlayArea.
         * Override by the elements of the enum
         * @param cardsOnArea the PlayArea we are checking
         * @param occupiedPosition position where the player wants to place the card
         * @return result :true if the position of the card matches the condition that the enum elements impose, false otherwise */
        public boolean isEdgePosition(Pair<Integer, Integer> occupiedPosition, List<List<SideOfCard>> cardsOnArea) {
            return false;
        }
    }



}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
                cardsOnArea.addFirst(new ArrayList<>());
            }

            @Override
            public boolean isEdgePosition(SideOfCard cardToCover, List<List<SideOfCard>> cardsOnArea){
                return cardToCover.getPositionOnArea().getFirst()==0;
            }


        },
        ColumnZero{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                for (List<SideOfCard> row : cardsOnArea) {
                    row.addFirst(null);
                }
            }
            @Override
            public boolean isEdgePosition(SideOfCard cardToCover, List<List<SideOfCard>> cardsOnArea){
                return cardToCover.getPositionOnArea().getSecond()==0;
            }

        },
        RowMax{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea){
                cardsOnArea.add(new ArrayList<>());
            }
            @Override
            public boolean isEdgePosition(SideOfCard cardToCover, List<List<SideOfCard>> cardsOnArea){
                return cardToCover.getPositionOnArea().getFirst()==(cardsOnArea.size()-1);
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
            public boolean isEdgePosition(SideOfCard cardToCover,List<List<SideOfCard>> cardsOnArea){
                return cardToCover.getPositionOnArea().getSecond()==(cardsOnArea.getFirst().size()-1);
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
         * @param cardToCover The card whose position on the PlayArea we are checking
         * @return result :true if the position of the card matches the condition that the enum elements impose, false otherwise */
        public boolean isEdgePosition(SideOfCard cardToCover, List<List<SideOfCard>> cardsOnArea) {
            return false;
        }
    }





    /**Each Edge Position on the PlayArea, potentially needs to update its dimensions, but the "full EdgeCase" is a combination of the
     * position of the Card in the PlayArea, and of the corners od the Card */
    public static Map<EdgeCases, ArrayList<CornerPosition>> cornersToCheck;




    /**Class Constructor
     * Method that constructs the "Full Edge Cases"*/
    public EdgePositions(){
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.RowZero, new ArrayList<>())).add(CornerPosition.TOPLEFT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.RowZero, new ArrayList<>())).add(CornerPosition.TOPRIGHT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.RowMax, new ArrayList<>())).add(CornerPosition.BOTTOMLEFT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.RowMax, new ArrayList<>())).add(CornerPosition.BOTTOMRIGHT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.ColumnZero, new ArrayList<>())).add(CornerPosition.BOTTOMLEFT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.ColumnZero, new ArrayList<>())).add(CornerPosition.TOPLEFT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.ColumnMax, new ArrayList<>())).add(CornerPosition.BOTTOMRIGHT);
        Objects.requireNonNull(cornersToCheck.put(EdgeCases.ColumnMax, new ArrayList<>())).add(CornerPosition.TOPRIGHT);
    }





}

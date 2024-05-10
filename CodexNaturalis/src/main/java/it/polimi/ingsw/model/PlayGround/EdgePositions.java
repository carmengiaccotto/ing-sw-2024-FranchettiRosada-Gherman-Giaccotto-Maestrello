package it.polimi.ingsw.model.PlayGround;

import it.polimi.ingsw.model.Cards.SideOfCard;
import it.polimi.ingsw.model.Enumerations.CornerPosition;
import it.polimi.ingsw.model.Pair;

import java.util.*;


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
                ArrayList array=new ArrayList<>();
                for(int i=0; i<cardsOnArea.get(0).size(); i++)
                    array.add(null);
                cardsOnArea.addFirst(array);
                for(int i=0; i<cardsOnArea.size(); i++)
                    for(int j=0; j<cardsOnArea.get(0).size(); j++) {
                        if(cardsOnArea.get(i).get(j)!=null) {
                            int row = cardsOnArea.get(i).get(j).getPositionOnArea().getFirst();
                            int column = cardsOnArea.get(i).get(j).getPositionOnArea().getSecond();
                            cardsOnArea.get(i).get(j).setPositionOnArea(new Pair<>(row+1, column ));
                        }
                    }
            }

            @Override
            public boolean isEdgePosition(SideOfCard cardToCover, List<List<SideOfCard>> cardsOnArea){
                return cardToCover.getPositionOnArea().getFirst()==0;
            }


        },
        ColumnZero{
            @Override
            public void ExpandArea(List<List<SideOfCard>> cardsOnArea) {
                if (cardsOnArea.isEmpty()) {
                    cardsOnArea.add(new ArrayList<>());
                }

                for (List<SideOfCard> row : cardsOnArea) {
                    row.add(0, null);
                }
                for(int i=0; i<cardsOnArea.size(); i++)
                    for(int j=0; j<cardsOnArea.get(0).size(); j++) {
                        if(cardsOnArea.get(i).get(j)!=null) {
                            int row = cardsOnArea.get(i).get(j).getPositionOnArea().getFirst();
                            int column = cardsOnArea.get(i).get(j).getPositionOnArea().getSecond();
                            cardsOnArea.get(i).get(j).setPositionOnArea(new Pair<>(row, column + 1));
                        }
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
                List<SideOfCard> newRow = new ArrayList<>();
                int size = cardsOnArea.get(0).size();
                for (int i = 0; i < size; i++) {
                    newRow.add(null);
                }
                cardsOnArea.add(newRow);
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

    public EdgePositions() {
        cornersToCheck = new HashMap<>();
        for (EdgeCases edgeCase : EdgeCases.values()) {
            cornersToCheck.put(edgeCase, new ArrayList<>());
        }

        // Check for null before adding elements
        if (cornersToCheck.containsKey(EdgeCases.RowZero) && cornersToCheck.containsKey(EdgeCases.RowMax)) {
            cornersToCheck.get(EdgeCases.RowZero).addAll(Arrays.asList(CornerPosition.TOPLEFT, CornerPosition.TOPRIGHT));
            cornersToCheck.get(EdgeCases.RowMax).addAll(Arrays.asList(CornerPosition.BOTTOMLEFT, CornerPosition.BOTTOMRIGHT));
        }

        // Check for null before adding elements
        if (cornersToCheck.containsKey(EdgeCases.ColumnZero) && cornersToCheck.containsKey(EdgeCases.ColumnMax)) {
            cornersToCheck.get(EdgeCases.ColumnZero).addAll(Arrays.asList(CornerPosition.BOTTOMLEFT, CornerPosition.TOPLEFT));
            cornersToCheck.get(EdgeCases.ColumnMax).addAll(Arrays.asList(CornerPosition.BOTTOMRIGHT, CornerPosition.TOPRIGHT));
        }
    }
}

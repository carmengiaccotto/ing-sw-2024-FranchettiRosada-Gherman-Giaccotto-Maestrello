package it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.SideOfCard;

/** @author Carmen Giaccotto
 * This subclass represents a specific type of Objective Card which goal is to have certains cards' dispositions on the Player's PlayArea.
 */
public class DispositionObjectiveCard extends it.polimi.ingsw.model.ObjectiveCard {

        private final SideOfCard[][] disposition;

        public DispositionObjectiveCard(ObjectivePoints points, SideOfCard[][] disposition) {
            super(points);
            this.disposition = disposition;
        }

        public int Check(SideOfCard[][] cardsOnArea){

            int numberOfDispositions = 0;
            int m = cardsOnArea.length;
            int n = cardsOnArea[0].length;
            int p = disposition.length;
            int q = disposition[0].length;

            for (int i = 0; i <= m - p; i++) {
                for (int j = 0; j <= n - q; j++) {
                    if (isSubmatrixAtPosition(cardsOnArea, disposition, i, j)) {
                        numberOfDispositions++;
                        j += q - 1;
                    }
                }
            }

            return numberOfDispositions;

        } //javaDoc missing

    /**
     * Method that checks if a given submatrix exists at the specified position within a larger matrix.
     *
     * @param matrix the larger matrix in which to search for the submatrix
     * @param submatrix the submatrix to search for
     * @param row the starting row index in the larger matrix
     * @param col the starting column index in the larger matrix
     * @return {@code true} if the submatrix is found at the specified position, {@code false} otherwise
     */
        private static boolean isSubmatrixAtPosition(SideOfCard[][] matrix, SideOfCard[][] submatrix, int row, int col) {
            int p = submatrix.length;
            int q = submatrix[0].length;

            for (int i = 0; i < p; i++) {
                for (int j = 0; j < q; j++) {
                    if (matrix[row + i][col + j] != submatrix[i][j]) {
                        return false;
                    }
                }
            }
            return true;

        }

        /**
         * getter method for the ObjectiveCard's goal
         *
         * @return goal
         */
        public SideOfCard[][] getDisposition(){
            return disposition;

        }

    }

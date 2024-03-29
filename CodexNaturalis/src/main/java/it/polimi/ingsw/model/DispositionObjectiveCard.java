package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
    public class DispositionObjectiveCard extends ObjectiveCard {

        private SideOfCard[][] disposition;

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

        }

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

        public SideOfCard[][] getDisposition(){
            return disposition;

        }

    }

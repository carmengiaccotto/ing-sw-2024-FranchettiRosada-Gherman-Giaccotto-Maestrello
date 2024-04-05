package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

    public class SideOfCard {
        private final HashMap<Symbol, Integer> symbols;
        static Corner[][] corners;
        private boolean isInConfiguration;

        public SideOfCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration) {
            this.symbols = symbols;
            SideOfCard.corners = corners;
            this.isInConfiguration = isInConfiguration;
        }

        public HashMap<Symbol, Integer> getSymbols(){
            return symbols;
        }

        public static Corner[][] getCorners(){
            return corners;
        }

        public void play(){        //to revise

        }

        public boolean isInConfig(){
            return isInConfiguration;
        }


        /** @author Denisa Minodora Gherman
         * Mehod to get a specific corner of the PlayCard
         * @return corner in the i,j position
         * @param i row
         * @param j */
        public Corner getCorner(int i, int j){
            return corners[i][j];
        }

        public void resetConfig(){
            isInConfiguration = false;
        }
    }

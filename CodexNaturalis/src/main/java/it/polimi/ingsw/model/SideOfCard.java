package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.HashMap;

    /** @author Alessia Franchetti-Rosada
     * Class that represents a side of a card that can be front or back.
     */
    public class SideOfCard {
        private final HashMap<Symbol, Integer> symbols;
        static Corner[][] corners;
        private boolean isInConfiguration;

        public SideOfCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration) {
            this.symbols = symbols;
            SideOfCard.corners = corners;
            this.isInConfiguration = isInConfiguration;
        }

        /**
         * getter method for the SideOfCard's symbols
         *
         * @return symbols
         */
        public HashMap<Symbol, Integer> getSymbols(){
            return symbols;
        }

        /**
         * getter method for the SideOfCard's corners
         *
         * @return corners
         */
        public static Corner[][] getCorners(){
            return corners;
        }

        public void play(){        //to revise + javadoc

        }

        /**
         * A boolean method that returns true if a specific card is used in a configuration of a objective card, or false otherwise.
         *
         * @return true if the specific card is used in the configuration of a objective card, false otherwise.
         */
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

        /**
         * This method resets the value of the parameter isInConfiguration to false,
         * after finding all dispositions in the play area of a specific objective card.
         */
        public void resetConfig(){
            isInConfiguration = false;
        }
    }

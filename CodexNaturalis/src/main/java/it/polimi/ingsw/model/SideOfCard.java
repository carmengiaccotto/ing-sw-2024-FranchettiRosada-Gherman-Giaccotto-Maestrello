package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Corner[][];

import java.util.HashMap;

    public class SideOfCard {
        private HashMap<Symbol, Integer> symbols;
        Corner[][] corners;
        private boolean isInConfiguration;
        private Colors color;

        public PlayCard(HashMap<Symbol, Integer> symbols, Corner[][] corners, boolean isInConfiguration, Colors color) {
            this.symbols = symbols;
            this.corners = corners;
            this.isInConfiguration = isInConfiguration;
            this.color = color;
        }

        public HashMap<Symbol, Integer> getSymbols(){
            return symbols;
        }

        public Corner[][] getCorners(){
            return corners;
        }

        public void play(){        //to revise

        }

        public boolean isInConfig(){
            //to revise
        }


        /** @author Denisa Minodora Gherman
         * Mehod to get a specific corner of the PlayCard
         * @return corner in the i,j position
         * @param i row
         * @param j */
        public Corner getCorner(int i, int j){
            return corners[i][j];
        }

        public boolean resetConfig(){
            isInConfiguration = false;
            return isInConfiguration;
        }
    }

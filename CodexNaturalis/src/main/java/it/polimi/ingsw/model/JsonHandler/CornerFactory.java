package it.polimi.ingsw.model.JsonHandler;

import it.polimi.ingsw.model.Cards.Corner;
import it.polimi.ingsw.model.Symbol;

/**Support Class for Json corners construction*/
public class CornerFactory {
    /**
     * Json corner constructor, has to be a static method to construct corners in SideOfCard
     *
     * @param corner can be Corner1, Corner2, Corner3, Corner4 in Json file
     * @return corner
     */
    public static Corner createCornerFromJson(String corner) {

        for(Symbol symbols: Symbol.values()){
            if (corner.equalsIgnoreCase(symbols.toString())) {
                return new Corner(symbols, false);
            }
        }

        if (corner.equalsIgnoreCase("HIDDEN")) {
            return new Corner(null, true);
        }

        if(corner.equalsIgnoreCase("EMPTY")) {
            return new Corner(null, false);
        }

        throw new IllegalArgumentException("Incorrect value for corner: " + corner);
    }
}
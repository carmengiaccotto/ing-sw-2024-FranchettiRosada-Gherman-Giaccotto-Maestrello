package it.polimi.ingsw.Model.JsonHandler;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Enumerations.Symbol;

/**
 * This class is a support class for constructing corners from a JSON file.
 */
public class CornerFactory {

    /**
     * This method is a static method used to construct corners in SideOfCard from a JSON file.
     * It takes a string as input which can be "Corner1", "Corner2", "Corner3", "Corner4", "HIDDEN", or "EMPTY".
     * If the input string matches any of the Symbol values, it creates a new Corner with that symbol and returns it.
     * If the input string is "HIDDEN", it creates a new Corner with no symbol and sets its hidden status to true.
     * If the input string is "EMPTY", it creates a new Corner with no symbol and sets its hidden status to false.
     * If the input string does not match any of the above, it throws an IllegalArgumentException.
     *
     * @param corner A string that represents the corner in the JSON file. It can be "Corner1", "Corner2", "Corner3", "Corner4", "HIDDEN", or "EMPTY".
     * @return A new Corner object constructed based on the input string.
     * @throws IllegalArgumentException If the input string does not match any of the expected values.
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
package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public class CornerFactory{

    public static Corner createCornerFromJson(String corner) {
        for(Symbol symbols: Symbol.values()){
            if (corner.equals(symbols.toString().toUpperCase())) {
                return new Corner(symbols, false);

            }

        }
        if (corner.equals("HIDDEN")){
            return new Corner(null, true);

        }
        if(corner.equals("EMPTY")){
            return new Corner(null, false);
        }
        throw new IllegalArgumentException("Incorrect value");

    }
}

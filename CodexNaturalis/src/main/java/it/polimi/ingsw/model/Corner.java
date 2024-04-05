package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import it.polimi.ingsw.model.Pair;

public class Corner {

    private Pair position;
    private Symbol symbol;
    private boolean hidden;
    private boolean covered;

    public Corner(Pair position, Symbol symbol, boolean hidden, boolean covered) {
        this.position = position;
        this.symbol = symbol;
        this.hidden = hidden;
        this.covered = covered;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isHidden(){
        return hidden;
    }

    public boolean isCovered(){
        return covered;
    }

    public Pair getPosition(){
       return position;
    }

}





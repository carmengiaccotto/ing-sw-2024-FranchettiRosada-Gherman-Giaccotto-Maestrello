package it.polimi.ingsw.model;

public class Corner {

    private Pair position;
    private Symbol symbol;
    private boolean isHidden;
    private boolean isCovered;

    public Corner(Pair position, Symbol symbol, boolean isHidden, boolean isCovered) {
        this.position = position;
        this.symbol = symbol;
        this.isHidden = isHidden;
        this.isCovered = isCovered;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isHidden(){

    }

    public boolean isCovered(){

    }

    public Pair getPosition(){
       return position;
    }


}

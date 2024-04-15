package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public class Corner {

    private Symbol symbol;

    private CornerPosition position;
    private boolean covered;
    private boolean hidden;
    private Corner nextCorner;
    private SideOfCard parentCard;


    public Corner(Symbol symbol, boolean covered, boolean hidden, Corner nextCorner){


        this.symbol = symbol;
        this.covered = covered;
        this.hidden = hidden;
        this.nextCorner = nextCorner;
    }


    public boolean isCovered() {
        return covered;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Corner getNextCorner() {
        return nextCorner;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setCovered() {
        covered = true;
    }

    public void setNextCorner(Corner nextCorner) {
        this.nextCorner = nextCorner;
    }



    public SideOfCard getParentCard() {
        return parentCard;
    }

    public void setParentCard(SideOfCard parentCard) {
        this.parentCard = parentCard;
    }

    public CornerPosition getPosition() {
        return position;
    }

    public void setPosition(CornerPosition position) {
        this.position = position;
    }
}

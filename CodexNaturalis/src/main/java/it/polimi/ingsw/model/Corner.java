package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public class Corner {

    private  Symbol symbol;

    private CornerPosition position;
    private boolean covered;
    private boolean hidden;
    private Corner nextCorner;
    private SideOfCard parentCard;


    /**
     * Class Constructor
     */
    public Corner(Symbol symbol, boolean hidden){
        this.symbol = symbol;
        covered=false;
        this.hidden = hidden;
        nextCorner=null;
    }


    /**Getter method for Covered attribute. To be used by controller checks
     * @return covered true if the card is covered*/
    public boolean isCovered() {
        return covered;
    }


    /**Getter method for Hidden attribute. To be used by controller checks
     * @return hidden true if the card is hidden*/

    public boolean isHidden() {
        return hidden;
    }



    /**Getter method for NextCorner attribute.
     * @return nextCorner can be the one that covers the current one or the one that is covered by it*/
    public Corner getNextCorner() {
        return nextCorner;
    }


    /**Getter method for symbol attribute.
     * @return symbol can be null*/
    public Symbol getSymbol() {
        return symbol;
    }

    /**A cornered has Covered=False by default, this method sets it to true*/
    public void setCovered() {
        covered = true;
    }



    /**Setter method for NextCorner attribute.
     * @param nextCorner can be the one that covers the current one or the one that is covered by it*/
    public void setNextCorner(Corner nextCorner) {
        this.nextCorner = nextCorner;
    }



    /**Getter method for parentCard attribute.
     * @return parentCard*/
    public SideOfCard getParentCard() {
        return parentCard;
    }





    /**Setter method for NextCorner attribute.
     * @param parentCard method used during card construction*/
    public void setParentCard(SideOfCard parentCard) {
        this.parentCard = parentCard;
    }



    /**Getter method for position on the card attribute.
     * @return position*/
    public CornerPosition getPosition() {
        return position;
    }



    /**Setter method for position attribute.
     * @param position method used during card construction*/
    public void setPosition(CornerPosition position) {
        this.position = position;
    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public class PairOfCards {
    private Pair sides;
    private int idCard;

    public PairOfCards(Pair side, int idCard) {
        this.side = side;
        this.idCard = idCard;
    }

    public Pair getSides(){
        return sides;
    }

    public PlayCard chooseSide(Side side) {
        PlayCard card;
        return card;
    }

    public int getId(){
        return idCard;
    }

    public void setId(int idCard) {
        this.idCard = idCard;
    }
}


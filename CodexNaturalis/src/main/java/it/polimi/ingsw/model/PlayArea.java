package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import java.util.Map;
import java.util.HashMap;

public class PlayArea {
    private HashMap<Symbol, Integer > symbols;
    private Card[][] CardsOnArea;

    public PlayArea(HashMap<Symbol, Integer> symbols, Card[][] CardsOnArea){


    }
    public Card[][] getCardsOnArea() {
        return CardsOnArea;
    }

    public void setCardsOnArea(Card[][] cardsOnArea) {

        CardsOnArea = cardsOnArea;
    }
}

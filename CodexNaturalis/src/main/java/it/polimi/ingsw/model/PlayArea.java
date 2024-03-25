package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Card;

import java.util.Map;

public class PlayArea {
    private Map<Symbol, Integer > Symbols;
    private Card[][] CardsOnArea;

    public Card[][] getCardsOnArea() {
        return CardsOnArea;
    }
    public void setCardsOnArea(Card[][] cardsOnArea) {
        CardsOnArea = cardsOnArea;
    }

    public Card getCard( int row, int column) {
        if (CardsOnArea != null) {
            if (row < CardsOnArea.length && column<CardsOnArea[0].length) {
                return CardsOnArea[row][column];
            }
            else {
                /*create new line and new column*/
            }

        }
        else{
            throw new NullPointerException("The PlayArea is non existent");
        }
        return null;
    }

    public Map<Symbol, Integer> getSymbols() {
        return Symbols;
    }

    public void setSymbols(Map<Symbol, Integer> symbols) {
        Symbols = symbols;
    }

    public int getNumSymbols(Map<Symbol,Integer> Symbols, Symbol symbol){
        return Symbols.get(symbol);
    }

    public static void changeNumSymbol(Map<Symbol, Integer> Symbols, Symbol symbolToUpdate, int n){
        int NewNumSymbol = getNumSymbols(Symbols, symbolToUpdate) + n;
        Symbols.put(symbolToUpdate, NewNumSymbol);
    }

}

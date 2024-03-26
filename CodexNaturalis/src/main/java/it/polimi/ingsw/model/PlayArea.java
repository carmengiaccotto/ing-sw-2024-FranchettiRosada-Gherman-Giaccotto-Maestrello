package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Card;

import java.util.Map;
/*@author Denisa Minodora Gherman
* Class that implements the PlayArea of the single Player. It is built as a Matrix whose Type is Card:
* The player has already chosen the side they want to play the Card on*/
public class PlayArea {
    /*Number of occurrences of each symbol on the PlayArea. Needed for objective Cards and for goldCards requirement to be placed*/
    private Map<Symbol, Integer > Symbols;
    private Card[][] CardsOnArea;





    /*CardsOnArea matrix getter method
    * @return Card[][] which represents the PlayArea to which the cards are to be added at every Round*/
    public Card[][] getCardsOnArea() {
        return CardsOnArea;
    }





    /*CardsOnArea setter method
    * @return Card[][]
    *
    * Do we need this??
    public void setCardsOnArea(Card[][] cardsOnArea) {
        CardsOnArea = cardsOnArea;
    }

     */



    /*This method returns the card placed in a certain position in the PlayArea
    * @return Card
    * @param int row
    * @param int column
    * @throws NullPointer exception if the PlayArea does not Exist yet
    * @throws ArrayIndexOutOfBoundsException if we are trying to search an invalid position
    * row and column represent the position we want to search*/
    public Card getCard( int row, int column) {
        if (CardsOnArea != null) {
            if (row < CardsOnArea.length && column<CardsOnArea[0].length && row>=0 && column>=0) {
                return CardsOnArea[row][column];
            }
            else {
                throw new ArrayIndexOutOfBoundsException("This position does not exist on your playArea");
            }

        }
        else{
            throw new NullPointerException("The PlayArea is non existent");
        }
    }






    public Map<Symbol, Integer> getSymbols() {
        return Symbols;
    }

    public void setSymbols(Map<Symbol, Integer> symbols) {
        Symbols = symbols;
    }
    public void initializePlayArea(){
        for (Symbol symbol : Symbol.values()) {//
            Symbols.put(symbol, 0);
        }
        // add the initialization of the matrix.
        //Big matrix with the maximum of rows and columns or a dynamic one? How do we think of the dynamic one?
        //For the big Matrix, where does the initial  card belong?


    }

    public void AddCardOnArea(Card newCard, int row, int column){
        if(row < CardsOnArea.length && column<CardsOnArea[0].length && row>=0 && column>=0){
            if(CardsOnArea[row][column]==null){
                //To add another if case, that checks that the angle is free
                /*also check that the card does not cover two angles of the same card:
                * get the position of the card the angle belongs to, and check that
                * Parameters row and column are not == to the ones of the card*/
                CardsOnArea[row][column]=newCard;
            }
            else{
                //Throw Exception
            }
        }
        else{
            //modify the matrix
            //Add new row and new column even if we don't need them to avoid switch case?
        }
    }

    public int getNumSymbols(Map<Symbol,Integer> Symbols, Symbol symbol){
        return Symbols.get(symbol);
    }

    public static void changeNumSymbol(Map<Symbol, Integer> Symbols, Symbol symbolToUpdate, int n){
        int NewNumSymbol = getNumSymbols(Symbols, symbolToUpdate) + n;
        Symbols.put(symbolToUpdate, NewNumSymbol);
    }

}


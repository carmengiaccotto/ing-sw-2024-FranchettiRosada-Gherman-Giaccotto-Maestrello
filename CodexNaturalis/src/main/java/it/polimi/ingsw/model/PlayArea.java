package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import it.polimi.ingsw.model.Card;

import java.util.Map;
/**@author Denisa Minodora Gherman
* Class that implements the PlayArea of the single Player. It is built as a Matrix whose Type is Card:
* The player has already chosen the side they want to play the Card on*/
public class PlayArea {
    /**Number of occurrences of each symbol on the PlayArea. Needed for objective Cards and for goldCards requirement to be placed*/
    private static Map<Symbol, Integer > symbols;
    private Card[][] CardsOnArea;





    /**CardsOnArea matrix getter method
    * @return Card[][] which represents the PlayArea to which the cards are to be added at every Round*/
    public Card[][] getCardsOnArea() {
        return CardsOnArea;
    }





    /**CardsOnArea setter method
    * @return Card[][]
    *
    * Do we need this??
    public void setCardsOnArea(Card[][] cardsOnArea) {
        CardsOnArea = cardsOnArea;
    }

     */



    /**This method returns the card placed in a certain position in the PlayArea
    * @return Card
    * @param  row
    * @throws NullPointerException if the PlayArea does not Exist yet
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





    /**Symbols Map getter
    * @return Symbols map with key and value*/
    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }










    /**Initializes the Playarea to a 3x3 Matrix, so that, at the second round, we have space to place the
     * second card without modifying the matrix. In also populates the map and sets all the counts to zero*/
    public void initializePlayArea(){
        for (Symbol symbol : Symbol.values()) {//
            symbols.put(symbol, 0);
        }
        CardsOnArea=new Card[3][3];//do we initialize at 3x3 or 1x1?

    }






    /**Method to add a new card on the PlayGround.
    * @param newCard card that we want to add
    * @param row
    * @param column
    * @throws IllegalStateException the playing is trying to place a card on a position already occupied by another card
    * row and column are the coordinates where we want the card to be placed
    * It is a dynamic matrix, so we might need to add a new row and/or a new column.*/
    public void AddCardOnArea(Card newCard, int row, int column){
        if(row < CardsOnArea.length && column<CardsOnArea[0].length && row>=0 && column>=0){
            if(CardsOnArea[row][column]==null){
                //To add another if case, that checks that the Corner is free
                //if (CardToCover.checkCornerToCover)
                /*also check that the card does not cover two corners of the same card:
                * get the position of the card the angle belongs to, and check that
                * Parameters row and column are not == to the ones of the card*/
                CardsOnArea[row][column]=newCard;
            }
            else{
                throw new IllegalStateException("The card cannot be placed here, as there is already another Card placed");
            }
        }
        else{
           Card[][] TemporaryPlayArea= new Card[CardsOnArea.length+1][CardsOnArea[0].length];
           if(row<0 && column<0){
               for (int i = 0; i < CardsOnArea.length; i++) {
                   System.arraycopy(CardsOnArea[i], 0, TemporaryPlayArea[i + 1], 1, CardsOnArea[i].length);
               }
               CardsOnArea=TemporaryPlayArea;
               TemporaryPlayArea=null;
               System.gc();
               CardsOnArea[0][0]=newCard;

           } else if (row>CardsOnArea.length && column>CardsOnArea[0].length) {
               CardsOnArea=TemporaryPlayArea;
               TemporaryPlayArea=null;
               System.gc();
               CardsOnArea[CardsOnArea.length-1][CardsOnArea[0].length]=newCard;

           }

        }
    }








    /**Method to get the occurrences of a given symbol. This method is going to be used to check the requirements for the goldCards
    * and to check if the goal of GoalObjectiveCards has been reached
    * @return int n number of occurrences of the given symbol
    * @param symbol the symbol whose occurrences we want to save
    *  */
    public static int getNumSymbols(Symbol symbol){

        return symbols.get(symbol);
    }








    /**This method is being used each time a FrontCard is placed: The back cards don't have symbols in their corner
    * n might also be a negative number, when a card covers a corner that contains a symbol
    * @param symbolToUpdate new symbols on the board or symbol in the corner that is being covered
    * @param  n number of occurrences added or removed */
    public  void changeNumSymbol(Symbol symbolToUpdate, int n){
        int NewNumSymbol = getNumSymbols(symbolToUpdate) + n;
        symbols.put(symbolToUpdate, NewNumSymbol);
    }

}


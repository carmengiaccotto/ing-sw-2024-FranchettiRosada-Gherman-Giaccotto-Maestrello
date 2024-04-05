package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Position;

import java.util.Map;
/**@author Denisa Minodora Gherman
* Class that implements the PlayArea of the single Player. It is built as a Matrix whose Type is SideOfCard:
 * HOW DOES THE PLACING OF THE CARD WORK?
 * The Player has already chosen the side of the card they want to play, so we are going to place that one.
 * The player will have to choose the Corner they want to cover. The information about the card the corner belongs to is also goimg to be transmitted.
 * Using the Position enumeration and its methods, we are given the position the chosen card has to be placed in. The matrix is a dynamic one, so first we check that it
 * is big enough to contain the card. If the result is positive, we just check that the place is not already taken. If the matruix is not big enough, we create a new one with
 * the right dimensions and shift everything (when needed) and place the card in the cright position.
 * Checking that the surrounding corners are not hidden or already covered, is going to be implemented by the controller, before calling the AddCardOnArea method.
 * Once the card is Placed, we set the Covered attribute of the Corners that our new card is covering to true.*/
public class PlayArea {
    /**Number of occurrences of each symbol on the PlayArea. Needed for objective Cards and for goldCards requirement to be placed*/
    private static Map<Symbol, Integer > symbols;
    private SideOfCard[][] CardsOnArea;





    /**CardsOnArea matrix getter method
    * @return Card[][] which represents the PlayArea to which the cards are to be added at every Round*/
    public SideOfCard[][] getCardsOnArea() {
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
    public SideOfCard getCard( int row, int column) {
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










    /**Initializes the Playarea to a 1x1 Matrix, even if we already know we are going to need to allocate a new One,
     *  so that the second round is not a "special" round, but it is coherent with the rest of the game functioning
     *  It also populates the map and sets all the counts to zero*/
    public void initializePlayArea(){
        for (Symbol symbol : Symbol.values()) {//
            symbols.put(symbol, 0);
        }
        CardsOnArea=new SideOfCard[1][1];

    }

/**Method that we are using to check if we are trying to access a column that does not exist
 * @return exists boolean
 * @param column the column whose existence we want to check*/
    public boolean columnExists(int column) {
        return CardsOnArea.length > 0 && column >= 0 && column < CardsOnArea[0].length;
    }








    /**Method that we are using to check if we are trying to access a row that does not exist
     * @return exists boolean
     * @param row the row whose existence we want to check*/
    public boolean rowExists(int row) {
        return row >= 0 && row < CardsOnArea.length;
    }









/**Method to set the Covered Attribute to true on the corners that the Newly placed Card Covers
 * @param PlacedCard is the card that has just been placed. We need it to get the list of its corners
 * @param row
 * @param column
 * row, column: position of the newly placed card on the PlayArea.
 */
    public void CoverCorners(SideOfCard PlacedCard, int row, int column){
        for (Corner[] corners : SideOfCard.getCorners()){
            for(Corner corner: corners) {
                Position cornerVerticalPosition = (Position) corner.getPosition().getFirst();
                Position cornerHorizontalPosition = (Position) corner.getPosition().getSecond();
                int RowToCheck = Position.VerticalPositioning(row,cornerVerticalPosition);
                int ColumnToCheck = Position.HorizontalPositioning(column, cornerHorizontalPosition);
                Position Indexrow = (Position) corner.getPosition().getFirst();
                Position IndexCol = (Position) corner.getPosition().getSecond();
                int CornerRow = Indexrow.getIndex();
                int CornerColumn = IndexCol.getIndex();

                if (getCard(RowToCheck, ColumnToCheck) != null) {
                    Corner TargetCorner = getCard(RowToCheck, ColumnToCheck).getCorner(1 - CornerRow, 1 - CornerColumn);
                    TargetCorner.isCovered() = true; //SERVE METODO COVERCORNER IN CORNER
                    changeNumSymbol(TargetCorner.getSymbol(), -1);

                }

            }
        }
    }









    /**Method to add a new card on the PlayGround.
    * @param newCard card that we want to add
    * @param row
    * @param column
    * @throws IllegalStateException the playing is trying to place a card on a position already occupied by another card
    * row and column are the coordinates where we want the card to be placed
    * It is a dynamic matrix, so we might need to add a new row and/or a new column.*/
    public void AddCardOnArea(SideOfCard newCard, int row, int column){
        if(rowExists(row) && columnExists((column))){
            if(CardsOnArea[row][column]==null){
                    CardsOnArea[row][column]=newCard;
            }

            else{
                throw new IllegalStateException("The card cannot be placed here, as there is already another Card placed");
            }
        }
        else{
            if ((row == CardsOnArea.length && column==CardsOnArea[0].length) || (row<0 && column<0)) {
                SideOfCard[][] TemporaryPlayArea = new SideOfCard[CardsOnArea.length + 1][CardsOnArea[0].length+1];
                if (row < 0 && column < 0) {
                    for (int i = 0; i < CardsOnArea.length; i++) {
                        System.arraycopy(CardsOnArea[i], 0, TemporaryPlayArea[i + 1], 1, CardsOnArea[i].length);
                    }
                    CardsOnArea = TemporaryPlayArea;
                    TemporaryPlayArea = null;
                    System.gc();
                    CardsOnArea[0][0] = newCard;

                } else if (row == CardsOnArea.length && column == CardsOnArea[0].length) {
                    CardsOnArea = TemporaryPlayArea;
                    TemporaryPlayArea = null;
                    System.gc();
                    CardsOnArea[CardsOnArea.length - 1][CardsOnArea[0].length-1] = newCard;

                }
            }
            else{
                if((row < 0|| row == CardsOnArea.length) && columnExists(column) ){
                    SideOfCard[][] TemporaryPlayArea = new SideOfCard[CardsOnArea.length + 1][CardsOnArea[0].length];
                    if(row<0){
                        for (int i = 0; i < CardsOnArea.length; i++) {
                            for (int j = 0; j < CardsOnArea[i].length; j++) {
                                TemporaryPlayArea[i + 1][j] = CardsOnArea[i][j];
                            }
                        }
                        CardsOnArea = TemporaryPlayArea;
                        TemporaryPlayArea = null;
                        System.gc();
                        CardsOnArea[0][column] = newCard;

                    } else if (row== CardsOnArea.length) {
                        for (int i = 0; i < CardsOnArea.length; i++) {
                            for (int j = 0; j < CardsOnArea[i].length; j++) {
                                TemporaryPlayArea[i][j] = CardsOnArea[i][j];
                            }
                        }
                        CardsOnArea = TemporaryPlayArea;
                        TemporaryPlayArea = null;
                        System.gc();
                        CardsOnArea[CardsOnArea.length][column] = newCard;

                    }

                } else if ((column < 0|| column == CardsOnArea[0].length) && rowExists(row) ) {
                    SideOfCard[][] TemporaryPlayArea = new SideOfCard[CardsOnArea.length][CardsOnArea[0].length+1];
                    if(column<0){
                        for (int i = 0; i < CardsOnArea.length; i++) {
                            for (int j = 0; j < CardsOnArea[i].length; j++) {
                                TemporaryPlayArea[i][j+1] = CardsOnArea[i][j];
                            }
                        }
                        CardsOnArea = TemporaryPlayArea;
                        TemporaryPlayArea = null;
                        System.gc();
                        CardsOnArea[row][0] = newCard;
                    }
                    else if(column==CardsOnArea[0].length){
                        for (int i = 0; i < CardsOnArea.length; i++) {
                            for (int j = 0; j < CardsOnArea[i].length; j++) {
                                TemporaryPlayArea[i][j] = CardsOnArea[i][j];
                            }
                        }
                        CardsOnArea = TemporaryPlayArea;
                        TemporaryPlayArea = null;
                        System.gc();
                        CardsOnArea[row][CardsOnArea[0].length] = newCard;


                    } else if (column>CardsOnArea[0].length || row>CardsOnArea.length) {
                        //messaggio di errore


                    }

                }
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


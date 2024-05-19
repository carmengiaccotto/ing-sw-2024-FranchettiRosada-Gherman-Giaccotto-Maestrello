package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Symbol;

import java.util.*;

/**This class represents the playArea. Each player has its own*/
public class PlayArea {
    /**
     * A map that contains all the symbols that are visible on the playArea. It is going to be used for
     * goldCards requirement check and to attribute ObjectiveCards points and goldCard points
     */
    private final Map<Symbol, Integer> symbols;


    /**
     * Dynamic matrix of SideOfCard. We are using SideOfCard, because, if the card is present here, is because the player has
     * already made a choice about what side they want to play
     */
    private List<List<SideOfCard>> cardsOnArea=new ArrayList<>();


    /**
     * Class Constructor

     */
    public PlayArea() {
        cardsOnArea.add(new ArrayList<>()); // Adding an inner list
        cardsOnArea.getFirst().add(null); // Adding a null element to the inner list
        symbols = InitializeSymbolMap();
    }


    /**Getter method for Symbols attribute
     * @return  symbols  on the playArea*/
    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    /**Returns the number of occurrences of a specified symbol on the playArea
     * @param symbol whose occurrences we want to check */
    public int getNumSymbols(Symbol symbol) {
        return symbols.get(symbol);
    } //tested


    /**Setter method for cardsOnArea attribute. Used for testing purposes*/
    public void setCardsOnArea(List<List<SideOfCard>> cards){
        this.cardsOnArea=cards;
    } //TODO test this


    /**
     * Method that is used to initialize the Symbols map of the playArea.
     * Populates the map with all the symbols from Symbol enum, and sets all their values to zero
     * @return playAreaMap which is going to be our symbols map
     */
    public Map<Symbol, Integer> InitializeSymbolMap() { //tested
        Map<Symbol, Integer> playAreaMap = new HashMap<>();
        for (Symbol symbol : Symbol.values()) {
            playAreaMap.put(symbol, 0);
        }
        return playAreaMap;

    }

    /**method to add the initial card to the area. It is used only for the first card of the game.
    /**method to add the initial card to the area. It is used only for the first card of the game
     * Once the card is added, the matrix is expanded, adding a new row before and after, and a new
     * column before and after. The new card is placed in the center of the matrix.
     * @param card initial card to be added*/
    public void addInitialCardOnArea(SideOfCard card){//tested
        cardsOnArea.getFirst().set(0, card);
        AddSymbolsToArea(card);

        //add an empty row at the beginning of the playArea
        cardsOnArea.addFirst(new ArrayList<>(Collections.nCopies(cardsOnArea.getFirst().size(), null)));
        //add an empty row at the  ending of the playArea
        cardsOnArea.add(new ArrayList<>(Collections.nCopies(cardsOnArea.getFirst().size(), null)));

        // add an empty column at the beginning of each row
        for (List<SideOfCard> row : cardsOnArea) {
            row.add(0, null);
        }
        //add an empty column at the end of each row
        for (List<SideOfCard> row : cardsOnArea) {
            row.add(null);
        }
    }

    /**Method to get the card that occupies a specified position on the area.
     * @param  i desired row
     * @param j desired column*/
    public SideOfCard getCardInPosition(int i, int j) { //tested
        if(i<cardsOnArea.size() && j< cardsOnArea.getFirst().size()) {
            List<SideOfCard> internalList = getCardsOnArea().get(i);
            return internalList.get(j);
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid Position");
    }


    /**
     * Checks if the PlayArea has a column in the given position
     *
     * @param column the column whose existence we have to check
     * @return boolean true if the column exists, false if it doesn't
     */
    public boolean columnExists(int column) { //tested
        return !cardsOnArea.getFirst().isEmpty() && column >= 0 && column < cardsOnArea.getFirst().size();
    }


    /**
     * Method that we are using to check if we are trying to access a row that does not exist
     *
     * @param row the row whose existence we want to check
     * @return exists boolean
     */
    public boolean rowExists(int row) {
        return row >= 0 && row < cardsOnArea.size();
    } //tested


    /**
     * Once the Card is placed, we also want to check if it covers other corners besides the one the player has indicated.
     * We check the positions where the card can cover other cards without covering two corners of the same card.
     * If  a card in the searched position is found, get the right cover to corner, through the CornerPosition Enum and set the corner to covered
     * Update the NextCorner attribute: these corners are now each others next.
     * Update the symbols map: if the covered corner contained a symbol, now it is not visible on the map anymore
     *
     * @param newCard the card that has just been placed: this method is called after the card has been added to the PlayArea
     */


    //TODO Modify this method with new Logic
    public void checkCloseNeighbours(Pair<Integer, Integer>newCardPosition, SideOfCard newCard) {
        for (Corner[] Rowcorner : newCard.getCorners()) {//access the corners lines
            for (Corner corner : Rowcorner) {// access the single corner
                Pair<Integer, Integer> newPosition = corner.getPosition().neighborToCheck(newCardPosition.getFirst(), newCardPosition.getSecond());
                //use ConerPosition methods to calculate position to check
                if (newPosition != null) {// check
                    int rowToCheck = newPosition.getFirst();//get row to check
                    int columnToCheck = newPosition.getSecond();// get column to check
                    if (rowExists(rowToCheck) && columnExists(columnToCheck)) {// check that these positions exist
                        List<SideOfCard> row = cardsOnArea.get(rowToCheck);// get cards on the row to check
                        if (row != null) {//check that row is not null
                            SideOfCard neighbourCard = row.get(columnToCheck);//get card in the column to check
                            if (neighbourCard != null) {//check that this card is not occupied by a null object
                                CornerPosition cornerToCover = corner.getPosition().CoverCorners(); //get the corresponding corner to cover
                                if (neighbourCard.getCornerInPosition(cornerToCover) != null) {//check this corner is not null
                                    neighbourCard.getCornerInPosition(cornerToCover).setCovered();//cover corner
                                    Symbol coveredSymbol = neighbourCard.getCornerInPosition(cornerToCover).getSymbol(); //get symbol in the covered corner
                                    if (symbols.containsKey(coveredSymbol)) {//if the playArea symbol map contains this symbol
                                        symbols.put(coveredSymbol, symbols.getOrDefault(coveredSymbol, 1) - 1);//that symbol is no longer visible, so we must subtract 1
                                    }
                                    // setting the corners to be each other's next
                                    neighbourCard.getCornerInPosition(cornerToCover).setNextCorner(corner);
                                    corner.setNextCorner(neighbourCard.getCornerInPosition(cornerToCover));
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    /**resets the attribute InConfig pf all the cards on the playArea to false.
     * This method is used when we finished checking the number of occurrences of
     * one Objective Disposition, and we have to start to check for a new one*/
    public void resetConfig() {
        for (List<SideOfCard> row :cardsOnArea)
            for (SideOfCard card : row) {
                if(card!=null) {
                    if (card.isInConfiguration())
                        card.setInConfiguration(false);
                }
            }
    }



    /**
     * Once the card has been placed, the symbols that the corners of the card contain, need to update the playArea symbols map
     *
     * @param NewCard card that has just been placed
     */
    public void AddSymbolsToArea(SideOfCard NewCard) { //ToTest
        for (Symbol symbol : NewCard.getSymbols().keySet()) {
            int newValue = NewCard.getSymbols().get(symbol) + symbols.get(symbol);
            symbols.put(symbol, newValue);

        }
    }


    /**
     * Main method of the player's round. Once a player has chosen the card they want to play, and the side of the card, they have to place it on the area.
     * PlayArea has to be updated with new dimensions
     * else the playArea stays the same and the card gets Placed in the chosen position.
     * The corner to cover is set to covered, and the nextCorners are set
     * Card symbols are added to the PlayAreaMap
     * Neighbours check
     *
     * @param NewCard       card the player wants to place

     */
    //TODO In controller add checking that we are covering at least a card+
    // check hidden corners+check not covering two corners same card+ IsEdgeCase expansion
    public void  addCardOnArea(SideOfCard NewCard, int r, int c) {
        //check if the desired position exists and is not already occupied
        if(rowExists(r) && columnExists(c)) {
            System.out.println("prev card: "+getCardInPosition(r,c));
            cardsOnArea.get(r).set(c, NewCard);//addCard in the desired position
            System.out.println("curr card: "+getCardInPosition(r,c));
            IsEdgeCase(new Pair<>(r,c));//Expand Area if needed;
            checkCloseNeighbours(new Pair<>(r,c), NewCard);//cover the corners of the surrounding cards
            System.out.println("new card neighbor: "+ NewCard.getCornerInPosition(CornerPosition.BOTTOMRIGHT).getNextCorner());
            AddSymbolsToArea(NewCard);//update playArea symbols map with the ones of the new card
            //other checks to be done in controller

        }
        else {
            throw new ArrayIndexOutOfBoundsException("Invalid Position");
        }
    }



    /**Method that verifies if the placement of the new card in the desired position is an edge case
     * and the matrix needs to be Expanded
     * @param positionPlacedCard position where the player wants to place a new card
     * */
    public void IsEdgeCase(Pair<Integer, Integer>positionPlacedCard) {
        EdgePositions edgePositions=new EdgePositions();
        for (EdgePositions.EdgeCases key : EdgePositions.EdgeCases.values()) {
            if (key.isEdgePosition(positionPlacedCard, cardsOnArea)) {
                key.ExpandArea(cardsOnArea);
            }
        }
    }



    /**Getter method for cardsOnArea attribute
     * @return cardsOnArea */

    public List<List<SideOfCard>> getCardsOnArea() {
        return cardsOnArea;
    }



}

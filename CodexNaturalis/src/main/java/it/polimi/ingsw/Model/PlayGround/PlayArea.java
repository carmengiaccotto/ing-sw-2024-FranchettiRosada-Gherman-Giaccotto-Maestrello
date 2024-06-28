package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents the play area in the game. Each player has their own play area.
 * It implements Serializable to allow the state of the play area to be saved and loaded.
 */
public class PlayArea implements Serializable {

    /**
     * A map that contains all the symbols that are visible on the play area.
     * This map is used for checking the requirements of gold cards and for attributing points for objective cards and gold cards.
     * The key is the symbol and the value is the number of occurrences of that symbol in the play area.
     */
    private final Map<Symbol, Integer> symbols;

    /**
     * A dynamic matrix of SideOfCard objects. This matrix represents the current state of the play area.
     * A SideOfCard object is used because when a card is present in the play area, the player has already made a choice about what side they want to play.
     */
    private List<List<SideOfCard>> cardsOnArea=new ArrayList<>();

    /**
     * Default constructor for the PlayArea class.
     * It initializes the play area with an empty list of lists, representing an empty play area.
     * It also adds a null element to the first list, representing an empty space.
     * Finally, it initializes the symbols map, which will keep track of the symbols present in the play area.
     */
    public PlayArea() {
        cardsOnArea.add(new ArrayList<>()); // Adding an inner list
        cardsOnArea.getFirst().add(null); // Adding a null element to the inner list
        symbols = InitializeSymbolMap();
    }

    /**
     * This is a constructor for the PlayArea class that takes a matrix of cards and a map of symbols as parameters.
     * It sets the state of the play area and the symbols map to the provided parameters.
     * This constructor is typically used when you want to create a PlayArea with a specific initial state and symbols map.
     *
     * @param cardsOnPlayArea A matrix of SideOfCard objects representing the initial state of the play area.
     * @param symbols A map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     */
    public PlayArea(List<List<SideOfCard>> cardsOnPlayArea, Map<Symbol, Integer> symbols) {
        this.cardsOnArea = cardsOnPlayArea;
        this.symbols = symbols;
    }

    /**
     * This method is a getter for the 'symbols' attribute of the PlayArea class.
     * The 'symbols' attribute is a map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     * This method is used to retrieve the current state of the 'symbols' map.
     *
     * @return A map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     */
    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    /**
     * This method is used to get the number of occurrences of a specified symbol on the play area.
     * The 'symbols' attribute is a map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     * This method is typically used when you want to check the frequency of a specific symbol in the play area.
     *
     * @param symbol The symbol whose occurrences we want to check.
     * @return The number of occurrences of the specified symbol in the play area.
     */
    public int getNumSymbols(Symbol symbol) {
        return symbols.get(symbol);
    }

    /**
     * This method is a setter for the 'cardsOnArea' attribute of the PlayArea class.
     * The 'cardsOnArea' attribute is a dynamic matrix of SideOfCard objects that represents the current state of the play area.
     * This method is used to set the state of the play area to a specific state.
     * It is primarily used for testing purposes, to set up specific scenarios for testing.
     *
     * @param cards A matrix of SideOfCard objects representing the new state of the play area.
     */
    public void setCardsOnArea(List<List<SideOfCard>> cards){
        this.cardsOnArea=cards;
    }

    /**
     * This method is used to initialize the 'symbols' map of the PlayArea class.
     * The 'symbols' map is a map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     * This method populates the map with all the symbols from the Symbol enum, and sets all their values to zero.
     * This method is typically used when you want to create a PlayArea with a specific initial state of the 'symbols' map.
     *
     * @return A map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area. Initially, all values are set to zero.
     */
    public Map<Symbol, Integer> InitializeSymbolMap() { //tested
        Map<Symbol, Integer> playAreaMap = new HashMap<>();
        for (Symbol symbol : Symbol.values()) {
            playAreaMap.put(symbol, 0);
        }
        return playAreaMap;

    }

    /**
     * This method is used to add the initial card to the play area. It is only used for the first card of the game.
     * Once the card is added, the matrix is expanded by adding a new row before and after, and a new column before and after.
     * The new card is placed in the center of the matrix.
     * This method also updates the 'symbols' map of the PlayArea class with the symbols of the new card.
     *
     * @param card The initial card to be added to the play area. This is a SideOfCard object representing the card to be added.
     */
    public void addInitialCardOnArea(SideOfCard card) {
        cardsOnArea.getFirst().set(0, card);
        AddSymbolsToArea(card);

        // Add an empty row at the beginning of the play area
        cardsOnArea.addFirst(new ArrayList<>(Collections.nCopies(cardsOnArea.getFirst().size(), null)));
        // Add an empty row at the end of the play area
        cardsOnArea.add(new ArrayList<>(Collections.nCopies(cardsOnArea.getFirst().size(), null)));

        // Add an empty column at the beginning of each row
        for (List<SideOfCard> row : cardsOnArea) {
            row.add(0, null);
        }
        // Add an empty column at the end of each row
        for (List<SideOfCard> row : cardsOnArea) {
            row.add(null);
        }
    }

    /**
     * This method is used to retrieve the card that occupies a specified position on the play area.
     * The play area is represented as a matrix of SideOfCard objects, where each position corresponds to a specific card.
     * If the specified position is valid and within the bounds of the play area, the method returns the card at that position.
     * If the specified position is not valid or outside the bounds of the play area, the method throws an ArrayIndexOutOfBoundsException.
     *
     * @param i The row index of the desired position. This should be a non-negative integer less than the number of rows in the play area.
     * @param j The column index of the desired position. This should be a non-negative integer less than the number of columns in the play area.
     * @return The card that occupies the specified position on the play area. This is a SideOfCard object representing the card at the specified position.
     * @throws ArrayIndexOutOfBoundsException If the specified position is not valid or outside the bounds of the play area.
     */
    public SideOfCard getCardInPosition(int i, int j) { //tested
        if(i<cardsOnArea.size() && j< cardsOnArea.getFirst().size()) {
            List<SideOfCard> internalList = getCardsOnArea().get(i);
            return internalList.get(j);
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid Position");
    }

    /**
     * This method checks if a column exists in the play area.
     * The play area is represented as a matrix of SideOfCard objects, where each column corresponds to a specific vertical section of the play area.
     * The method checks if the specified column index is within the bounds of the play area.
     * If the specified column index is valid and within the bounds of the play area, the method returns true.
     * If the specified column index is not valid or outside the bounds of the play area, the method returns false.
     *
     * @param column The column index to check. This should be a non-negative integer less than the number of columns in the play area.
     * @return A boolean value indicating whether the specified column exists in the play area. Returns true if the column exists, false otherwise.
     */
    public boolean columnExists(int column) {
        return column >= 0 && column < cardsOnArea.getFirst().size();
    }

    /**
     * This method checks if a row exists in the play area.
     * The play area is represented as a matrix of SideOfCard objects, where each row corresponds to a specific horizontal section of the play area.
     * The method checks if the specified row index is within the bounds of the play area.
     * If the specified row index is valid and within the bounds of the play area, the method returns true.
     * If the specified row index is not valid or outside the bounds of the play area, the method returns false.
     *
     * @param row The row index to check. This should be a non-negative integer less than the number of rows in the play area.
     * @return A boolean value indicating whether the specified row exists in the play area. Returns true if the row exists, false otherwise.
     */
    public boolean rowExists(int row) {
        return row >= 0 && row < cardsOnArea.size();
    }

    /**
     * This method checks the neighboring positions of a newly placed card on the play area.
     * It verifies if the new card covers other corners besides the one the player has indicated.
     * The method checks the positions where the card can cover other cards without covering two corners of the same card.
     * If a card in the searched position is found, it gets the corresponding corner to cover, through the CornerPosition Enum and sets the corner to covered.
     * It also updates the NextCorner attribute: these corners are now each others next.
     * Finally, it updates the symbols map: if the covered corner contained a symbol, now it is not visible on the map anymore.
     *
     * @param newCardPosition The position of the newly placed card. This is a Pair object where the first element is the row index and the second element is the column index.
     * @param newCard The card that has just been placed. This method is called after the card has been added to the PlayArea. This is a SideOfCard object representing the newly placed card.
     */
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

    /**
     * This method resets the 'InConfig' attribute of all the cards on the play area to false.
     * The 'InConfig' attribute is a boolean value that indicates whether a card is part of a specific configuration.
     * This method is used when we have finished checking the number of occurrences of one Objective Disposition, and we have to start to check for a new one.
     * By resetting the 'InConfig' attribute of all cards, we ensure that the next check starts from a clean state.
     */
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
     * This method is used to update the 'symbols' map of the PlayArea class with the symbols of a newly placed card.
     * The 'symbols' map is a map where the key is a Symbol and the value is the number of occurrences of that symbol in the play area.
     * The method iterates over the symbols of the new card, and for each symbol, it increases the corresponding value in the 'symbols' map by the number of occurrences of that symbol on the new card.
     * This method is typically used after a new card has been placed on the play area, to update the 'symbols' map with the symbols of the new card.
     *
     * @param NewCard The card that has just been placed on the play area. This is a SideOfCard object representing the newly placed card.
     */
    public void AddSymbolsToArea(SideOfCard NewCard) {
        for (Symbol symbol : NewCard.getSymbols().keySet()) {
            int newValue = NewCard.getSymbols().get(symbol) + symbols.get(symbol);
            symbols.put(symbol, newValue);
        }
    }

    /**
     * This method is used to add a new card to the play area.
     * The play area is represented as a matrix of SideOfCard objects, where each position corresponds to a specific card.
     * The method checks if the specified position is valid and not already occupied.
     * If the specified position is valid and not occupied, the method adds the new card to that position, covers the corners of the surrounding cards, expands the area if needed, and updates the 'symbols' map with the symbols of the new card.
     * If the specified position is not valid or already occupied, the method prints an error message.
     *
     * @param NewCard The card that the player wants to place on the play area. This is a SideOfCard object representing the card to be placed.
     * @param r The row index of the desired position. This should be a non-negative integer less than the number of rows in the play area.
     * @param c The column index of the desired position. This should be a non-negative integer less than the number of columns in the play area.
     */
    public void  addCardOnArea(SideOfCard NewCard, int r, int c) {
        //check if the desired position exists and is not already occupied
        if(rowExists(r) && columnExists(c)) {
            cardsOnArea.get(r).set(c, NewCard);//addCard in the desired position
            checkCloseNeighbours(new Pair<>(r,c), NewCard);//cover the corners of the surrounding cards
            IsEdgeCase(new Pair<>(r,c));//Expand Area if needed;
            AddSymbolsToArea(NewCard);//update playArea symbols map with the ones of the new card
            //other checks to be done in controller

        }
        else {
            System.out.println("Invalid Position");
        }
    }

    /**
     * This method checks if the placement of a new card in the desired position is an edge case,
     * and if the matrix needs to be expanded.
     * The method creates an instance of the EdgePositions class and iterates over its EdgeCases enum values.
     * For each EdgeCases enum value, it checks if the desired position for the new card is an edge position according to that enum value.
     * If the desired position is an edge position, it calls the ExpandArea method of the EdgeCases enum value to expand the play area.
     * This method is typically used after a new card has been placed on the play area, to ensure that the play area is expanded if necessary.
     *
     * @param positionPlacedCard The position where the player wants to place a new card. This is a Pair object where the first element is the row index and the second element is the column index.
     */
    public void IsEdgeCase(Pair<Integer, Integer>positionPlacedCard) {
        EdgePositions edgePositions=new EdgePositions();
        for (EdgePositions.EdgeCases key : EdgePositions.EdgeCases.values()) {
            if (key.isEdgePosition(positionPlacedCard, cardsOnArea)) {
                key.ExpandArea(cardsOnArea);
            }
        }
    }

    /**
     * This method is a getter for the 'cardsOnArea' attribute of the PlayArea class.
     * The 'cardsOnArea' attribute is a dynamic matrix of SideOfCard objects that represents the current state of the play area.
     * This method is used to retrieve the current state of the play area.
     *
     * @return A dynamic matrix of SideOfCard objects representing the current state of the play area.
     */
    public List<List<SideOfCard>> getCardsOnArea() {
        return cardsOnArea;
    }


}

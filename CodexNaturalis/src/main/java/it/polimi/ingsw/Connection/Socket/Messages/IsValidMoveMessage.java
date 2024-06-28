package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class extends the GenericMessage class and is used to represent a "Is Valid Move" message.
 * It contains an ArrayList of ArrayLists of SideOfCard objects representing the cards on the play area,
 * a HashMap of Symbols and Integers representing the symbols, an integer for the row, an integer for the column,
 * and a SideOfCard object representing the new card.
 */
public class IsValidMoveMessage extends GenericMessage {
    // Represents the cards on the play area. It is a 2D ArrayList where each element is a list of SideOfCard objects.
private final ArrayList<ArrayList<SideOfCard>> cardsOnPlayArea;

// Represents the symbols. It is a HashMap where the key is a Symbol and the value is an Integer.
private final HashMap<Symbol, Integer> symbols;

// Represents the row. It is an integer that specifies the row number.
private final int row;

// Represents the column. It is an integer that specifies the column number.
private final int column;

// Represents the new card. It is a SideOfCard object that specifies the new card to be placed on the play area.
private final SideOfCard newCard;

    /**
     * Constructs a new IsValidMoveMessage with the specified cards on the play area, symbols, row, column, and new card.
     * @param cardsOnPlayArea the cards on the play area
     * @param symbols the symbols
     * @param row the row
     * @param column the column
     * @param newCard the new card
     */
    public IsValidMoveMessage(List<List<SideOfCard>> cardsOnPlayArea, Map<Symbol, Integer> symbols, int row, int column, SideOfCard newCard) {
        this.cardsOnPlayArea = convertToArrayList(cardsOnPlayArea);
        this.symbols = convertToHashMap(symbols);
        this.row = row;
        this.column = column;
        this.newCard = newCard;
    }

    /**
     * Returns the cards on the play area.
     * @return a List of Lists of SideOfCard objects representing the cards on the play area
     */
    public List<List<SideOfCard>> getCardsOnPlayArea() {
        return convertToList(cardsOnPlayArea);
    }

    /**
     * Returns the symbols.
     * @return a Map of Symbols and Integers representing the symbols
     */
    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    /**
     * Returns the row.
     * @return an integer representing the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column.
     * @return an integer representing the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the new card.
     * @return a SideOfCard object representing the new card
     */
    public SideOfCard getNewCard() {
        return newCard;
    }

    /**
     * Converts a List of Lists of SideOfCard objects to an ArrayList of ArrayLists of SideOfCard objects.
     * This method creates a new ArrayList of ArrayLists, iterates over the original List of Lists,
     * and for each inner List, creates a new ArrayList with the same elements and adds it to the new ArrayList of ArrayLists.
     * @param originalList the original List of Lists of SideOfCard objects
     * @return an ArrayList of ArrayLists of SideOfCard objects with the same elements as the original List of Lists
     */
    private ArrayList<ArrayList<SideOfCard>> convertToArrayList(List<List<SideOfCard>> originalList) {
        ArrayList<ArrayList<SideOfCard>> newList = new ArrayList<>();

        for (List<SideOfCard> innerList : originalList) {
            newList.add(new ArrayList<>(innerList));
        }

        return newList;
    }

    /**
     * Converts an ArrayList of ArrayLists of SideOfCard objects to a List of Lists of SideOfCard objects.
     * This method creates a new List of Lists, iterates over the original ArrayList of ArrayLists,
     * and for each inner ArrayList, creates a new List with the same elements and adds it to the new List of Lists.
     * @param arrayList the original ArrayList of ArrayLists of SideOfCard objects
     * @return a List of Lists of SideOfCard objects with the same elements as the original ArrayList of ArrayLists
     */
    private List<List<SideOfCard>> convertToList(ArrayList<ArrayList<SideOfCard>> arrayList) {
        List<List<SideOfCard>> newList = new ArrayList<>();

        for (ArrayList<SideOfCard> innerList : arrayList) {
            newList.add(new ArrayList<>(innerList));
        }
        return newList;
    }

    /**
     * Converts a Map of Symbols and Integers to a HashMap of Symbols and Integers.
     * This method creates a new HashMap and passes the existing map to the constructor.
     * @param map the original Map of Symbols and Integers
     * @return a HashMap of Symbols and Integers with the same elements as the original Map
     */
    public static HashMap<Symbol, Integer> convertToHashMap(Map<Symbol, Integer> map) {
        // Create a new HashMap and pass the existing map to the constructor
        return new HashMap<>(map);
    }
}

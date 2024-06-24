package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsValidMoveMessage extends GenericMessage {
    private final ArrayList<ArrayList<SideOfCard>> cardsOnPlayArea;
    private final HashMap<Symbol, Integer> symbols;
    private final int row;
    private final int column;
    private final SideOfCard newCard;

    public IsValidMoveMessage(List<List<SideOfCard>> cardsOnPlayArea, Map<Symbol, Integer> symbols, int row, int column, SideOfCard newCard) {
        this.cardsOnPlayArea = convertToArrayList(cardsOnPlayArea);
        this.symbols = convertToHashMap(symbols);
        this.row = row;
        this.column = column;
        this.newCard = newCard;
    }

    public List<List<SideOfCard>> getCardsOnPlayArea() {
        return convertToList(cardsOnPlayArea);
    }

    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SideOfCard getNewCard() {
        return newCard;
    }

    private  ArrayList<ArrayList<SideOfCard>> convertToArrayList(List<List<SideOfCard>> originalList) {
        ArrayList<ArrayList<SideOfCard>> newList = new ArrayList<>();

        for (List<SideOfCard> innerList : originalList) {
            newList.add(new ArrayList<>(innerList));
        }

        return newList;
    }

    private List<List<SideOfCard>> convertToList(ArrayList<ArrayList<SideOfCard>> arrayList) {
        List<List<SideOfCard>> newList = new ArrayList<>();

        for (ArrayList<SideOfCard> innerList : arrayList) {
            newList.add(new ArrayList<>(innerList));
        }
        return newList;
    }

    public static HashMap<Symbol, Integer> convertToHashMap(Map<Symbol, Integer> map) {
        // Create a new HashMap and pass the existing map to the constructor
        return new HashMap<>(map);
    }
}

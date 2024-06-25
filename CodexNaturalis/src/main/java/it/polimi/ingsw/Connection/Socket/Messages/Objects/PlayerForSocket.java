package it.polimi.ingsw.Connection.Socket.Messages.Objects;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerForSocket extends Player {

    private ArrayList<ArrayList<SideOfCard>> cardsOnPlayAreaForSocket;
    private HashMap<Symbol, Integer> symbolsForSocket;

    public PlayerForSocket(Player p) {
        super(p);
        this.cardsOnPlayAreaForSocket = convertToArrayList(p.getPlayArea().getCardsOnArea());
        this.symbolsForSocket = convertToHashMap(p.getPlayArea().getSymbols());
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

    public List<List<SideOfCard>> getCardsOnAreaForSocket() {
        return convertToList(cardsOnPlayAreaForSocket);
    }

    public Map<Symbol, Integer> getSymbolsForSocket() {
        return symbolsForSocket;
    }
}

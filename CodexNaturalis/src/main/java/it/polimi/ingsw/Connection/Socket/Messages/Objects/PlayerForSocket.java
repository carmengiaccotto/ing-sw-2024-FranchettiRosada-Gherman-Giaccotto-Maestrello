package it.polimi.ingsw.Connection.Socket.Messages.Objects;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class extends the Player class and is used to represent a player in a socket-based connection.
 * It contains additional fields and methods to handle the conversion of data types for socket communication.
 */
public class PlayerForSocket extends Player {

    private ArrayList<ArrayList<SideOfCard>> cardsOnPlayAreaForSocket;
    private HashMap<Symbol, Integer> symbolsForSocket;

    /**
     * This constructor creates a new PlayerForSocket object based on an existing Player object.
     * It also converts the play area and symbols of the player to ArrayList and HashMap respectively for socket communication.
     *
     * @param p The existing Player object.
     */
    public PlayerForSocket(Player p) {
        super(p);
        this.cardsOnPlayAreaForSocket = convertToArrayList(p.getPlayArea().getCardsOnArea());
        this.symbolsForSocket = convertToHashMap(p.getPlayArea().getSymbols());
    }

    /**
     * This method is used to convert a List of List of SideOfCard objects to an ArrayList of ArrayList of SideOfCard objects.
     *
     * @param originalList The original List of List of SideOfCard objects.
     * @return The converted ArrayList of ArrayList of SideOfCard objects.
     */
    private  ArrayList<ArrayList<SideOfCard>> convertToArrayList(List<List<SideOfCard>> originalList) {
        ArrayList<ArrayList<SideOfCard>> newList = new ArrayList<>();

        for (List<SideOfCard> innerList : originalList) {
            newList.add(new ArrayList<>(innerList));
        }

        return newList;
    }

    /**
     * This method is used to convert an ArrayList of ArrayList of SideOfCard objects to a List of List of SideOfCard objects.
     *
     * @param arrayList The original ArrayList of ArrayList of SideOfCard objects.
     * @return The converted List of List of SideOfCard objects.
     */
    private List<List<SideOfCard>> convertToList(ArrayList<ArrayList<SideOfCard>> arrayList) {
        List<List<SideOfCard>> newList = new ArrayList<>();

        for (ArrayList<SideOfCard> innerList : arrayList) {
            newList.add(new ArrayList<>(innerList));
        }
        return newList;
    }

    /**
     * This method is used to convert a Map of Symbol and Integer pairs to a HashMap of Symbol and Integer pairs.
     *
     * @param map The original Map of Symbol and Integer pairs.
     * @return The converted HashMap of Symbol and Integer pairs.
     */
    public static HashMap<Symbol, Integer> convertToHashMap(Map<Symbol, Integer> map) {
        // Create a new HashMap and pass the existing map to the constructor
        return new HashMap<>(map);
    }

    /**
     * This method is used to get the cards on the play area for socket communication.
     *
     * @return The cards on the play area as a List of List of SideOfCard objects.
     */
    public List<List<SideOfCard>> getCardsOnAreaForSocket() {
        return convertToList(cardsOnPlayAreaForSocket);
    }

    /**
     * This method is used to get the symbols for socket communication.
     *
     * @return The symbols as a Map of Symbol and Integer pairs.
     */
    public Map<Symbol, Integer> getSymbolsForSocket() {
        return symbolsForSocket;
    }
}
package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Exceptions.DeckCreationException;
import it.polimi.ingsw.Model.JsonHandler.JsonDeckCreator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a generic deck of cards.
 * It implements Serializable interface for saving the state of an object.
 * The deck is represented as an ArrayList of Card objects.
 */
public class Deck implements Serializable {
    private ArrayList<? extends Card> deck;
    private String filepath = "CodexNaturalis/src/main/resources/";

    /**
     * This constructor initializes a deck of a specific type of cards.
     * It uses the JsonDeckCreator to create the deck from a JSON file.
     * The file path is constructed using the simple name of the DeckType class.
     *
     * @param DeckType The class of the cards that the deck should contain.
     * @throws IOException If there is an error reading the JSON file.
     */
    public Deck(Class<? extends Card> DeckType) throws IOException {
        String filepathfinal = (DeckType.getSimpleName() + ".json");
        this.deck = JsonDeckCreator.createDeckFromJson(DeckType, filepathfinal);
    }

//    /**
//     * This constructor is used for testing purposes.
//     * It also initializes a deck of a specific type of cards from a JSON file.
//     * If there is an error reading the JSON file, it throws a DeckCreationException.
//     *
//     * @param DeckType The class of the cards that the deck should contain.
//     * @param filepath The path to the JSON file.
//     */
//    public Deck(Class<? extends Card> DeckType, String filepath){
//        filepath=filepath.concat(DeckType.getSimpleName() + ".json");
//        try {
//            this.deck = JsonDeckCreator.createDeckFromJson(DeckType, filepath);
//        } catch (IOException e) {
//            throw new DeckCreationException("Error during deck creation", e);
//        }
//    }

    /**
     * This method returns the cards that are currently in the deck.
     *
     * @return An ArrayList of Card objects.
     */
    public ArrayList<? extends Card> getCards() {
        return deck;
    }

    /**
     * This method returns the size of the deck.
     *
     * @return The number of cards in the deck.
     */
    public int getSize(){
        return deck.size();
    }


    /**
     * This method is used to draw a card from the deck.
     * It first checks if the deck is empty. If it is, it throws a RuntimeException.
     * If the deck is not empty, it gets the last card from the deck, removes it from the deck, and returns it.
     *
     * @return The card that was drawn from the deck.
     * @throws RuntimeException If the deck is empty.
     */
    public Card drawCard(){
        if(deck.isEmpty())
            throw new RuntimeException("Deck is empty");
        Card card=deck.getLast();
        deck.remove(card);
        return card;
    }

    /**
     * This method is used to shuffle the cards in the deck.
     * It uses the Collections.shuffle method to randomly permute the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }
}

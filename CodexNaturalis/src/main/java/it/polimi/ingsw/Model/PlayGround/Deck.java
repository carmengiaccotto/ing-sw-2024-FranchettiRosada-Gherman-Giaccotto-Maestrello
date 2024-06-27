package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Exceptions.DeckCreationException;
import it.polimi.ingsw.Model.JsonHandler.JsonDeckCreator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**This is the class that implements the generic deck of cards*/
public class Deck implements Serializable {
    private ArrayList<? extends Card> deck;
    private String filepath = "CodexNaturalis/src/main/resources/";





    /**
     * Class Constructor
     * */

    public Deck(Class<? extends Card> DeckType) throws IOException {
        filepath=filepath.concat(DeckType.getSimpleName() + ".json");
        this.deck = JsonDeckCreator.createDeckFromJson(DeckType, filepath);
    }




    /**
     * Class Constructor for testing purpose
     * */

    public Deck(Class<? extends Card> DeckType, String filepath){
        filepath=filepath.concat(DeckType.getSimpleName() + ".json");
        try {
            this.deck = JsonDeckCreator.createDeckFromJson(DeckType, filepath);
        } catch (IOException e) {
            throw new DeckCreationException("Error during deck creation", e);
        }
    }





    /**
     * Returns the Cards that are currently contained in the Deck
    * @return ArrayList<CardClass>
     */

    public ArrayList<? extends Card> getCards() {
        return deck;
    }





    /**
     * Method that returns the size of the deck
     * @return Cards.size() the size of the deck we are considering
     * */

    public int getSize(){
        return deck.size();
    }


    /**Method used to draw a card from a deck. Returns the drawn Card and removes it from the deck
     * @return drawn card*/
    public Card drawCard(){
        if(deck.isEmpty())
            throw new RuntimeException("Deck is empty");
        Card card=deck.getLast();
        deck.remove(card);
        return card;
    }


    /**
     * Method used to shuffle the cards
     * */
    public void shuffle() {
        Collections.shuffle(deck);
    }
}

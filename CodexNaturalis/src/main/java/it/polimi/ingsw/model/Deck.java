package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**@author Denisa Gherman
* This is the class that implements the generic deck of cards*/
public class Deck {
    private ArrayList<Card> Cards;





    /**Class Constructor*/
    public Deck() {
        Cards = new ArrayList<>();
        shuffle();
    }









    /** This is the method that creates the deck with all the cards
    **/
    public void initializeDeck(Class<?> TypeOfDeck) throws IOException {
            String pathToJson = Jsonpath(TypeOfDeck);
            List<? extends Card> carte = readCardsFromJSON(pathToJson, TypeOfDeck);
            // Aggiungi le carte al mazzo o esegui altre operazioni necessarie
            // Esempio: mazzo.addAll(carte);

    }

    private String Jsonpath(Class<?> typeOfDeck) {
        return "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + typeOfDeck.getSimpleName() + ".json";
    }


    private List<? extends Card> readCardsFromJSON(String pathToJson, Class<?> typeOfDeck) throws IOException {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(pathToJson)) {
            Type typeOfList = new TypeToken<List<? extends Card>>(){}.getType();
            List<? extends Card> cards = gson.fromJson(fileReader, typeOfList);
            List<Card> initializedCards = new ArrayList<>();
            for (Card card : cards) {
                //initializeCardFields(card);
                initializedCards.add(card);
            }
            return initializedCards;
        }
    }




    /**Returns the Cards that are currently contained in the Deck
    * @return ArrayList<PairOfCards>*/
    public ArrayList<Card> getCards() {
        return Cards;
    }



    /**@returns Cards.size() the size of the deck we are considering*/
    public int getSize(){

        return Cards.size();
    }






    /**Method that is going to be used to draw a Card from the Deck. It returns a card with both sides (front and back)
    The player is later going to choose which side they want to play with
    * @return PairOfCards the card that is on top of the Deck*/
    public Card getLastCard(){
        if (!Cards.isEmpty()) {
            return Cards.get(getSize()-1);
        } else {
            System.out.println("The Deck is Empty");
            return null;
        }

    }






   /**Shuffles the cards of the Deck. Uses Collection library*/
    public void shuffle(){
        Collections.shuffle(Cards);
    }





    /**Method that allows to draw a card from the Deck.
    * If the deck is empty:
    * @return null
    * else
    * @return PairOfCards using getLastCard() method
    * It also removes the Drawn Card from the deck, which is still the last one*/
    public Card DrawCard(){
        if (Cards.isEmpty()) {
            System.out.println("The deck is empty.");//Throw exception here
            return null;
        }
        else{
            Card drawnCard = getLastCard();
            Cards.remove(getSize()-1);
            return drawnCard;

        }
    }

    }

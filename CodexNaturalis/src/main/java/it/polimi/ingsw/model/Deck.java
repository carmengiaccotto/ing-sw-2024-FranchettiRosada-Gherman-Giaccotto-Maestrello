package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**@author Denisa Gherman
* This is the class that implements the generic deck of cards*/
public class Deck {
    private ArrayList<Card> Cards;





    /**Class Constructor*/
    public Deck(ArrayList<Card> cards) {
        Cards = new ArrayList<>();
    }

    public Deck createDeckFromJson(Class<? extends Card> DeckType) {
        try {
            Gson gson = new Gson();
            String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + DeckType.getSimpleName() + ".json";
            JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray(DeckType.getSimpleName());

            ArrayList<Card> cards = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                Card card = gson.fromJson(jsonElement, DeckType);
                cards.add(card);
            }
            return new Deck(cards);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }










    /*private String Jsonpath(Class<? extends Card> typeOfDeck) {
        return "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + typeOfDeck.getSimpleName() + ".json";
    }*/





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

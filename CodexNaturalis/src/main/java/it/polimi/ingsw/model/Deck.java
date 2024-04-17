package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**@author Denisa Gherman
* This is the class that implements the generic deck of cards*/
public class Deck {
    private ArrayList<Card> deck;





    /**Class Constructor*/
    public Deck(ArrayList<Card> Cards) {
        this.deck = Cards;
    }

    public Deck createDeckFromJson(Class<? extends Card> DeckType) throws IllegalArgumentException, IOException {
        if (DeckType == null) {
            throw new IllegalArgumentException("DeckType cannot be null");
        }

        String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + DeckType.getSimpleName() + ".json";
        try (FileReader fileReader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray(DeckType.getSimpleName());

            ArrayList<Card> deck= new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                Card card= gson.fromJson(jsonElement, DeckType);
                deck.add(card);
            }
            return new Deck(deck);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Invalid JSON format in file: " + filePath, e);
        }
    }










    /*private String Jsonpath(Class<? extends Card> typeOfDeck) {
        return "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + typeOfDeck.getSimpleName() + ".json";
    }*/





    /**Returns the Cards that are currently contained in the Deck
    * @return ArrayList<PairOfCards>*/
    public ArrayList<Card> getCards() {
        return deck;
    }



    /**@return Cards.size() the size of the deck we are considering*/
    public int getSize(){

        return deck.size();
    }






    /**Method that is going to be used to draw a Card from the Deck. It returns a card with both sides (front and back)
    The player is later going to choose which side they want to play with
    * @return PairOfCards the card that is on top of the Deck*/
    public Card getLastCard(){
        if (!deck.isEmpty()) {
            return deck.get(getSize()-1);
        } else {
            System.out.println("The Deck is Empty");
            return null;
        }

    }


    /**Method that allows to draw a card from the Deck.
     *
     * @return null If the deck is empty,  else return PairOfCards using getLastCard() method
     * It also removes the Drawn Card from the deck, which is still the last one*/
    public Card DrawCard(){
        if (deck.isEmpty()) {
            System.out.println("The deck is empty.");//Throw exception here
            return null;
        }
        else{
           Card drawnPlayCard = getLastCard();
            deck.remove(getSize()-1);
            return drawnPlayCard;

        }
    }






    }

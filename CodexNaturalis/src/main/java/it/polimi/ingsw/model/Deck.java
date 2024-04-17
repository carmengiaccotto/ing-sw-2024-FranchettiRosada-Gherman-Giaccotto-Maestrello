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
    private ArrayList<PlayCard> playCards;





    /**Class Constructor*/
    public Deck(ArrayList<PlayCard> playCards) {
        this.playCards = new ArrayList<>();
    }

    public Deck createDeckFromJson(Class<? extends PlayCard> DeckType) {
        try {
            Gson gson = new Gson();
            String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + DeckType.getSimpleName() + ".json";
            JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray(DeckType.getSimpleName());

            ArrayList<PlayCard> playCards = new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                PlayCard playCard = gson.fromJson(jsonElement, DeckType);
                playCards.add(playCard);
            }
            return new Deck(playCards);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }










    /*private String Jsonpath(Class<? extends Card> typeOfDeck) {
        return "CodexNaturalis/src/main/java/it/polimi/ingsw/model/" + typeOfDeck.getSimpleName() + ".json";
    }*/





    /**Returns the Cards that are currently contained in the Deck
    * @return ArrayList<PairOfCards>*/
    public ArrayList<PlayCard> getCards() {
        return playCards;
    }



    /**@returns Cards.size() the size of the deck we are considering*/
    public int getSize(){

        return playCards.size();
    }






    /**Method that is going to be used to draw a Card from the Deck. It returns a card with both sides (front and back)
    The player is later going to choose which side they want to play with
    * @return PairOfCards the card that is on top of the Deck*/
    public PlayCard getLastCard(){
        if (!playCards.isEmpty()) {
            return playCards.get(getSize()-1);
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
    public PlayCard DrawCard(){
        if (playCards.isEmpty()) {
            System.out.println("The deck is empty.");//Throw exception here
            return null;
        }
        else{
            PlayCard drawnPlayCard = getLastCard();
            playCards.remove(getSize()-1);
            return drawnPlayCard;

        }
    }






    }

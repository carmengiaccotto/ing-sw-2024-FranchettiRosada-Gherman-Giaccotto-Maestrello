package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Card;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**This is the class that implements the generic deck of cards*/
public class Deck {
    private final ArrayList<Card> deck;





    /**Class Constructor*/
    public Deck(ArrayList<Card> Cards) {
        this.deck = Cards;
    }


    /**JSON General Deck Constructor
     * @param DeckType class of cards we want to punt in the Deck
     * @return deck arrayList od cards on the desired type
     * JSON Card files and Card Classes have the sam name, so we use DeckType.getSimpleName() to build the Json path*/
    public ArrayList<Card> createDeckFromJson(Class<? extends Card> DeckType) throws IllegalArgumentException, IOException {
        if (DeckType == null) {
            throw new IllegalArgumentException("DeckType cannot be null");
        }

        String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/JsonFiles" + DeckType.getSimpleName() + ".json";
        try (FileReader fileReader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray(DeckType.getSimpleName());

            ArrayList<Card> deck= new ArrayList<>();
            for (JsonElement jsonElement : jsonArray) {
                Card card= gson.fromJson(jsonElement, DeckType);
                deck.add(card);
            }
            return deck;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Invalid JSON format in file: " + filePath, e);
        }
    }






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
    public Card getLastCard() {
        if (!deck.isEmpty()) {
            return deck.get(getSize() - 1);
        } else {
            System.out.println("The Deck is Empty");
            return null;
        }

    }





    }

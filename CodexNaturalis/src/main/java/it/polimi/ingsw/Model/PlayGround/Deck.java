package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.JsonHandler.JsonDeckCreator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**This is the class that implements the generic deck of cards*/
public class Deck implements Serializable {
    private final ArrayList<? extends Card> deck;
    private String filepath = "CodexNaturalis/src/main/resources/";





    /**Class Constructor*/

    public Deck(Class<? extends Card> DeckType) throws IOException {
        filepath=filepath.concat(DeckType.getSimpleName() + ".json");
        this.deck = JsonDeckCreator.createDeckFromJson(DeckType, filepath);
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


    /**
     * Getter method for filePath attribute
     * @return filepath
     * */

    public String getFilepath() {
        return filepath;
    }



    /**Setter method for filePath attribute
     * @param filepath string of the filepath. Used for testing reasons*/

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}

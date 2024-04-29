package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Card;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.JsonHandler.JsonDeckCreator;

import java.io.IOException;
import java.util.ArrayList;

/**This is the class that implements the generic deck of cards*/
public class Deck {
    private final ArrayList<Card> deck;





    /**Class Constructor*/
    public Deck(Class<? extends Card> DeckType) throws IOException {
        this.deck = JsonDeckCreator.createDeckFromJson(DeckType);
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

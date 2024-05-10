package it.polimi.ingsw.model.PlayGround;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.JsonHandler.JsonDeckCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**This is the class that implements the generic deck of cards*/
public class Deck implements Iterable{
    private final ArrayList<? extends Card> deck;





    /**Class Constructor*/
    public Deck(Class<? extends Card> DeckType) throws IOException {
        this.deck = JsonDeckCreator.createDeckFromJson(DeckType);
    }





    

    /**Returns the Cards that are currently contained in the Deck
    * @return ArrayList<CardClass>*/
    public ArrayList<? extends Card> getCards() {
        return deck;
    }



    /**@return Cards.size() the size of the deck we are considering*/
    public int getSize(){

        return deck.size();
    }

    @Override
    public Iterator<? extends Card> iterator() {
        return deck.iterator();
    }


//    /**
//     * Method that is going to be used to draw a Card from the Deck. It returns a card with both sides (front and back)
//     * The player is later going to choose which side they want to play with
//     *
//     * @return PairOfCards the card that is on top of the Deck
//     */
//    public <? extends Card> getLastCard() {
//        if (!deck.isEmpty()) {
//            return deck.get(deck.size() - 1);
//        } else {
//            System.out.println("The Deck is Empty");
//            return null;
//        }
//    }





    }

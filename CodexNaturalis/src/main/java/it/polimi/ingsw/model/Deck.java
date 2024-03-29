package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;

import java.util.ArrayList;
import java.util.Collections;
/**@author Denisa Gherman
* This is the class that implements the generic deck of cards*/
public class Deck {
    /* We are going to need it to create the decks of the different type of cards */
    private String TypeOfDeck;
    private ArrayList<Card> Cards;





    /**Class Constructor*/
    public Deck(String TypeOfDeck) {
        Cards = new ArrayList<>();
        initializeDeck(TypeOfDeck);
        shuffle();
    }




    /**TypeOfDeck attribute getter*/
    public String getTypeOfDeck() {
        return TypeOfDeck;
    }






    /**TypeOfDeck attribute setter*/
    public void setTypeOfDeck(String typeOfDeck) {
        TypeOfDeck = typeOfDeck;
    }





    /** This is the method that creates the deck with all the cards
    * @param TypeOfDeck*/
    public void initializeDeck(String TypeOfDeck){
        //Initialize Deck with Json File
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
            System.out.println("The deck is empty.");
            return null;
        }
        else{
            Card drawnCard = getLastCard();
            Cards.remove(getSize()-1);
            return drawnCard;

        }
    }

    }

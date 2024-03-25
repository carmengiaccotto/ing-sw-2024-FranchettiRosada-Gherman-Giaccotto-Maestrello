package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private String TypeOfDeck;
    private ArrayList<PairOfCards> Cards;


    public Deck(String TypeOfDeck) {
        Cards = new ArrayList<>();
        initializeDeck(TypeOfDeck);
        shuffle();
    }

    public String getTypeOfDeck() {
        return TypeOfDeck;
    }

    public void setTypeOfDeck(String typeOfDeck) {
        TypeOfDeck = typeOfDeck;
    }

    public void initializeDeck(String TypeOfDeck){
        //Initialize Deck with Json File
    }

    public ArrayList<PairOfCards> getCards() {
        return Cards;
    }

    public PairOfCards getLastCard(){ //returns drawn card
        if (!Cards.isEmpty()) {
            return Cards.get(getSize()-1);
        } else {
            System.out.println("The Deck is Empty");
            return null;
        }

    }


    public int getSize(){

        return Cards.size();
    }

    public void shuffle(){
        Collections.shuffle(Cards);
    }

    public PairOfCards DrawCard(){
        if (Cards.isEmpty()) {
            System.out.println("The deck is empty.");
            return null;
        }
        else{
            PairOfCards drawnCard = getLastCard();
            Cards.remove(getSize()-1);
            return drawnCard;

        }
    }

    }

package it.polimi.ingsw.Model.PlayGround;


import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Chat;
import it.polimi.ingsw.Model.Chat.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * GameModel class
 * GameModel is the class that represents the game, it contains all the information about the game, and it's based on MVC pattern
 * It contains the list of players, the list of common play cards and common Objectives, the two deck, the game ID, the chat and the game status
 * It also contains the current player that is playing
 */

public class PlayGround implements Serializable {


    /**
     * It contains Decks
     */
    private final Deck GoldCardDeck;
    private final Deck ResourceCardDeck;
    private final Deck ObjectiveCardDeck;
    private final Deck InitialCardDeck;

    /**
     * It contains the list of common resourceCards, goldCard and objective card
     */
    private final ArrayList<ResourceCard> commonResourceCards;
    private final ArrayList<GoldCard> commonGoldCards;
    private final ArrayList<ObjectiveCard> commonObjectivesCards;


    /**
     * It contains the chat of the game
     */
    private Chat chat;


    /**
     * Constructor
     */
    public PlayGround() throws IOException {

        GoldCardDeck = new Deck(GoldCard.class);
        GoldCardDeck.shuffle();
        ResourceCardDeck = new Deck(ResourceCard.class);
        ResourceCardDeck.shuffle();
        ObjectiveCardDeck = new Deck(ObjectiveCard.class);
        ObjectiveCardDeck.shuffle();
        InitialCardDeck = new Deck(InitialCard.class);
        InitialCardDeck.shuffle();

        commonResourceCards = new ArrayList<>();
        commonGoldCards = new ArrayList<>();
        commonObjectivesCards = new ArrayList<>();

    }

    /**
     * @return the GoldCardDeck
     */
    public Deck getGoldCardDeck() {
        return GoldCardDeck;
    }

    /**
     * @return the InitialCardDeck
     */
    public Deck getInitialCardDeck() {
        return InitialCardDeck;
    }


    /**
     * @return the ObjectiveCardDeck
     */
    public Deck getObjectiveCardDeck() {
        return ObjectiveCardDeck;
    }

    /**
     * @return the ResourceCardDeck
     */
    public Deck getResourceCardDeck() {
        return ResourceCardDeck;
    }





    /**
     * Add a card to GameModel
     *
     * @param c card
     */
    public void addCommonCard(Card c) {

        switch (c) {
            case GoldCard goldCard -> commonGoldCards.add(goldCard);
            case ResourceCard resourceCard -> commonResourceCards.add(resourceCard);
            case ObjectiveCard objectiveCard -> commonObjectivesCards.add(objectiveCard);
            case null, default -> throw new IllegalArgumentException("Unsupported card type");
        }
    }



    /**
     * @return the chat
     */
    public Chat getChat() {
        return chat;
    }


    /**
     * @return the commonResourceCard extracted list
     */
    public ArrayList<ResourceCard> getCommonResourceCards() {
        return commonResourceCards;
    }

    /**
     * @return the commonObjectiveCard extracted list
     */
    public ArrayList<ObjectiveCard> getCommonObjectivesCards() {
        return commonObjectivesCards;
    }

    /**
     * @return the commonGoldCard extracted list
     */
    public ArrayList<GoldCard> getCommonGoldCards() {
        return commonGoldCards;
    }





    /**
     * Sends a message
     *
     * @param m message sent
     */

    public void sentMessage(Message m) {
        chat.addMessage(m);
    }




/**
 * Method that substitutes the drawn card from the playGround
 * @param card that the player wants to draw
 * @param i index of the card. Used to substitute the card in the right position.
 * */
    public void drawCardFromPlayground(int i, PlayCard card){
        if (card instanceof GoldCard){
            commonGoldCards.remove(card);
            commonGoldCards.add(i,(GoldCard) GoldCardDeck.drawCard());

    }else{
            commonResourceCards.remove(card);
            commonResourceCards.add(i,(ResourceCard) ResourceCardDeck.drawCard());
        }
    }



}





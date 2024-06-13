package it.polimi.ingsw.Model.PlayGround;


import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Chat;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Exceptions.GameEndedException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GameModel class
 * GameModel is the class that represents the game, it contains all the information about the game, and it's based on MVC pattern
 * It contains the list of players, the list of common play cards and common Objectives, the two deck, the game ID, the chat and the game status
 * It also contains the current player that is playing
 */

public class PlayGround implements Serializable {

    /**
     * It contains the list of all players, online and offline
     */
    private ArrayList<Player> players;

    /**
     * It contains the list of online players
     */
    private ArrayList<Player> onlinePlayers;

    /**
     * It contains Decks
     */
    private Deck GoldCardDeck;
    private Deck ResourceCardDeck;
    private Deck ObjectiveCardDeck;
    private Deck InitialCardDeck;

    /**
     * It contains the list of common resourceCards, goldCard and objective card
     */
    private ArrayList<ResourceCard> commonResourceCards;
    private ArrayList<GoldCard> commonGoldCards;
    private ArrayList<ObjectiveCard> commonObjectivesCards;

    /**
     * It contains the id of the game
     */
    private Integer gameId;

    private Integer numOfPlayers;

    /**
     * It contains the nickname of the current player that is playing
     */
    private String currentPlayer;

    /**
     * It contains the chat of the game
     */
    private Chat chat;

    /**
     * It contains the status of the game
     */

    /**
     * Constructor
     */
    public PlayGround(Integer gameId) throws IOException {
        players = new ArrayList<Player>();
        onlinePlayers = new ArrayList<Player>();

        GoldCardDeck = new Deck(GoldCard.class);
        ResourceCardDeck = new Deck(ResourceCard.class);
        ObjectiveCardDeck = new Deck(ObjectiveCard.class);
        InitialCardDeck = new Deck(InitialCard.class);

        commonResourceCards = new ArrayList<ResourceCard>();
        commonGoldCards = new ArrayList<GoldCard>();
        commonObjectivesCards = new ArrayList<ObjectiveCard>();

        this.gameId = gameId;

        currentPlayer = null;
        chat = new Chat();
        numOfPlayers = 0;
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

    public void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
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
     * @return the number of total players
     */
    public int getNumOfCurrentPlayers() {
        return players.size();
    }

    /**
     * @return the maximum number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }


    /**
     * @return the number of online players
     */
    public int getNumOfOnlinePlayers() {
        return onlinePlayers.size();
    }

    /**
     * @return the number of players required by the game
     */
    public int getSpecificNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * @return player's list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return online player's list
     */
    public ArrayList<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    /**
     * @return the number of commonPlayCards extracted
     */
    public int getNumOfCommonPlayCards() {
        return commonResourceCards.size();
    }

    /**
     * @param i index of common resource card
     * @return common resource card corresponding to said index
     */
    public PlayCard getCommonResourceCards(int i) {
        return commonResourceCards.get(i);
    }

    /**
     * @param nickname of the player to check
     * @return the player's card goal
     */
    public ObjectiveCard getPlayerObjectiveCard(String nickname) {
        for (Player name : players) {
            if (name.getNickname().equals(nickname))
                return name.getPersonalObjectiveCard();
        }
        throw new IllegalArgumentException("The player doesn't exist");
    }

    /**
     * Add a card to GameModel
     *
     * @param c
     */
    public void addCommonCard(Card c) {

        if (c instanceof GoldCard) {
            commonGoldCards.add((GoldCard) c);
        } else if (c instanceof ResourceCard) {
            commonResourceCards.add((ResourceCard) c);
        } else if (c instanceof ObjectiveCard) {
            commonObjectivesCards.add((ObjectiveCard) c);
        } else {
            throw new IllegalArgumentException("Unsupported card type");
        }
    }

    /**
     * Remove card from deck d
     *
     * @param d
     */
    public void removeCardFromDeck(Card c, Deck d) {

        d.getCards().remove(c);
    }

    /**
     * @return the game id
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * Set game id
     *
     * @param gameId new game id
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * @return nickname of current player playing
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player
     *
     * @param currentPlayer active playing player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
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
     * @return true if every player in the game has a personal goal assigned
     */
    public boolean doAllPlayersHaveObjecvtiveCard() {
        for (Player p : players) {
            if (p.getPersonalObjectiveCard() == null)
                return false;
        }
        return true;
    }


    /**
     * @return true if the player in turn is online
     */
    private boolean isTheCurrentPlayerOnline() {

        return onlinePlayers.contains(currentPlayer);

    }


    /**
     * Reconnect a player to the Game unless the game is already over
     */

    public void reconnectPlayer(Player p) throws GameEndedException {

        if (players.contains(p) && (!onlinePlayers.contains(p))) {
            onlinePlayers.add(p);
        } else {
            System.out.println("ERROR: Trying to reconnect a online player or a player not logged in the game");
        }
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





package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;


import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Chat;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.GameEndedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * PlayGround class
 * PlayGround is the class that represents the GameModel, it contains all the information about the game.
 * It contains the list of players (online and offline) the list of common resource cards,
 * common Gold card and common Objectives, the four deck, the number of player, the game ID, the chat and the game status.
 * It also contains the current player that is playing.
 */

public class PlayGround {

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
    private ArrayList<PlayCard> commonResourceCards;
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
    private GameStatus status;

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

        commonResourceCards = null;
        commonGoldCards = null;
        commonObjectivesCards = null;

        this.gameId = gameId;

        currentPlayer = null;
        chat = new Chat();
        status = GameStatus.WAIT;
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
     * @return the number of total players
     */
    public int getNumOfPlayers() {
        return players.size();
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
    public int getSpecificNumOfPlayers(){
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
        for (Player name: players){
            if(name.getNickname().equals(nickname))
                return name.getPersonalObjectiveCard();
        }
        throw new IllegalArgumentException("The player doesn't exist");
    }

    /**
     * Add a card to GameModel
     *
     * @param c
     */
    public void addCommonCard(Card c){

        if(c instanceof GoldCard){
            commonGoldCards.add((GoldCard) c);
        }
        else if(c instanceof ResourceCard) {
            commonResourceCards.add((ResourceCard) c);
        }
        else if( c instanceof ObjectiveCard){
            commonObjectivesCards.add((ObjectiveCard) c);
        }
        else {
            throw new IllegalArgumentException("Unsupported card type");
        }
    }

    /**
     * Remove card from deck d
     * @param card
     * @param d
     */
    public void removeCardFromDeck(Card card, Deck d){

        if(card instanceof GoldCard){
            Iterator<GoldCard> cardIterator = (Iterator<GoldCard>) d.iterator();

            while(cardIterator.hasNext()){
                GoldCard nexCard = cardIterator.next();
                if(nexCard.getIdCard() == (card.getIdCard())){
                    cardIterator.remove();
                }
            }
        }

        else if(card instanceof ResourceCard){
            Iterator<ResourceCard> cardIterator = (Iterator<ResourceCard>) d.iterator();

            while(cardIterator.hasNext()){
                ResourceCard nexCard = cardIterator.next();
                if(nexCard.getIdCard() == (card.getIdCard())){
                    cardIterator.remove();
                }
            }
        }

        else if(card instanceof InitialCard){
            Iterator<InitialCard> cardIterator = (Iterator<InitialCard>) d.iterator();

            while(cardIterator.hasNext()){
                InitialCard nexCard = cardIterator.next();
                if(nexCard.getIdCard() == (card.getIdCard())){
                    cardIterator.remove();
                }
            }
        }

        else if(card instanceof ObjectiveCard){
            Iterator<ObjectiveCard> cardIterator = (Iterator<ObjectiveCard>) d.iterator();

            while(cardIterator.hasNext()){
                ObjectiveCard nexCard = cardIterator.next();
                if(nexCard.getIdCard() == (card.getIdCard())){
                    cardIterator.remove();
                }
            }
        }
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
     * @return the game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * @return the commonResourceCard extracted list
     */
    public ArrayList<PlayCard> getCommonResourceCards() {
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

    public void reconnectPlayer(Player p) throws  GameEndedException {

        if(players.contains(p) && (!onlinePlayers.contains(p))){
            onlinePlayers.add(p);
        }
        else {
                System.out.println ("ERROR: Trying to reconnect a online player or a player not logged in the game");
            }
        }

    /** Method to be called by the first Player present in the lobby*/
    private void chooseNumOfPlayers(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter how many players you want to play with: ");
        this.numOfPlayers = scan.nextInt();
        if((numOfPlayers > 4) || (numOfPlayers <= 1)){

            throw new IllegalArgumentException("Invalid number of players.");

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
}



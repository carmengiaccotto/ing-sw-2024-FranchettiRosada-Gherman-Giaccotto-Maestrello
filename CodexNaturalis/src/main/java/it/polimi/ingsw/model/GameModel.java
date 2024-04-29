package CodexNaturalis.src.main.java.it.polimi.ingsw.model;


import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Deck;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GameModel class
 * GameModel is the class that represents the game, it contains all the information about the game, and it's based on MVC pattern
 * It contains the list of players, the list of common play cards and common Objectives, the two deck, the game ID, the chat and the game status
 * It also contains the current player that is playing
 */

public class GameModel {

    /**
     * It contains the list of players
     */
    private ArrayList<Player> players;

    /**
     * It contains the two Deck
     */
    private Deck GoldCardDeck;
    private Deck ResourceCardDeck;
    private Deck ObjectiveCardDeck;
    private Deck InitialCardDeck;

    /**
     * It contains the list of common resourceCards, goldCard objective card
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
    public GameModel(Integer gameId) throws IOException {
        players = new ArrayList<Player>();

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
    public ObjectiveCard getPlayerObjectiveCard(int nickname) {
        for (Player name: players){
            if(name.equals(nickname))
                return name.getPersonalObjectiveCard();
        }
        return null;
    }

    public void addCommonCard(Card c){
    }

    public void removeCardFromDeck(Card card, Deck d){

    }

    /**
     * @return the game id
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * Sets the game id
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
     * Sets the game status
     *
     * @param status
     */
    public void setStatus(GameStatus status) {
        //If I want to set the gameStatus to "RUNNING", there needs to be at least
        // DefaultValue.minNumberOfPlayers -> (2) in lobby
        if (status.equals(GameStatus.RUNNING) &&
                ((players.size() < DefaultValue.minNumOfPlayer
                        || getNumOfCommonPlayCards() != DefaultValue.NumOfCommonCards
                        || !doAllPlayersHaveGoalCard())
                        || getCurrentPlayer() == null)) {
            throw new NotReadyToRunException();
        } else {
            this.status = status;
        }
        if (status == GameStatus.ENDED) {
            decreeWinner(); //Find winner
        }
    }

    /**
     * @return true if every player in the game has a personal goal assigned
     */
    public boolean doAllPlayersHaveGoalCard() {
        for (Player p : players) {
            if (p.getPersonalObjectiveCard() == null)
                return false;
        }
        return true;
    }

    /**
     * Calls the next turn
     *
     * @throws GameEndedException
     */
    public void nextTurn() throws GameEndedException {

    }

    /**
     * @return true if the player in turn is online
     */
    private boolean isTheCurrentPlayerOnline() {

    }


    /**
     * Sends a message
     *
     * @param m message sent
     */
    public void sentMessage(Message m) {


    }

    /**
     * add a player to the game
     *
     */
    public void addPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {


    }

    /**
     * @return true if there are enough players to start, and if every one of them is ready
     */
    public boolean arePlayersReadyToStartAndEnough() {

    }

    /** Add a player to Game's lobby. The player will be asked to set a nickname */
    public void addPlayerToLobby(Player p) throws MaxNumPlayersReached {
    }

    /** Reconnect a player to the Game unless the game is already over*/
    public void reconnectPlayer(Player p) throws  GameEndedException {
    }

    /** Method to be called by the first Player present in the lobby*/
    private void chooseNumOfPlayers(){

    }

    public synchronized void sentMessage() {
    }

    public void initializeGame(){

    }

}

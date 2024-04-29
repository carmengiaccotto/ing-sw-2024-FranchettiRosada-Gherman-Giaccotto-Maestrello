package CodexNaturalis.src.main.java.it.polimi.ingsw.model;


import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.InitialCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.PlayCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Deck;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.DefaultValue;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;

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
    private Deck GoldDeck;
    private Deck PlayDeck;
    private Deck ObjectiveDeck;

    private Deck InitialCardDeck;



    /**
     * It contains the list of common play cards and objective card
     */

    private ArrayList<PlayCard> commonPlayCards;
    private ArrayList<ObjectiveCard> commonObjectives;

    /**
     * It contains the id of the game
     */
    private Integer gameId;

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
     *
     * @param players
     * @param commonPlayCards
     * @param commonObjectives
     * @param PlayDeck
     * @param GoldDeck
     * @param gameId
     * @param chat
     * @param status
     * @param currentPlayer
     *
     */
    public GameModel(ArrayList<Player> players, ArrayList<PlayCard> commonPlayCards, ArrayList<ObjectiveCard> commonObjectives, Deck PlayDeck, Deck ObjectiveDeck, Deck GoldDeck, Deck InitialCardDeck, Integer gameId, Chat chat, String currentPlayer, GameStatus status) {
        this.players = players;
        this.commonPlayCards = commonPlayCards;
        this.commonObjectives = commonObjectives;
        this.PlayDeck = PlayDeck;
        this.GoldDeck = GoldDeck;
        this.gameId = gameId;
        this.chat = chat;
        this.currentPlayer = currentPlayer;
        this.status = status;
        this.ObjectiveDeck = ObjectiveDeck;
        this.InitialCardDeck = InitialCardDeck;
    }


    /**
     * @return the GoldDeck
     */
    public Deck getGoldDeck() {
        return GoldDeck;
    }

    /**
     * @return the InitialCardDeck
     */
    public Deck getInitialCardDeck() {
        return InitialCardDeck;
    }

    /**
     * @return the ObjectiveDeck
     */
    public Deck getObjectiveDeck() {
        return ObjectiveDeck;
    }

    /**
     * @return the PlayDeck
     */
    public Deck getPlayDeck() {
        return PlayDeck;
    }

    /**
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return players.size();
    }

    /**
     * @return player's list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the number of common cards extracted
     */
    public int getNumOfCommonPlayCards() {
        return commonPlayCards.size();
    }

    /**
     * @param i index of common play card
     * @return common play card corresponding to said index
     */
    public PlayCard getCommonPlayCards(int i) {
        return commonPlayCards.get(i);
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
     * @return the common card extracted list
     */
    public ArrayList<PlayCard> getCommonPlayCards() {
        return commonPlayCards;
    }

    /**
     * @return the common card extracted list
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
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

        if (status == GameStatus.ENDED) {
            decreeWinner();//Find winner
            } else if (status == GameStatus.LAST_TURN) {

            }
        }
    }


    /**
     * @return true if every player in the game has a personal goal assigned
     */
    public boolean doAllPlayersHaveGoalCard() {
        for (Player p : players) {
            if (p.getPersonalObjectiveCard().equals(ObjectiveCard))
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

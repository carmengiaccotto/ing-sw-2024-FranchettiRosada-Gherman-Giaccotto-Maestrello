package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.ObjectiveCard;
import it.polimi.ingsw.model.Card;

import java.util.ArrayList;
import java.util.HashMap;
public class PlayGround {

    private static HashMap<Player, PlayArea> players;
    private static ArrayList<Deck> decks;
    private ArrayList<Card>  commonCards;
    private ArrayList<ObjectiveCard> commonObjectives;
    private Player currentPlayer;
    private GameStatus gameStatus;


    public PlayGround(HashMap<Player, PlayArea> players, ArrayList<Deck> decks, ArrayList <Card> commonCards, ArrayList<PairOfCards> commonCards, ArrayList<ObjectiveCard> commonObjectives, Player currentPlayer, GameStatus gameStatus) {
        this.players = players;
        this.decks = decks;
        this.commonCards = commonCards;
        this.commonObjectives = commonObjectives;
        this.currentPlayer = currentPlayer;
        this.gameStatus = gameStatus;
    }

    /**
     * getter method for the PlayGround's players
     *
     * @return players
     */
    public HashMap<Player,PlayArea> getPlayers(){
        return players;
    }

    /**
     * getter method for the PlayGround's decks
     *
     * @return decks
     */






    /**@author Denisa Minodora Gherman
     * Method that returns the list of the players, without their playArea
     * @return playersList */
    public static ArrayList<Player> getListOfPlayers(){
        ArrayList<Player> playersList = new ArrayList<>();
        for (Player player : players.keySet()) {
            playersList.add(player);
        }
        return playersList;

    }







    public static ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * getter method for the PlayGround's commonCards
     *
     * @return commonCards
     */
    public static ArrayList<Card> getCommonCards() {
        return commonCards;
    }

    /**
     * getter method for the PlayGround's commonObjectives
     *
     * @return commonObjectives
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * getter method for the PlayGround's commonObjectives
     *
     * @return currentPlayer
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * getter method for the PlayGround's gameStatus
     *
     * @return gameStatus
     */
    public GameStatus getGameStatus(){
        return gameStatus;
    }

    /**
     * currentPlayer setter Method
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * gameStatus setter Method
     *
     * @param gameStatus
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * ....
     *
     *
     */
    public HashMap<Player, PlayArea> addPlayer(HashMap<Player, PlayArea> players, Player player, PlayArea playarea){
        players.put(player, playarea);
        return players;
    }

    /**
     * ....
     *
     *
     */
    public boolean CheckUniqueNickNames(){
    }

    /**
     * ....
     *
     *
     */
    public ArrayList<Card> addCommonCard(Card card){
        commonCards.add(card);
        return commonCards;
    }

    /**
     * ....
     *
     *
     */
    public void initializeGame(){
    }

    /**
     * ....
     *
     *
     */
    public void addObjectiveScore(){
    }

    /**
     * ....
     *
     *
     */
    public Player Winner(){
    }
}




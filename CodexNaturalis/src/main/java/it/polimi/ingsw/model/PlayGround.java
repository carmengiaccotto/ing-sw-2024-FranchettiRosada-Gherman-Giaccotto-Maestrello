package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import it.polimi.ingsw.model.ObjectiveCard;
public class PlayGround {

    private HashMap<Player, PlayArea> players;
    private static ArrayList<Deck> decks;
    private ArrayList<PairOfCards>  commonCards;
    private ArrayList<ObjectiveCard> commonObjectives;
    private Player currentPlayer;
    private GameStatus gameStatus;


    public PlayGround(HashMap<Player, PlayArea> players, ArrayList<Deck> decks, ArrayList <PairOfCards> commonCards, ArrayList<PairOfCards> commonCards, ArrayList<ObjectiveCard> commonObjectives, Player currentPlayer, GameStatus gameStatus) {
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
    public static ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * getter method for the PlayGround's commonCards
     *
     * @return commonCards
     */
    public ArrayList<PairOfCards> getCommonCards() {
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
    public ArrayList<PairOfCards> addCommonCard(Card card){
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

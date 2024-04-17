//package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
//
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
///**@author Lucrezia Maestrello
// * Class that implements the PlayGround of the play.
// */
//public class PlayGround {
//
//    private static HashMap<Player, PlayArea> players;
//    private static ArrayList<Deck> decks;
//    private ArrayList<PlayCard> commonPlayCards;
//    private ArrayList<ObjectiveCard> commonObjectives;
//    private Player currentPlayer;
//    private GameStatus gameStatus;
//
//    /**
//     * Constructor of the class PlayGround
//     *
//     * @param players
//     * @param decks
//     * @param commonPlayCards
//     * @param commonPlayCards
//     * @param commonObjectives
//     * @param currentPlayer
//     * @param gameStatus
//     */
//    public PlayGround(HashMap<Player, PlayArea> players, ArrayList<Deck> decks, ArrayList<PlayCard> commonPlayCards, ArrayList<ObjectiveCard> commonObjectives, Player currentPlayer, GameStatus gameStatus) {
//        this.players = players;
//        this.decks = decks;
//        this.commonPlayCards = commonPlayCards;
//        this.commonObjectives = commonObjectives;
//        this.currentPlayer = currentPlayer;
//        this.gameStatus = gameStatus;
//    }
//
//
//    /**
//     * @return playersList
//     * @author Denisa Minodora Gherman
//     * Method that returns the list of the players, without their playArea
//     */
//    public static ArrayList<Player> getListOfPlayers() {
//        ArrayList<Player> playersList = new ArrayList<>();
//        for (Player player : players.keySet()) {
//            playersList.add(player);
//        }
//        return playersList;
//
//    }
//
//    /**
//     * getter method for the PlayGround's players
//     *
//     * @return players
//     */
//    public HashMap<Player, PlayArea> getPlayers() {
//        return players;
//    }
//
//    /**
//     * getter method for the PlayGround's decks
//     *
//     * @return decks
//     */
//    public static ArrayList<Deck> getDecks() {
//        return decks;
//    }
//
//    /**
//     * getter method for the PlayGround's commonCards
//     *
//     * @return commonCards
//     */
//    public static ArrayList<PlayCard> getCommonCards() {
//        return commonPlayCards;
//    }
//
//    /**
//     * getter method for the PlayGround's commonObjectives
//     *
//     * @return commonObjectives
//     */
//    public ArrayList<ObjectiveCard> getCommonObjectives() {
//        return commonObjectives;
//    }
//
//    /**
//     * getter method for the PlayGround's commonObjectives
//     *
//     * @return currentPlayer
//     */
//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }
//
//    /**
//     * getter method for the PlayGround's gameStatus
//     *
//     * @return gameStatus
//     */
//    public GameStatus getGameStatus() {
//        return gameStatus;
//    }
//
//    /**
//     * currentPlayer setter Method
//     *
//     * @param currentPlayer
//     */
//    public void setCurrentPlayer(Player currentPlayer) {
//        this.currentPlayer = currentPlayer;
//    }
//
//    /**
//     * gameStatus setter Method
//     *
//     * @param gameStatus
//     */
//    public void setGameStatus(GameStatus gameStatus) {
//        this.gameStatus = gameStatus;
//    }
//
//    /**
//     * Method to add a player with his playarea to the list of players
//     *
//     * @param players
//     * @param player
//     * @param playarea
//     * @return HashMap<Player, PlayArea>
//     */
//    public HashMap<Player, PlayArea> addPlayer(HashMap<Player, PlayArea> players, Player player, PlayArea playarea) {
//        players.put(player, playarea);
//        return players;
//    }
//
//    /**
//     * Method to check that the nickname chosen by the player is unique.
//     *
//     * @param chosenNickName
//     * @return boolean
//     */
//    public boolean CheckUniqueNickNames(String chosenNickName) {
//        boolean AvailableNickName= true;
//        while(AvailableNickName) {
//            for (Player player : getListOfPlayers()) AvailableNickName = !(player.getNickname().equals(chosenNickName));
//
//            }
//        return AvailableNickName;
//    }
//
//    /**
//     * Method to replace a common card when the one on the table has been taken.
//     *
//     * @param playCard
//     * @return ArrayList<Card>
//     */
//    public ArrayList<PlayCard> addCommonCard(PlayCard playCard) {
//        commonPlayCards.add(playCard);
//        return commonPlayCards;
//    }
//
//    /**
//     * Method to initialize the game.
//     * Shuffles the decks of cards, distributes the cards to the players and prepares the game board.
//     */
//    public void initializeGame() {
//        decks = new ArrayList<Deck>();
//        commonPlayCards = new ArrayList<PlayCard>();
//        commonObjectives = new ArrayList<ObjectiveCard>();
//
//        //initialize decks with the cards from the Json file
//        // for(Deck deck: decks){
//        //   deck = readCardsFromJson("");
//        // }
//        for(int i = 0; i <= decks.size(); i++){
//            decks.get(i).shuffle();
//        }
//        commonPlayCards.get(0) = decks.get(0).getLastCard();
//        commonPlayCards.get(1) = decks.get(0).getLastCard();
//        commonObjectives.get(1) = decks.get(1).getLastCard();
//
//    } //to revise
//
//    /**
//     *
//     */
//    public void addObjectiveScore(Player player, HashMap<Player, PlayArea> players, ArrayList<ObjectiveCard> commonObjectives ) {
//        PlayArea playArea = players.get(player);
//        for(ObjectiveCard commonobjectives : getCommonObjectives()){
//        }
//    } // to revise + javaDoc missing
//
//    /** Method to check if the current player has won.
//     * @param currentPlayer
//     * @return boolean
//     */
//    public static boolean Winner(Player currentPlayer) {
//        //check that we do outside of the method every time the player plays and his score is updated?
//        // if (currentPlayer.getScore() >= 20) ...
//        ArrayList<Player> playersList = new ArrayList<>();
//        for (Player player : playersList) {
//            if ((currentPlayer.getRound() != player.getRound()) && currentPlayer.getNickname() != player.getNickname()){
//                return false;
//            }
//        }
//        for (Player player : playersList) {
//            // addObjectivescore(player);
//            // adds points of the common objectives cards
//        }
//        for (Player player : playersList){
//            if((currentPlayer.getScore() < player.getScore()) && currentPlayer.getNickname() != player.getNickname()){
//                return false;
//            }
//        }
//        return true;
//    } //to revise
//
//}







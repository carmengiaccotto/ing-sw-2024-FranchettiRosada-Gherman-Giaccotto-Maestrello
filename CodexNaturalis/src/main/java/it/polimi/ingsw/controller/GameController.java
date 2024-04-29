package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.GameModel;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Deck;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/** Exceptions need to be added. Methods to manage connection and
 * disconnection of a Player need to be added.  */

public class GameController implements Runnable {

    private GameModel model;
    private final Random random = new Random();

    @Override
    public void run() {

    }

    /** Add a player to Game's lobby.*/
    public void addPlayerToLobby(Player p) throws MaxNumPlayersException {

            if (getPlayers().size() + 1 <= model.getSpecificNumOfPlayers()) {

            } else {
                throw new MaxNumPlayersException();
            }

    }

    /** Check if the nickname is unique*/
    public boolean checkUniqueNickname(String name){
        for (Player p : getPlayers()){
            if (name.equals(p.getNickname())){
                return false;
            }
        }
        return true;
    }

    /** Return a list containing all the current players of the game (Online and Offline)*/
    public List <Player> getPlayers() {
        return model.getPlayers();
    }

    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

    /** Reconnect a player to the Game unless the game is already over*/
    public void reconnectPlayer(Player p) throws  GameEndedException {
    }

    private void extractCommonObjectiveCards() {
        while(model.getCommonObjectiveCards().size() < 2 ){
            int cardExtracted = random.nextInt(model.getObjectiveCardsDeck().size()-1);
            ObjectiveCard c = getObjectiveCardsDeck().get(cardExtracted);
            model.addCommonObjectiveCard(c);
            model.removeCardFromDeck(c, getObjectiveCardsDeck());
            }
        }


    private void extractCommonPlaygroundCards() {
        while(model.getCommonPlaygroundGoldCards.size() < 2 ){
            int cardExtracted = random.nextInt(model.getGoldCardsDeck().size()-1);
            GoldCard c = getGoldCardsDeck().get(cardExtracted);
            model.addCommonPlaygroundGoldCard(c);
            model.removeCardFromDeck(c, getGoldCardsDeck());
        }
        while(model.getCommonPlaygroundResourceCards.size() < 2 ){
            int cardExtracted = random.nextInt(model.getResourceCardsDeck().size()-1);
            ResourceCard c = getResourceCardsDeck().get(cardExtracted);
            model.addCommonPlaygroundResourceCard(c);
            model.removeCardFromDeck(c, getResourceCardsDeck());
        }
    }


    private void extractPlayerHandCards(){
        Deck goldCardDeck = model.getGoldCardDeck();
        ArrayList<GoldCard> goldCards = (ArrayList<GoldCard>) goldCardDeck.getCards();
        Deck resourceCardDec=model.getResourceCardDeck();
        ArrayList<ResourceCard> resourceCards= (ArrayList<ResourceCard>) resourceCardDec.getCards();
        for (Player p: getPlayers()){
            GoldCard lastGoldCard=goldCards.getLast();
            p.getCardsInHand().add(lastGoldCard);
            p.getCardsInHand().remove(lastGoldCard);
            for(int i=1; i<=2; i++){
                ResourceCard lastResourceCard=resourceCards.getLast();
                p.getCardsInHand().add(lastResourceCard);
                p.getCardsInHand().remove(lastResourceCard);
            }

        }
    }

    public List<PawnColor> AvailableColors(){

        List<PawnColor> colors = getPlayers().stream().map(Player::getPawnColor).toList();

        List<PawnColor> available = new ArrayList<>();

        for (PawnColor color: PawnColor.values()){
            if(!colors.contains(color)){
                available.add(color);
            }
        }
        return available;
    }

    /** Method to be called by the first Player present in the lobby*/
    private void chooseNumOfPlayers(){

    }

    public synchronized void sentMessage() {
    }

    private void checkObjectivePoints() {

    }


    public void executePlayerTurn() {
    }


    public int getGameId() {
        return model.getGameId();
    }

    public void initializeGame(){

    }

    /** Method that checks for each turn if a player got 20 points*/
    public boolean scoreMaxReached(){
        for(Player p: getPlayers()){
            if(p.getScore() >= 20){
              return true;
            }
        }
        return false;
    }

    public void FinalizeGame(){

    }

    public String decreeWinner(){

        List<Player> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for(Player p : getPlayers()){
            if(p.getScore() > score){
                score = p.getScore();
                winners.clear();
                winners.add(p);
            }else if(p.getScore() == score){
                winners.add(p);
            }
        }

        if (winners.size() == 1) {
            return "The winners is" + winners.get(0).getNickname();
        } else {
            return "Tie between the following players: " + winners.stream().map(Player::getNickname).collect(Collectors.joining(", "));
        }

    }

    /**
     * Return the GameStatus of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

    public void setStatus(GameStatus status) {
        model.setStatus(status);
    }

}



















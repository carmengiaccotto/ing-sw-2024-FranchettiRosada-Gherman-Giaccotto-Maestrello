package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.GameEndedException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.GameModel;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;

import java.rmi.RemoteException;
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
                getPlayers().add(p);
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
    public void reconnectPlayer(Player p) {

    }

    public void extractCommonObjectiveCards() {
        while(model.getCommonObjectivesCards().size() < 2 ){
            int cardExtracted = random.nextInt(model.getObjectiveCardDeck().getSize()-1);
            ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getObjectiveCardDeck());
            }
        }


    public void extractCommonPlaygroundCards() {
        while(model.getCommonGoldCards().size() < 2 ){
            int cardExtracted = random.nextInt(model.getGoldCardDeck().getSize()-1);
            GoldCard c = (GoldCard) model.getGoldCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getGoldCardDeck());
        }
        while(model.getCommonResourceCards().size() < 2 ){
            int cardExtracted = random.nextInt(model.getResourceCardDeck().getSize()-1);
            ResourceCard c = (ResourceCard) model.getResourceCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getResourceCardDeck());
        }
    }


    /**private void extractPlayerHandCards(){
        for (Player p: getPlayers()){
            p.DrawCardFrom(model.getGoldCardDeck().getCards(), model.getGoldCardDeck().getLastCard());
            p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
            p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
        }
    } */

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


    public synchronized void sentMessage(Message m) throws RemoteException {
        model.sentMessage(m);
    }

    public void addPersonalObjectiveCardPoints() {
        for(Player p: getPlayers()){
            if(p.getPersonalObjectiveCard() instanceof SymbolObjectiveCard){
                SymbolObjectiveCard c = (SymbolObjectiveCard) p.getPersonalObjectiveCard();
                int numOfGoals = c.CheckGoals(p.getPlayArea().getSymbols());
                int points = c.calculatePoints(numOfGoals);
                p.increaseScore(points);
            }else{
                DispositionObjectiveCard c = (DispositionObjectiveCard) p.getPersonalObjectiveCard();
                int numOfGoals = c.CheckGoals(p.getPlayArea());
                int points = c.calculatePoints(numOfGoals);
                p.increaseScore(points);

            }
        }
    }

    public void addCommonObjectiveCardsPoints() {
        for(Player p: getPlayers()){
            for(ObjectiveCard card: model.getCommonObjectivesCards()){
                if(card instanceof SymbolObjectiveCard){
                    SymbolObjectiveCard c = (SymbolObjectiveCard) card;
                    int numOfGoals = c.CheckGoals(p.getPlayArea().getSymbols());
                    int points = c.calculatePoints(numOfGoals);
                    p.increaseScore(points);
                }else{
                    DispositionObjectiveCard c = (DispositionObjectiveCard) card;
                    int numOfGoals = c.CheckGoals(p.getPlayArea());
                    int points = c.calculatePoints(numOfGoals);
                    p.increaseScore(points);
                }
            }
        }

    }


    public int getGameId() {
        return model.getGameId();
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

    public void initializeGame(){
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
        /** extractPlayerHandCards(); */
        setStatus(GameStatus.SET_UP);
    }

    public void FinalizeGame(){
        addPersonalObjectiveCardPoints();
        addCommonObjectiveCardsPoints();
        decreeWinner();
        setStatus(GameStatus.ENDED);
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

    public void setStatus(GameStatus status) throws NotReadyToRunException {
        model.setStatus(status);
    }

    /**
     * Calls the next turn
     *
     * @return
     * @throws GameEndedException
     */
    public void nextTurn() throws GameEndedException {

    }

}



















package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Client;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayGround;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/** Exceptions need to be added. Methods to manage connection and
 * disconnection of a Player need to be added.  */

public class GameController implements Runnable {
    private ArrayList<Client> clients;

    private PlayGround model;

    private static GameController instance = null;
    private final Random random = new Random();

    public GameController(ArrayList<Client> clients, PlayGround model) {
        this.clients = clients;
        this.model = model;
    }

    @Override
    public void run() {

    }

    /** Reconnect a player to the Game unless the game is already over*/
    public void reconnectPlayer(Player p) {
    }

    public ArrayList<Client> getClients(){
        return clients;
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


   /** private void extractPlayerHandCards(){
        for (Client c: getClients()){
            c.getPlayer().DrawCardFrom(model.getGoldCardDeck().getCards(), model.getGoldCardDeck().getLastCard());
            p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
            p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
        }
    } */

    public List<PawnColor> AvailableColors(){

        List<PawnColor> colors = clients.stream()
                .map(client -> client.getPlayer().getPawnColor())
                .toList();

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
        for(Client c: getClients()){
            if(c.getPlayer().getPersonalObjectiveCard() instanceof SymbolObjectiveCard){
                SymbolObjectiveCard card = (SymbolObjectiveCard) c.getPlayer().getPersonalObjectiveCard();
                int numOfGoals = card.CheckGoals(c.getPlayer().getPlayArea().getSymbols());
                int points = card.calculatePoints(numOfGoals);
                c.getPlayer().increaseScore(points);
            }else{
                DispositionObjectiveCard card = (DispositionObjectiveCard) c.getPlayer().getPersonalObjectiveCard();
                int numOfGoals = card.CheckGoals(c.getPlayer().getPlayArea());
                int points = card.calculatePoints(numOfGoals);
                c.getPlayer().increaseScore(points);

            }
        }
    }

    public void addCommonObjectiveCardsPoints() {
        for(Client c: getClients()){
            for(ObjectiveCard card: model.getCommonObjectivesCards()){
                if(card instanceof SymbolObjectiveCard){
                    SymbolObjectiveCard s = (SymbolObjectiveCard) card;
                    int numOfGoals = s.CheckGoals(c.getPlayer().getPlayArea().getSymbols());
                    int points = s.calculatePoints(numOfGoals);
                    c.getPlayer().increaseScore(points);
                }else{
                    DispositionObjectiveCard s = (DispositionObjectiveCard) card;
                    int numOfGoals = s.CheckGoals(c.getPlayer().getPlayArea());
                    int points = s.calculatePoints(numOfGoals);
                    c.getPlayer().increaseScore(points);
                }
            }
        }

    }


    public int getGameId() {
        return model.getGameId();
    }


    /** Method that checks for each turn if a player got 20 points*/
    public boolean scoreMaxReached(){
        for(Client c: getClients()){
            if(c.getPlayer().getScore() >= 20){
              return true;
            }
        }
        return false;
    }

    public void initializeGame() throws NotReadyToRunException {
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
        /** extractPlayerHandCards(); */
    }

    public void FinalizeGame() throws NotReadyToRunException {
        addPersonalObjectiveCardPoints();
        addCommonObjectiveCardsPoints();
        decreeWinner();
    }

    public String decreeWinner(){

        List<Client> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for(Client c : getClients()){
            if(c.getPlayer().getScore() > score){
                score = c.getPlayer().getScore();
                winners.clear();
                winners.add(c);
            }else if(c.getPlayer().getScore() == score){
                winners.add(c);
            }
        }

        if (winners.size() == 1) {
            return "The winners is" + winners.get(0).getPlayer().getNickname();
        } else {
            return "Tie between the following players: " + winners.stream()
                    .map(client -> client.getPlayer().getNickname())
                    .collect(Collectors.joining(", "));
        }

    }

    public List<ObjectiveCard> drawObjectiveCardForPlayer(){
        List<ObjectiveCard> listCard = new ArrayList<ObjectiveCard>();
        while(listCard.size() < 2 ) {
            int cardExtracted = random.nextInt(model.getObjectiveCardDeck().getSize() - 1);
            ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().getCards().get(cardExtracted);
            listCard.add(c);
            model.removeCardFromDeck(c, model.getObjectiveCardDeck());
        }
        return listCard;
    }

    public PlayGround getModel() {
        return model;
    }

}



















package it.polimi.ingsw.controller;

import it.polimi.ingsw.Connection.Client;
import it.polimi.ingsw.View.UserInterface;
import it.polimi.ingsw.model.Cards.*;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.model.Enumerations.CornerPosition;
import it.polimi.ingsw.model.Enumerations.PawnColor;
import it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.PlayGround.PlayArea;
import it.polimi.ingsw.model.PlayGround.PlayGround;
import it.polimi.ingsw.model.PlayGround.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/** Exceptions need to be added. Methods to manage connection and
 * disconnection of a Player need to be added.  */

public class GameController implements Runnable {
    private ArrayList<Client> clients = null;

    private static UserInterface view;
    private PlayGround model;
    private static GameController instance = null;
    private final Random random = new Random();

    @Override
    public void run() {
        int pos = 0;
        System.out.println("The game has started");
        try {
            initializeGame();
            System.out.println("This is the initial board of the game:");
            view.showBoardAndPlayAreas();
        } catch (NotReadyToRunException e) {
            throw new RuntimeException(e);
        }
        for (Client c : getClients()) {
            c.getView().choosePersonalObjectiveCard(c.getPlayer().getNickname());
            InitialCard card = extractInitialCard();
            c.getView().playInitialCard(card, c.getPlayer().getNickname());
        }

        while (!scoreMaxReached()) {
            for (Client c : getClients()) {
                System.out.println("It's your turn!");
                c.getView().playCard(c.getPlayer().getNickname());
                c.getView().drawCard(c.getPlayer().getNickname());
                pos = getClients().indexOf(c);
            }
        }

        if (pos < (clients.size() - 1)){
            for (int i = pos + 1; i < clients.size(); i++) {
                System.out.println("It's your last turn!");
                getClients().get(i).getView().playCard(getClients().get(i).getPlayer().getNickname());
                getClients().get(i).getView().drawCard(getClients().get(i).getPlayer().getNickname());
            }
        }
        try {
            FinalizeGame();
        } catch (NotReadyToRunException e) {
            throw new RuntimeException(e);
        }

    }

    public GameController(ArrayList<Client> clients, PlayGround model) {
        this.clients = clients;
        this.model = model;
    }

    /**
     * Reconnect a player to the Game unless the game is already over
     */
    public void reconnectPlayer(Player p) {
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void extractCommonObjectiveCards() {
        while (model.getCommonObjectivesCards().size() < 2) {
            int cardExtracted = random.nextInt(model.getObjectiveCardDeck().getSize() - 1);
            ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getObjectiveCardDeck());
        }
    }


    public void extractCommonPlaygroundCards() {
        while (model.getCommonGoldCards().size() < 2) {
            int cardExtracted = random.nextInt(model.getGoldCardDeck().getSize() - 1);
            GoldCard c = (GoldCard) model.getGoldCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getGoldCardDeck());
        }
        while (model.getCommonResourceCards().size() < 2) {
            int cardExtracted = random.nextInt(model.getResourceCardDeck().getSize() - 1);
            ResourceCard c = (ResourceCard) model.getResourceCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getResourceCardDeck());
        }
    }

    public InitialCard extractInitialCard(){
        int cardExtracted = random.nextInt(model.getInitialCardDeck().getSize() - 1);
        InitialCard c = (InitialCard) model.getInitialCardDeck().getCards().get(cardExtracted);
        model.removeCardFromDeck(c, model.getInitialCardDeck());

        return c;

    }


    /**
     * private void extractPlayerHandCards(){
     * for (Client c: getClients()){
     * c.getPlayer().DrawCardFrom(model.getGoldCardDeck().getCards(), model.getGoldCardDeck().getLastCard());
     * p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
     * p.DrawCardFrom(model.getResourceCardDeck().getCards(), model.getResourceCardDeck().getLastCard());
     * }
     * }
     */

    public List<PawnColor> AvailableColors() {

        List<PawnColor> colors = clients.stream()
                .map(client -> client.getPlayer().getPawnColor())
                .toList();

        List<PawnColor> available = new ArrayList<>();

        for (PawnColor color : PawnColor.values()) {
            if (!colors.contains(color)) {
                available.add(color);
            }
        }
        return available;
    }


    public synchronized void sentMessage(Message m) throws RemoteException {
        model.sentMessage(m);
    }

    public void addPersonalObjectiveCardPoints() {
        for (Client c : getClients()) {
            if (c.getPlayer().getPersonalObjectiveCard() instanceof SymbolObjectiveCard) {
                SymbolObjectiveCard card = (SymbolObjectiveCard) c.getPlayer().getPersonalObjectiveCard();
                int numOfGoals = card.CheckGoals(c.getPlayer().getPlayArea().getSymbols());
                int points = card.calculatePoints(numOfGoals);
                c.getPlayer().increaseScore(points);
            } else {
                DispositionObjectiveCard card = (DispositionObjectiveCard) c.getPlayer().getPersonalObjectiveCard();
                int numOfGoals = card.CheckGoals(c.getPlayer().getPlayArea());
                int points = card.calculatePoints(numOfGoals);
                c.getPlayer().increaseScore(points);

            }
        }
    }

    public void addCommonObjectiveCardsPoints() {
        for (Client c : getClients()) {
            for (ObjectiveCard card : model.getCommonObjectivesCards()) {
                if (card instanceof SymbolObjectiveCard) {
                    SymbolObjectiveCard s = (SymbolObjectiveCard) card;
                    int numOfGoals = s.CheckGoals(c.getPlayer().getPlayArea().getSymbols());
                    int points = s.calculatePoints(numOfGoals);
                    c.getPlayer().increaseScore(points);
                } else {
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


    /**
     * Method that checks for each turn if a player got 20 points
     */
    public boolean scoreMaxReached() {
        for (Client c : getClients()) {
            if (c.getPlayer().getScore() >= 20) {
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

    public String decreeWinner() {

        List<Client> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for (Client c : getClients()) {
            if (c.getPlayer().getScore() > score) {
                score = c.getPlayer().getScore();
                winners.clear();
                winners.add(c);
            } else if (c.getPlayer().getScore() == score) {
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

    public List<ObjectiveCard> drawObjectiveCardForPlayer() {
        List<ObjectiveCard> listCard = new ArrayList<ObjectiveCard>();
        while (listCard.size() < 2) {
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

    public void addInHands(String nickname, Card card) {

    }

    public ObjectiveCard getPersonalObjective(String nickname) {
        for (Client client : clients) {
            if (client.getPlayer().getNickname().equals(nickname)) {
                return client.getPlayer().getPersonalObjectiveCard();
            }
        }
        return null;
    }


    public ArrayList<PlayCard> getCardInHandPlayer(String nickname){
        for(Client client : clients){
            if(client.getPlayer().getNickname().equals(nickname)){
                return client.getPlayer().getCardsInHand();
            }
        }
        return null;
    }

    public void removeCardInHand(Card card, String nickname){
        Player p = getPlayer(nickname);
        p.getCardsInHand().remove(card);
    }

    public void getChat(){

    }
   // public Card getInitialcard(){

    //}


    //Name suggestions for this method?
    /**Method that Checks if the Player is trying to cover two corners of the same Card while placing a card
     * @return  true id the position is valid, false otherWise
     * @param playArea of the current player
     * @param column1 chosen column where to place the card
     * @param row1 chosen row where to place the card*/
    public boolean ValidTwoCornersSameCard(PlayArea playArea, int row1, int column1){
        //Graphic playArea is shown with one more column and one more row at the beginning and at the end
        //compared to the actual PlayArea, to give the Player the opportunity to choose the position,
        //rather than the card and the corner
        int r = row1 - 1;
        int c = column1 - 1;
        for (int i=r-1; i<=r+1; i++){
            for (int j=c-1; j<=c+1; c++){
                if(i==r || j==c){
                    if (playArea.getCardInPosition(i,j)!=null) // if we have cards in the adjacent positions
                        //we are trying to cover two corners of the same card
                        return false;
                }
            }
        }
        return true;
    }

//Method still to revise and test
    /**Method that allows the Player to choose the position where to place the card in a more UserFriendly way*/
    public boolean ValidPositionCardOnArea(PlayArea playArea, int row1, int column1, SideOfCard newCard) {
        //Graphic playArea is shown with one more column and one more row at the beginning and at the end
        //compared to the actual PlayArea, to give the Player the opportunity to choose the position,
        //rather than the card and the corner
        int r = row1 - 1;
        int c = column1 - 1;

        //Check if the position on the playArea is Already occupied
        if (playArea.getCardInPosition(r, c) != null)
            return false;

        //Finds a Card to cover for the addCardOnArea method in PlayArea.
        //This way checks if the player is trying to place a card without attaching it to another one
        SideOfCard neighbourCard=new SideOfCard(null, null);
        Corner cornerToCover=null;
        for (Corner[] Rowcorner : newCard.getCorners()) {
            for (Corner corner : Rowcorner) {
                Pair<Integer, Integer> newPosition = corner.getPosition().PositionNewCard(newCard);
                if (newPosition != null) {
                    int rowToCheck = newPosition.getFirst();
                    int columnToCheck = newPosition.getSecond();
                    if (playArea.rowExists(rowToCheck) && playArea.columnExists(columnToCheck)) {
                        List<SideOfCard> row = playArea.getCardsOnArea().get(rowToCheck);
                        if (row != null) {
                            neighbourCard = row.get(columnToCheck);
                            if (neighbourCard != null){
                                cornerToCover=neighbourCard.getCornerInPosition(corner.getPosition().CoverCorners());
                                if (!cornerToCover.isHidden())
                                    break;
                                else
                                    return false;// We are trying to cover a hidden Corner, so the position Is Invalid


                            }


                        }
                    }
                }
            }
        }
        if (neighbourCard == null)
            return false;
        //Add Checks
        playArea.AddCardOnArea(newCard,cornerToCover,neighbourCard);
        return true;
    }

    /**Method that checks if the Player is trying to cover any hidden corners*/
    public boolean notTryingToCoverHiddenCorners(PlayArea playArea, int row1, int column1, SideOfCard newCard){
        for (Corner[] Rowcorner : newCard.getCorners()) {
            for (Corner corner : Rowcorner) {
                Pair<Integer, Integer> newPosition = corner.getPosition().PositionNewCard(newCard);
                if (newPosition != null) {
                    int rowToCheck = newPosition.getFirst();
                    int columnToCheck = newPosition.getSecond();
                    if (playArea.rowExists(rowToCheck) && playArea.columnExists(columnToCheck)) {
                        List<SideOfCard> row = playArea.getCardsOnArea().get(rowToCheck);
                        if (row != null) {
                            SideOfCard neighbourCard = row.get(columnToCheck);
                            if (neighbourCard != null) {
                                CornerPosition cornerToCover = corner.getPosition().CoverCorners();
                                if (neighbourCard.getCornerInPosition(cornerToCover).isHidden())
                                    return false;

                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public Player getPlayer(String nickname){
        for(Client client: clients){
            if(client.getPlayer().getNickname().equals(nickname)){
                return client.getPlayer();

            }
        }
        return null;
    }


}






















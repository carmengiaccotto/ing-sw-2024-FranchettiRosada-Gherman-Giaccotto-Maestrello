package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Connection.VirtualClient;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameController implements  Runnable, Serializable {

    private List<VirtualClient> players = new ArrayList<>();
    private GameStatus status;
    private final int numPlayers;
    private final int id;

    private static UserInterface view;

    private final Random random = new Random();

    private final PlayGround model;

    public GameController(int id, int n) {
        this.status = GameStatus.WAITING;
        this.numPlayers=n;
        this.id=id;
        try {
            model = new PlayGround(this.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
//        while(!status.equals(GameStatus.ENDED)){
//            performAction(GameStatus currentStatus);
//        }
//
//        while(status.equals(GameStatus.RUNNING)){
//            receiveMessage(Command c);
//        }
//
//
//
    }


    public synchronized List<VirtualClient> getPlayers() {
        return players;
    }

    public int getNumPlayers() {

        return numPlayers;
    }
    public synchronized void addPlayer(VirtualClient client){
        players.add(client);
    }


    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public void CheckMaxNumPlayerReached(){
        if(players.size()==numPlayers){
            setStatus(GameStatus.SETUP);
            run();
        }
        else{
            for(VirtualClient client: players){
                try {
                    client.Wait();
                } catch (RemoteException e) {
                    System.out.println("Problem connecting to view");
                }
            }

        }

    }


    public List<PawnColor> AvailableColors(List<VirtualClient> clients) {

        List<PawnColor> colors = clients.stream()
                .map(client -> {
                    try {
                        return client.getPlayer().getPawnColor();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        List<PawnColor> available = new ArrayList<>();

        for (PawnColor color : PawnColor.values()) {
            if (!colors.contains(color)) {
                available.add(color);
            }
        }
        return available;
    }

    public void setPlayers(List<VirtualClient> players){
        this.players=players;

    }

    public synchronized void NotifyNewPlayerJoined(VirtualClient newPlayer) {
        for (VirtualClient player : players) {
            try {
                player.updatePlayers(players);
            } catch (RemoteException e) {
                System.out.println("An error Occurred dutìring Players update: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            newPlayer.setPawnColor((ArrayList<PawnColor>) AvailableColors(players));
        } catch (RemoteException e) {
            System.out.println("An error occured");
        }
        //TODO
        System.out.println("reached this point");
        CheckMaxNumPlayerReached();
    }

    public int getId() {
        return id;
    }

    public void PlayGroundSetUp(){

    }

    void receiveMessage(Command c){
        switch (c){
            case CHAT ->{
                //broadcast chat
            }
            case MOVE ->{
                // check se è current player
                //aggiungi carta se si
                //digli fatti i fatti tuoi se no
            }
        }
    }

    void performAction(GameStatus status){

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

    public void addPersonalObjectiveCardPoints() throws RemoteException {
        for (VirtualClient c : getPlayers()) {
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

    public void addCommonObjectiveCardsPoints() throws RemoteException {
        for (VirtualClient c : getPlayers()) {
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

    public boolean scoreMaxReached() throws RemoteException {
        for (VirtualClient c : getPlayers()) {
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

    public void FinalizeGame() throws NotReadyToRunException, RemoteException {
        addPersonalObjectiveCardPoints();
        addCommonObjectiveCardsPoints();
        decreeWinner();
    }

    public String decreeWinner() throws RemoteException {

        List<VirtualClient> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for (VirtualClient c : getPlayers()) {
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
                    .map(client -> {
                        try {
                            return client.getPlayer().getNickname();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    })
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

    public ObjectiveCard getPersonalObjective(String nickname) throws RemoteException {
        for (VirtualClient client : players) {
            if (client.getPlayer().getNickname().equals(nickname)) {
                return client.getPlayer().getPersonalObjectiveCard();
            }
        }
        return null;
    }

    public ArrayList<PlayCard> getCardInHandPlayer(String nickname) throws RemoteException {
        for(VirtualClient client : players){
            if(client.getPlayer().getNickname().equals(nickname)){
                client.getPlayer().getCardsInHand();
            }
        }
        return null;
    }

    /**Method that adds the initialCard to the playArea of the client
     * @param client the client that is currently Playing
     * @param initialCard side of the initialCard that the Player chose to Play */
    public void  playInitialCard(VirtualClient client, SideOfCard initialCard){
        try {
            for(Player p: model.getPlayers()){
                if(p.getNickname().equals(client.getPlayer().getNickname())){
                    p.getPlayArea().addInitialCardToArea(initialCard);
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

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


}

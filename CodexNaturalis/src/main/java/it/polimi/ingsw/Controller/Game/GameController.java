package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.*;
import it.polimi.ingsw.Model.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameController implements  Runnable, Serializable, GameControllerInterface {

    private List<ClientControllerInterface> players = new ArrayList<>();
    private GameStatus status;
    private final int numPlayers;
    private final int id;


    private final Random random = new Random();

    private PlayGround model;

    private final ReentrantLock turnLock = new ReentrantLock();

    private int numOfLastPlayer = 0;

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
        while(!status.equals(GameStatus.ENDED)){
            try {
                performAction(status);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FinalizeGame();
        } catch (RemoteException | NotReadyToRunException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized List<ClientControllerInterface> getPlayers() {
        return players;
    }

    public int getNumPlayers() {

        return numPlayers;
    }
    public synchronized void addPlayer(ClientControllerInterface client){
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
            for(ClientControllerInterface client: players){
                try {
                    client.Wait();
                } catch (RemoteException e) {
                    System.out.println("Problem connecting to view");
                }
            }

        }

    }


    public List<PawnColor> AvailableColors(List<ClientControllerInterface> clients) {

        List<PawnColor> colors = clients.stream()
                .map(client -> {
                    try {
                        return client.getPawnColor();
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

    public void setPlayers(List<ClientControllerInterface> players){
        this.players=players;

    }

    public synchronized void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) {
        for (ClientControllerInterface player : players) {
            try {
                player.updatePlayers(players);
            } catch (RemoteException e) {
                System.out.println("An error Occurred dutìring Players update: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            newPlayer.ChoosePawnColor((ArrayList<PawnColor>) AvailableColors(players));
        } catch (RemoteException e) {
            System.out.println("An error occured");
        }
        CheckMaxNumPlayerReached();
    }

    public int getId() {
        return id;
    }


    public void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException{
        switch (c){
            case CHAT ->{
                //broadcast chat
            }
            case MOVE -> {
                turnLock.lock();
                try {
       //             client.getView().playCard(client.getNickname()); //TODO change with a method that invokes a method in the ClientController
                    System.out.println(client.getNickname() + "has played a card");
                    for (ClientControllerInterface clients : getPlayers()) {
                        clients.showBoardAndPlayAreas(getModel());
                    }
                    PlayCard card = client.chooseCardToDraw(model);
                    client.getPlayer().addCardToHand(card);
                    updatePlayground(card);
                    System.out.println("This is the current Playground: ");
                    for (ClientControllerInterface clients : getPlayers()) {
                        clients.showBoardAndPlayAreas(getModel());
                    }

                } finally {
                    turnLock.unlock();
                }
            }
        }
    }

    void performAction(GameStatus status) throws RemoteException {
        switch(status) {
            case SETUP -> {
                try {
                    initializeGame();
                    for (ClientControllerInterface c : getPlayers()) {
                        c.printInitialMessage();
                        c.showBoardAndPlayAreas(getModel());
                    }
                    setStatus(GameStatus.INITIAL_CIRCLE);
                } catch (NotReadyToRunException e) {
                    throw new RuntimeException(e);
                }

            }
            case INITIAL_CIRCLE -> {
                for (ClientControllerInterface c : getPlayers()) {
                    ArrayList<ObjectiveCard> objectiveList = drawObjectiveCardForPlayer();
                    int indexOfCard = c.choosePersonaObjectiveCard(objectiveList);
                    c.setPersonalObjectiveCard(objectiveList.get(indexOfCard));
                    for (ClientControllerInterface client : getPlayers()) {
                        client.printObjectiveCardMessage(c.getNickname());
                    }
                    InitialCard card = extractInitialCard();
                    String side = c.chooseSideInitialCard(card);
                    playInitialCard(c, card.chooseSide(Side.valueOf(side)));
                    for (ClientControllerInterface clients : getPlayers()) {
                        clients.printInitialCardMessage(c.getNickname());
                        clients.showBoardAndPlayAreas(getModel());
                    }
                }
                setStatus(GameStatus.RUNNING);

            }
            case RUNNING -> {
//                for (ClientControllerInterface c : getPlayers()) {
//                    Command command = null;
//                    c.receiveCommand();
//                }
//
//
//                    while (!scoreMaxReached()) {
//                        for (ClientControllerInterface c : getPlayers()) {
//                            model.setCurrentPlayer(c.getNickname());
//                            c.printTurnMessage(c.getNickname());
//                            numOfLastPlayer = getPlayers().indexOf(c);
//                            Command command = c.receiveCommand();
//                            if (command == Command.MOVE && c.getNickname().equals(model.getCurrentPlayer())) {
//                                receiveMessage(command, c);
//                            } else {
//                                System.out.println("Please wait for your turn!");
//                            }
//                        }
//                    }
//                    setStatus(GameStatus.LAST_CIRCLE);


            }
            case LAST_CIRCLE -> {
//                if (numOfLastPlayer < (getPlayers().size() - 1)) {
//                    System.out.println("We are at the last round of the Game, " + getPlayers().get(numOfLastPlayer).getNickname() + "has reached the maximum score!");
//                    for (int i = numOfLastPlayer + 1; i < getPlayers().size(); i++) {
//                        model.setCurrentPlayer(getPlayers().get(i).getNickname());
//                        System.out.println("It's your last turn, " + getPlayers().get(i).getNickname() + "!");
//                        Command command = getPlayers().get(i).receiveCommand();
//                        receiveMessage(command, getPlayers().get(i));
//                    }
//
//                }
                setStatus(GameStatus.ENDED);
            }
        }
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
        for (ClientControllerInterface c : getPlayers()) {
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
        for (ClientControllerInterface c : getPlayers()) {
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
        for (ClientControllerInterface c : getPlayers()) {
            if (c.getPlayer().getScore() >= 20) {
                return true;
            }
        }
        return false;
    }

    public void initializeGame() throws NotReadyToRunException, RemoteException {
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
        extractPlayerHandCards();
    }

    public void FinalizeGame() throws NotReadyToRunException, RemoteException {
        addPersonalObjectiveCardPoints();
        addCommonObjectiveCardsPoints();
        for (ClientControllerInterface client : players) {
            client.printFinalScoresMessage(players);
        }
        decreeWinner();
        for (ClientControllerInterface client : players) {
            client.printFinalMessage();
            client.disconnect();
        }
    }

    public void decreeWinner() throws RemoteException {

        List<ClientControllerInterface> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for (ClientControllerInterface c : getPlayers()) {
            if (c.getPlayer().getScore() > score) {
                score = c.getPlayer().getScore();
                winners.clear();
                winners.add(c);
            } else if (c.getPlayer().getScore() == score) {
                winners.add(c);
            }
        }

        if (winners.size() == 1) {
            for (ClientControllerInterface client : players) {
                client.printWinnerMessage(winners);
            }
        } else {
            for (ClientControllerInterface client : players) {
                client.printWinnersMessage(winners);
            }
        }

    }

    public ArrayList<ObjectiveCard> drawObjectiveCardForPlayer() {
        ArrayList<ObjectiveCard> listCard = new ArrayList<ObjectiveCard>();
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

    public void setModel(PlayGround model) {
        this.model = model;
    }

    public void updatePlayground(PlayCard c){
        boolean ok = false;
        if(c instanceof GoldCard){
            for(int i = 0; i<model.getCommonGoldCards().size(); i++){
                if(model.getCommonGoldCards().get(i).equals(c)){
                    GoldCard card = (GoldCard) model.getGoldCardDeck().getCards().getFirst();
                    model.removeCardFromDeck(card, model.getGoldCardDeck());
                    model.getCommonGoldCards().set(i, card);
                    ok = true;
                }
            }
            if(!ok){
                model.removeCardFromDeck(c, model.getGoldCardDeck());
            }
        }
        else{
            for(int i = 0; i<model.getCommonResourceCards().size(); i++){
                if(model.getCommonResourceCards().get(i).equals(c)){
                   ResourceCard card = (ResourceCard) model.getResourceCardDeck().getCards().getFirst();
                   model.removeCardFromDeck(card, model.getResourceCardDeck());
                   model.getCommonResourceCards().set(i, card);
                   ok = true;
                }
            }
            if(!ok){
                model.removeCardFromDeck(c, model.getResourceCardDeck());
            }
        }
    }

    public void extractPlayerHandCards() throws RemoteException {
        for (ClientControllerInterface client : players) {
            while (client.getPlayer().getCardsInHand().size() < 2) {
                int cardExtracted = random.nextInt(model.getResourceCardDeck().getSize() - 1);
                ResourceCard c = (ResourceCard) model.getResourceCardDeck().getCards().get(cardExtracted);
                client.getPlayer().addCardToHand(c);
                model.removeCardFromDeck(c, model.getResourceCardDeck());
            }
            int cardExtracted = random.nextInt(model.getGoldCardDeck().getSize() - 1);
            GoldCard c = (GoldCard) model.getGoldCardDeck().getCards().get(cardExtracted);
            client.getPlayer().addCardToHand(c);
            model.removeCardFromDeck(c, model.getGoldCardDeck());
        }

    }

    public ObjectiveCard getPersonalObjective(String nickname) throws RemoteException {
        for (ClientControllerInterface client : players) {
            if (client.getNickname().equals(nickname)) {
                return client.getPlayer().getPersonalObjectiveCard();
            }
        }
        return null;
    }

    /**Method that adds the initialCard to the playArea of the client
     * @param client the client that is currently Playing
     * @param initialCard side of the initialCard that the Player chose to Play */
    public void  playInitialCard(ClientControllerInterface client, SideOfCard initialCard) throws RemoteException {
        for(Player p: model.getPlayers()){
            if(p.getNickname().equals(client.getNickname())){
                p.getPlayArea().addInitialCardToArea(initialCard);
            }
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

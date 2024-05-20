package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


public class GameController extends UnicastRemoteObject implements  Runnable, Serializable, GameControllerInterface {
    private GameListener listener = new GameListener();
    private GameStatus status;
    private final int numPlayers;
    private final int id;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> currentTimer;


    private final Random random = new Random();

    private PlayGround model;

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    private final ReentrantLock turnLock = new ReentrantLock();

    private int numOfLastPlayer = 0;

    /**
     * Creates a new GameController with the specified number of players and game ID.
     *
     * @param numPlayers The number of players in the game.
     * @param id The game ID.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public GameController(int numPlayers, int id) throws RemoteException {
        this.numPlayers = numPlayers;
        this.id = id;
        this.status = GameStatus.WAITING;
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

    /**Method that returns the number of players in the game
     * @return the number of players in the game*/
    public int getNumPlayers() throws RemoteException {

        return numPlayers;
    }

    /**Method that returns the GameListener of the game
     * @return the GameListener of the game*/
    public GameListener getListener() throws RemoteException {
        return listener;
    }

    /**Method that adds a player to the game
     * @param client the client that is added to the game*/
    public synchronized void addPlayer(ClientControllerInterface client) throws RemoteException{
        listener.getPlayers().add(client);
        System.out.println(client.getNickname());
    }


    /**
     * Gets the current game status.
     *
     * @return The current game status.
     */
    public GameStatus getStatus() throws RemoteException {
        return status;
    }

    /**
     * Sets the current game status.
     *
     * @param status The new game status.
     */
    public void setStatus(GameStatus status) throws RemoteException {
        this.status = status;
    }


    /**
     * Checks if the maximum number of players has been reached.
     * If the number of players in the game is equal to the maximum number of players, the game is ready to run.
     * If the maximum number of players has not been reached, the game waits for more players to join.
     */
    public void CheckMaxNumPlayerReached() throws RemoteException {
        if(listener.getPlayers().size()==getNumPlayers()){
            setStatus(GameStatus.SETUP);
            if (executor.isShutdown()) {
                executor.execute(this);
            }
        }
        else{
            for(ClientControllerInterface client: listener.getPlayers()){
                try {
                    client.Wait();
                } catch (RemoteException e) {
                    System.out.println("Problem connecting to view");
                }
            }

        }

    }

    /**
     * Returns a list of available pawn colors for a player to choose from.
     * The method iterates through the list of players in the game and extracts the pawn color of each player.
     * It then checks the available colors and returns a list of colors that are not already taken by other players.
     *
     * @param clients The list of clients in the game.
     * @return A list of available pawn colors for a new player to choose from.
     */
    public List<PawnColor> AvailableColors(List<ClientControllerInterface> clients) throws RemoteException {

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

    /**
     * Notifies all players that a new player has joined the game.
     * The method sends an update message to all players with the list of players in the game.
     * It then prompts the new player to choose a pawn color from the available colors.
     * If the maximum number of players has been reached, the method checks if the game is ready to run.
     */
    public synchronized void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {
        try {
            getListener().updatePlayers(newPlayer);
        } catch (RemoteException e) {
            System.out.println("An error Occurred during Players update: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            newPlayer.ChoosePawnColor((ArrayList<PawnColor>) AvailableColors(listener.getPlayers()));
        } catch (RemoteException e) {
            System.out.println("An error occured");
        }
        CheckMaxNumPlayerReached();
    }

    public int getId() throws RemoteException {
        return id;
    }


    /**
     * Receives a message from a client and performs actions based on the command received.
     * The method checks the command received and performs the following actions:
     * CHAT - Broadcasts a chat message to all players.
     * MOVE - Checks if it is the player's turn. If it is, the player is prompted to play a card.
     * If the player does not respond within 2 minutes, the turn is passed to the next player.
     *
     * @param c The command received from the client.
     * @param client The client interface of the player who sent the command.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException{
        switch (c){
            case CHAT ->{
                //broadcast chat
            }
            case MOVE -> {
                if (!client.getNickname().equals(model.getCurrentPlayer())) {
                    client.sendUpdateMessage("It's not your turn, please wait!");
                } else {
                    if (currentTimer != null && !currentTimer.isDone()) {
                        currentTimer.cancel(true);
                    }
                    turnLock.lock();
                    try {
                        PlayCard handCard = client.chooseCardToPlay(model);
                        listener.updatePlayers(client.getNickname() + "has played a card");
                        listener.updatePlayers(getModel());
                        PlayCard card = client.chooseCardToDraw(model);
                        client.getPlayer().addCardToHand(card);
                        updatePlayground(card);
                        listener.updatePlayers("This is the current Playground: ");
                        listener.updatePlayers(getModel());
                        numOfLastPlayer = (numOfLastPlayer + 1) % listener.getPlayers().size();
                        model.setCurrentPlayer(listener.getPlayers().get(numOfLastPlayer).getNickname());
                        listener.updatePlayers("It's " + listener.getPlayers().get(numOfLastPlayer).getNickname() + "'s turn!");
                        startTurnTimer(listener.getPlayers().get(numOfLastPlayer));
                    } finally {
                        turnLock.unlock();
                    }
                }
            }
        }
    }

    /**
     * This method performs actions based on the current game status.
     *
     * @param status The current status of the game. It can be one of the following:
     *               WAITING - The game is waiting for players to join.
     *               SETUP - The game is being initialized.
     *               INITIAL_CIRCLE - Players are choosing their personal objective cards and playing their initial cards.
     *               RUNNING - The game is in progress and players are taking turns to play.
     *               LAST_CIRCLE - The game is in its final round.
     *               ENDED - The game has ended.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void performAction(GameStatus status) throws RemoteException {
        switch(status) {
            case SETUP -> {
                try {
                    initializeGame();
                    System.out.println("The game arrived here"); //TODO
                    listener.updatePlayers("This is the initial board of the game: \n");
                    listener.updatePlayers(getModel());
                    setStatus(GameStatus.INITIAL_CIRCLE);
                } catch (NotReadyToRunException e) {
                    throw new RuntimeException(e);
                }

            }
            case INITIAL_CIRCLE -> {
                for (ClientControllerInterface c : listener.getPlayers()) {
                    ArrayList<ObjectiveCard> objectiveList = drawObjectiveCardForPlayer();
                    int indexOfCard = c.choosePersonaObjectiveCard(objectiveList);
                    c.setPersonalObjectiveCard(objectiveList.get(indexOfCard));
                    listener.updatePlayers(c.getNickname() + "has chosen his personal Objective card.");
                    InitialCard card = extractInitialCard();
                    String side = c.chooseSideInitialCard(card);
                    playInitialCard(c, card.chooseSide(Side.valueOf(side)));
                    listener.updatePlayers(c.getNickname() + "has played his initial card.");
                    listener.updatePlayers(getModel());
                }
                setStatus(GameStatus.RUNNING);

            }
            case RUNNING -> {
                numOfLastPlayer = 0;
                model.setCurrentPlayer(listener.getPlayers().get(numOfLastPlayer).getNickname());
                listener.updatePlayers("It's " + listener.getPlayers().get(numOfLastPlayer).getNickname() + "'s turn!");
                startTurnTimer(listener.getPlayers().get(numOfLastPlayer));
                for (ClientControllerInterface c : listener.getPlayers()) {
                    while(true) {
                        if (scoreMaxReached()) {
                            break;
                        }
                        c.receiveCommand();
                    }
                }
                setStatus(GameStatus.LAST_CIRCLE);

            }
            case LAST_CIRCLE -> {
                int adjustedNumOfLastPlayer = ((numOfLastPlayer - 1) + listener.getPlayers().size()) % listener.getPlayers().size();
                if (adjustedNumOfLastPlayer < (listener.getPlayers().size() - 1)) {
                    listener.updatePlayers("We are at the last round of the Game, " + listener.getPlayers().get(adjustedNumOfLastPlayer).getNickname() + "has reached the maximum score!");
                    for (ClientControllerInterface c : listener.getPlayers()) {
                    while(true) {
                        if (numOfLastPlayer == listener.getPlayers().size() - 1){
                            break;
                        }
                        c.receiveCommand();
                    }
                }

                }
                setStatus(GameStatus.ENDED);
            }
        }
    }

    /**
     * Starts a turn timer for the current player. The timer is scheduled to send a message to the player
     * after 60 seconds, prompting them to enter their MOVE command. If the player does not respond within
     * another 60 seconds, the turn is passed to the next player.
     *
     * @param client The client interface of the current player. This is used to send update messages to the player.
     */
    private void startTurnTimer(ClientControllerInterface client) {

        currentTimer = scheduler.schedule(() -> {
            try {
                client.sendUpdateMessage("Please enter your MOVE command.");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            currentTimer = scheduler.schedule(() -> {
                try {
                    client.sendUpdateMessage("Time is up! Passing your turn.");
                    passPlayerTurn();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }, 60, TimeUnit.SECONDS);
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * Passes the turn to the next player in the game.
     * This method increments the index of the last player, wrapping around to the start of the player list if necessary.
     * It then sets the current player in the game model to the new player, sends an update to all players notifying them of the new current player,
     * and starts a new turn timer for the new current player.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    private void passPlayerTurn() throws RemoteException {
        numOfLastPlayer = (numOfLastPlayer + 1) % listener.getPlayers().size();
        model.setCurrentPlayer(listener.getPlayers().get(numOfLastPlayer).getNickname());
        listener.updatePlayers("It's " + listener.getPlayers().get(numOfLastPlayer).getNickname() + "'s turn!");
        startTurnTimer(listener.getPlayers().get(numOfLastPlayer));
    }

    /**
     * Extracts common objective cards from the deck and adds them to the common objectives.
     * The method continues to extract cards until there are 2 common objective cards.
     * After extracting a card, it is removed from the objective card deck.
     */
    public void extractCommonObjectiveCards() {
        while (model.getCommonObjectivesCards().size() < 2) {
            int cardExtracted = random.nextInt(model.getObjectiveCardDeck().getSize() - 1);
            ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().getCards().get(cardExtracted);
            model.addCommonCard(c);
            model.removeCardFromDeck(c, model.getObjectiveCardDeck());
        }
    }

    /**
     * Extracts common gold and resource cards from their respective decks and adds them to the common cards.
     * The method continues to extract cards until there are 2 common gold cards and 2 common resource cards.
     * After a card is extracted, it is removed from its respective deck.
     */
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

    /**
     * Extracts an initial card from the deck.
     * The method randomly selects a card from the deck, removes it from the deck, and returns it.
     *
     * @return The extracted initial card.
     */
    public InitialCard extractInitialCard(){
        int cardExtracted = random.nextInt(model.getInitialCardDeck().getSize() - 1);
        InitialCard c = (InitialCard) model.getInitialCardDeck().getCards().get(cardExtracted);
        model.removeCardFromDeck(c, model.getInitialCardDeck());

        return c;

    }

    /**
     * Calculates and adds the points from the personal objective card to each player's score.
     * This method iterates through each player in the game and checks their personal objective card.
     * If the card is a SymbolObjectiveCard, it calculates the number of goals the player has achieved and the points they have earned.
     * If the card is a DispositionObjectiveCard, it calculates the number of dispositions the player has got and the points they have earned.
     * The points are then added to the player's score.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void addPersonalObjectiveCardPoints() throws RemoteException {
        for (ClientControllerInterface c : listener.getPlayers()) {
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

    /**
     * Calculates and adds the points from the common objective cards to each player's score.
     * This method iterates through each player in the game and for each common objective card,
     * it checks the type of the card (SymbolObjectiveCard or DispositionObjectiveCard), calculates the number of goals
     * the player has achieved and the points they have earned based on the card's rules.
     * The points are then added to the player's score.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void addCommonObjectiveCardsPoints() throws RemoteException {
        for (ClientControllerInterface c : listener.getPlayers()) {
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

    /**
     * Checks if any player's score has reached or exceeded the maximum score limit (20).
     * This method iterates through each player in the game and checks their current score.
     * If a player's score is 20 or more, the method returns true, indicating that the maximum score has been reached.
     * If no player's score is 20 or more, the method returns false.
     *
     * @return true if any player's score is 20 or more, false otherwise.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public boolean scoreMaxReached() throws RemoteException {
        for (ClientControllerInterface c : listener.getPlayers()) {
            if (c.getPlayer().getScore() >= 20) {
                return true;
            }
        }
        return false;
    }

    /** Method that initializes the game by extracting the common playground cards and the common objective cards.
     * It then extracts the cards for each player and adds it to their hand.
     *
     * @throws NotReadyToRunException If the game is not ready to run.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public synchronized void initializeGame() throws NotReadyToRunException, RemoteException {
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
        extractPlayerHandCards();
        System.out.println("Ho estratto le carte delle mani"); //TODO
    }

    /**
     * Finalizes the game by adding the points from the personal objective cards and the common objective cards to each player's score.
     * It then determines the winner of the game based on the player with the highest score.
     * Finally, it sends a message to all players with the final scores and disconnects all players from the game.
     *
     * @throws NotReadyToRunException If the game is not ready to run.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void FinalizeGame() throws NotReadyToRunException, RemoteException {
        addPersonalObjectiveCardPoints();
        addCommonObjectiveCardsPoints();
        listener.updatePlayers("Those are the final scores: \n");
        listener.updatePlayers();
        decreeWinner();
        listener.updatePlayers("The game is over!");
        listener.disconnectPlayers();
    }

    /**
     * Determines the winner of the game based on the player with the highest score.
     * If there is only one player with the highest score, they are declared the winner.
     * If there are multiple players with the highest score, a tie is declared between them.
     * The method then sends a message to all players with the winner or the list of players in a tie.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void decreeWinner() throws RemoteException {

        List<ClientControllerInterface> winners = new ArrayList<>();
        int score = Integer.MIN_VALUE;

        for (ClientControllerInterface c : listener.getPlayers()) {
            if (c.getPlayer().getScore() > score) {
                score = c.getPlayer().getScore();
                winners.clear();
                winners.add(c);
            } else if (c.getPlayer().getScore() == score) {
                winners.add(c);
            }
        }
        listener.updatePlayers(winners);

    }

    /**
     * Draws two objective cards for a player.
     * This method randomly selects two objective cards from the objective card deck and adds them to a list.
     * The cards are then removed from the objective card deck.
     *
     * @return A list of two objective cards drawn for the player.
     */
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

    /**
     * Gets the current game model.
     *
     * @return The current game model.
     */
    public PlayGround getModel() {
        return model;
    }

    /**
     * Sets the current game model.
     *
     * @param model The new game model.
     */
    public void setModel(PlayGround model) {
        this.model = model;
    }

    /**Method that updates the playground after a card has been played by a player
     * @param c the card that has been played*/
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

    /**Method that extracts the cards for each player and adds them to their hand*/
    public void extractPlayerHandCards() throws RemoteException {
        System.out.println("Extracting cards for each player...");
        for (ClientControllerInterface client : listener.getPlayers()) {
            while (client.getPlayer().getCardsInHand().size() < 2) {
                System.out.println("Extracting cards for " + client.getNickname());
                if (!model.getResourceCardDeck().getCards().isEmpty()) {
                    Collections.shuffle(model.getResourceCardDeck().getCards());
                    ResourceCard c = (ResourceCard) model.getResourceCardDeck().getCards().getLast();
                    model.removeCardFromDeck(c, model.getResourceCardDeck());
                    client.addCardToHand(c);
                    System.out.println(client.getPawnColor());
                }
            }
            if (!model.getGoldCardDeck().getCards().isEmpty()) {
                Collections.shuffle(model.getGoldCardDeck().getCards());
                GoldCard c = (GoldCard) model.getGoldCardDeck().getCards().remove(model.getGoldCardDeck().getCards().size() - 1);
                client.getPlayer().addCardToHand(c);
            }
        }
    }

    /**Method that returns the personal objective card of a player
     * @param nickname the nickname of the player
     * @return the personal objective card of the player*/
    public ObjectiveCard getPersonalObjective(String nickname) throws RemoteException {
        for (ClientControllerInterface client : listener.getPlayers()) {
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
                p.getPlayArea().addInitialCardOnArea(initialCard);
            }
        }
    }
//
//    /**Method that Checks if the Player is trying to cover two corners of the same Card while placing a card
//     * @return  true id the position is valid, false otherWise
//     * @param playArea of the current player
//     * @param column1 chosen column where to place the card
//     * @param row1 chosen row where to place the card*/
//    public boolean ValidTwoCornersSameCard(PlayArea playArea, int row1, int column1){
//        //Graphic playArea is shown with one more column and one more row at the beginning and at the end
//        //compared to the actual PlayArea, to give the Player the opportunity to choose the position,
//        //rather than the card and the corner
//        int r = row1 - 1;
//        int c = column1 - 1;
//        for (int i=r-1; i<=r+1; i++){
//            for (int j=c-1; j<=c+1; c++){
//                if(i==r || j==c){
//                    if (playArea.getCardInPosition(i,j)!=null) // if we have cards in the adjacent positions
//                        //we are trying to cover two corners of the same card
//                        return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    //Method still to revise and test
//    /**Method that allows the Player to choose the position where to place the card in a more UserFriendly way*/
//    public boolean ValidPositionCardOnArea(PlayArea playArea, int row1, int column1, SideOfCard newCard) {
//        //Graphic playArea is shown with one more column and one more row at the beginning and at the end
//        //compared to the actual PlayArea, to give the Player the opportunity to choose the position,
//        //rather than the card and the corner
//        int r = row1 - 1;
//        int c = column1 - 1;
//
//        //Check if the position on the playArea is Already occupied
//        if (playArea.getCardInPosition(r, c) != null)
//            return false;
//
//        //Finds a Card to cover for the addCardOnArea method in PlayArea.
//        //This way checks if the player is trying to place a card without attaching it to another one
//        SideOfCard neighbourCard=new SideOfCard(null, null);
//        Corner cornerToCover=null;
//        for (Corner[] Rowcorner : newCard.getCorners()) {
//            for (Corner corner : Rowcorner) {
//                Pair<Integer, Integer> newPosition = corner.getPosition().PositionNewCard(newCard);
//                if (newPosition != null) {
//                    int rowToCheck = newPosition.getFirst();
//                    int columnToCheck = newPosition.getSecond();
//                    if (playArea.rowExists(rowToCheck) && playArea.columnExists(columnToCheck)) {
//                        List<SideOfCard> row = playArea.getCardsOnArea().get(rowToCheck);
//                        if (row != null) {
//                            neighbourCard = row.get(columnToCheck);
//                            if (neighbourCard != null){
//                                cornerToCover=neighbourCard.getCornerInPosition(corner.getPosition().CoverCorners());
//                                if (!cornerToCover.isHidden())
//                                    break;
//                                else
//                                    return false;// We are trying to cover a hidden Corner, so the position Is Invalid
//
//
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        }
//        if (neighbourCard == null)
//            return false;
//        //Add Checks
//        playArea.AddCardOnArea(newCard,cornerToCover,neighbourCard);
//        return true;
//    }
//
//    /**Method that checks if the Player is trying to cover any hidden corners*/
//    public boolean notTryingToCoverHiddenCorners(PlayArea playArea, int row1, int column1, SideOfCard newCard){
//        for (Corner[] Rowcorner : newCard.getCorners()) {
//            for (Corner corner : Rowcorner) {
//                Pair<Integer, Integer> newPosition = corner.getPosition().PositionNewCard(newCard);
//                if (newPosition != null) {
//                    int rowToCheck = newPosition.getFirst();
//                    int columnToCheck = newPosition.getSecond();
//                    if (playArea.rowExists(rowToCheck) && playArea.columnExists(columnToCheck)) {
//                        List<SideOfCard> row = playArea.getCardsOnArea().get(rowToCheck);
//                        if (row != null) {
//                            SideOfCard neighbourCard = row.get(columnToCheck);
//                            if (neighbourCard != null) {
//                                CornerPosition cornerToCover = corner.getPosition().CoverCorners();
//                                if (neighbourCard.getCornerInPosition(cornerToCover).isHidden())
//                                    return false;
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return true;
//    }


}

package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Model.Enumerations.*;
import it.polimi.ingsw.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;
//import java.util.concurrent.locks.ReentrantLock;


public class GameController extends UnicastRemoteObject implements  Runnable, Serializable, GameControllerInterface {
    private GameListener listener = new GameListener();

    private final List<PawnColor> availableColors;

    private GameStatus status;

    private final int numPlayers;

    private final int id;

    private transient ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private transient ScheduledFuture<?> currentTimer;

    private String currentPlayerNickname;

    private PlayGround model;

    private transient ExecutorService executor = Executors.newSingleThreadExecutor();

    //private final ReentrantLock turnLock = new ReentrantLock();



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
        this.currentPlayerNickname = "";
        this.status = GameStatus.WAITING;
        try {
            model = new PlayGround();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        availableColors= new ArrayList<>();
        availableColors.addAll(Arrays.asList(PawnColor.values()));

    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.executor = Executors.newSingleThreadExecutor();
    }


    @Override
    public void run() {
        while(!status.equals(GameStatus.ENDED)){
            try {
                GameLoop(status);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FinalizeGame();
        } catch (RemoteException | NotReadyToRunException e) {
            throw new RuntimeException(e);
        }
    } /**
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
    void GameLoop(GameStatus status) throws RemoteException {
        int currentPlayerIndex=0;
        switch(status) {
            case SETUP -> {
                try {

                    initializeGame();
                    List<Thread> threads = new ArrayList<>();
                    for (ClientControllerInterface c: listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                c.WhatDoIDoNow("INITIALIZE");
                            } catch (Exception e) {
                                //todo handle exception
                            }
                        });
                        threads.add(thread);
                        thread.start();
                    }

                    for (Thread thread : threads) {
                        thread.join();
                    }
                    System.out.println("Game is ready to run!");
                    setStatus(GameStatus.INITIAL_CIRCLE);
                } catch (NotReadyToRunException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            case INITIAL_CIRCLE -> {
                for (ClientControllerInterface c : listener.getPlayers()) {
                    try {
                        c.WhatDoIDoNow("INITIAL");
                    } catch (Exception e) {
                        //todo handle exception
                    }
                }
                setStatus(GameStatus.RUNNING);

            }
            case RUNNING -> {//
                System.out.println("Current player: " + listener.getPlayers().get(currentPlayerIndex).getNickname()+"index: "+currentPlayerIndex);
                while(status.equals(GameStatus.RUNNING)) {
                    ClientControllerInterface currentPlayer = listener.getPlayers().get(currentPlayerIndex);
                    try {
                        startTurnTimer(currentPlayer);
                        currentPlayer.WhatDoIDoNow("PLAY-TURN");
                    } catch (Exception e) {
                        //todo handle exception
                    }

                    new Thread(() -> {
                        for (ClientControllerInterface c : listener.getPlayers()) {
                            try {
                                if (!c.getNickname().equals(currentPlayerNickname)) {
                                    try {
                                        c.WhatDoIDoNow("NOT-MY-TURN");
                                        c.sendUpdateMessage("It's "+ currentPlayerNickname + "'s turn!");
                                    } catch (Exception e) {
                                        //todo handle exception
                                    }
                                }
                            } catch (RemoteException e) {
                                //todo handle exception
                            }
                        }
                    }).start();

                    currentPlayerIndex=passPlayerTurn(currentPlayerIndex);
                }


            }
            case LAST_CIRCLE -> {//todo change this
                int adjustedNumOfLastPlayer = ((currentPlayerIndex - 1) + listener.getPlayers().size()) % listener.getPlayers().size();
                if (adjustedNumOfLastPlayer < (listener.getPlayers().size() - 1)) {
                    listener.updatePlayers("We are at the last round of the Game, " + listener.getPlayers().get(adjustedNumOfLastPlayer).getNickname() + "has reached the maximum score!", listener.getPlayers().get(adjustedNumOfLastPlayer));
                    for (ClientControllerInterface c : listener.getPlayers()) {
                        while (currentPlayerIndex != listener.getPlayers().size() - 1) {

                        }
                    }

                }
                setStatus(GameStatus.ENDED);
            }
        }
    }




    /**
     * Method that returns the available colors for the player to choose from
     *
     * @return the available colors for the player to choose from
     */

    @Override
    public List<PawnColor> getAvailableColors() throws RemoteException{
        return this.availableColors;
    }




    /**
     * Method used to remove a color from the list of the available ones, once the color has been
     * chosen by a player
     * @param color chosen by the player
     * */

    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {
        availableColors.remove(color);
    }


    private void setCurrentPlayerNickname(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
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
     * Gets the ID of the game controller.
     *
     * @return The ID of the game controller.
     */

    public int getId() throws RemoteException {
        return id;
    }






    /**
     * Sets the current game model.
     *
     * @param model The new game model.
     */

    public void setModel(PlayGround model) {
        this.model = model;
    }

    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        for(ClientControllerInterface c: listener.getPlayers()){
            players.add(c.getPlayer());
        }
        return players;
    }


    /**Method that returns the number of players in the game
     * @return the number of players in the game*/
    public int getNumPlayers() throws RemoteException {

        return numPlayers;
    }





    /**
     * Method that returns the GameListener of the game
     * @return the GameListener of the game
     * */

    public GameListener getListener() throws RemoteException {
        if (listener == null) {
            System.out.println("Listener is null");
            listener = new GameListener();
        }
        return listener;
    }





    /**
     * Method that adds a player to the game
     * @param client the client that is added to the game
     * */
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
            listener.updatePlayers("Waiting for more players to join the game...");
            }

        }





    /**
     * Notifies all players that a new player has joined the game.
     * The method sends an update message to all players with the list of players in the game.
     * It then prompts the new player to choose a pawn color from the available colors.
     * If the maximum number of players has been reached, the method checks if the game is ready to run.
     */

    public synchronized void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {
        System.out.println("Notifying players that a new player has joined the game...");
        CheckMaxNumPlayerReached();
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
                } catch (RemoteException e) {
                    //todo handle exception
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

    private int passPlayerTurn(int currentPlayerIndex) throws RemoteException {//todo change
        currentPlayerIndex = (currentPlayerIndex + 1) % listener.getPlayers().size();
        setCurrentPlayerNickname(listener.getPlayers().get(currentPlayerIndex).getNickname());
        return currentPlayerIndex;
    }





    /**
     * Extracts common objective cards from the deck and adds them to the common objectives.
     * The method continues to extract cards until there are 2 common objective cards.
     * After extracting a card, it is removed from the objective card deck.
     */

    public synchronized void extractCommonObjectiveCards() {
        synchronized (model.getObjectiveCardDeck()) {//todo check synchronization
            while (model.getCommonObjectivesCards().size() < 2) {
                ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().drawCard();
                model.addCommonCard(c);
            }
        }
    }





    /**
     * Extracts common gold and resource cards from their respective decks and adds them to the common cards.
     * The method continues to extract cards until there are 2 common gold cards and 2 common resource cards.
     * After a card is extracted, it is removed from its respective deck.
     */

    public void extractCommonPlaygroundCards() {
        synchronized (model) {//todo check synchronization
            while (model.getCommonGoldCards().size() < 2) {
                GoldCard c = (GoldCard) model.getGoldCardDeck().drawCard();
                model.addCommonCard(c);
            }
            while (model.getCommonResourceCards().size() < 2) {
                ResourceCard c = (ResourceCard) model.getResourceCardDeck().drawCard();
                model.addCommonCard(c);;
            }
        }
    }





    /**
     * Method that draws and initial Card from the deck
     *
     * @return The extracted initial card.
     */

    public InitialCard extractInitialCard() throws RemoteException{
        synchronized (model.getInitialCardDeck()) {
            return (InitialCard) model.getInitialCardDeck().drawCard();
        }
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
            if (c.getPlayer().getPersonalObjectiveCard() instanceof SymbolObjectiveCard card) {
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

    public void addCommonObjectiveCardsPoints() throws RemoteException {//todo move this method to be called by each player during a final state
        for (ClientControllerInterface c : listener.getPlayers()) {
            for (ObjectiveCard card : model.getCommonObjectivesCards()) {
                if (card instanceof SymbolObjectiveCard s) {
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




    /** Method that initializes the game by extracting the common playground cards and the common objective cards.
     * It then extracts the cards for each player and adds it to their hand.
     *
     * @throws NotReadyToRunException If the game is not ready to run.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    //todo handle exceptions in a better way(?)
    public synchronized void initializeGame() throws NotReadyToRunException, RemoteException {
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
    }




    /**
     * Finalizes the game by adding the points from the personal objective cards and the common objective cards to each player's score.
     * It then determines the winner of the game based on the player with the highest score.
     * Finally, it sends a message to all players with the final scores and disconnects all players from the game.
     *
     * @throws NotReadyToRunException If the game is not ready to run.
     * @throws RemoteException If a remote or network communication error occurs.
     */

    public void FinalizeGame() throws NotReadyToRunException, RemoteException {//todo modify this and move it to the client
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
    public void decreeWinner() throws RemoteException {//todo change

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
     * Method that extracts the cards for the first hand of the player
     *
     * @return the hand of cards
     */

    @Override
    public synchronized ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException {
        System.out.println("extracting cards");
        ArrayList<PlayCard> hand= new ArrayList<>();
        ResourceCard card1= (ResourceCard) model.getResourceCardDeck().drawCard();
        hand.add(card1);
        ResourceCard card2=(ResourceCard) model.getResourceCardDeck().drawCard();
        hand.add(card2);
        GoldCard card3=(GoldCard) model.getGoldCardDeck().drawCard();
        hand.add(card3);
        return hand;
    }





    /**
     * Method that returns the personal objective card of a player
     *
     * @return the personal objective card of the player
     */

    @Override
    public synchronized ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException {
        ArrayList<ObjectiveCard> ObjectiveOptions=new ArrayList<>();
        System.out.println(model.getObjectiveCardDeck().getSize());
        ObjectiveCard card1= (ObjectiveCard) model.getObjectiveCardDeck().drawCard();
        System.out.println(model.getObjectiveCardDeck().getSize());
        ObjectiveCard card2=(ObjectiveCard) model.getObjectiveCardDeck().drawCard();
        System.out.println(model.getObjectiveCardDeck().getSize());
        ObjectiveOptions.add(card1);
        ObjectiveOptions.add(card2);
        return ObjectiveOptions;
    }






    @Override
    public boolean isValidMove(PlayArea playArea, int row, int column, SideOfCard newCard) throws RemoteException {
        boolean covering= false;
        //check id the row and the column exist
        if(!playArea.rowExists(row) || !playArea.columnExists(column)){
            return false;
        }
        //check if the spot is already occupied
        if(playArea.getCardInPosition(row, column) != null){
            return false;
        }
        //check if the player is trying to cover two corners of the same card
        if(!ValidTwoCornersSameCard(playArea, row, column)){
            return false;
        }

        for(Corner[] corners: newCard.getCorners()){
            for(Corner c: corners){
                Pair<Integer, Integer> newPosition = c.getPosition().neighborToCheck(row,column);
                if (newPosition != null) {// check
                    int rowToCheck = newPosition.getFirst();//get row to check
                    int columnToCheck = newPosition.getSecond();// get column to check
                    if (playArea.rowExists(rowToCheck) && playArea.columnExists(columnToCheck)) {// check that these positions exist
                        SideOfCard neighbourCard = playArea.getCardInPosition(rowToCheck, columnToCheck);//get the card in the position
                            if (neighbourCard != null) {//check that this card is not occupied by a null object
                                CornerPosition cornerToCover = c.getPosition().CoverCorners(); //get the corresponding corner to cover
                                if (neighbourCard.getCornerInPosition(cornerToCover).isHidden()) {//check if the corner is hidden
                                    return false;//we are trying to cover a hidden corner, so the position is invalid
                                }
                                else {
                                    covering = true;//we are covering a valid corner
                                }

                            }
                        }
                    }
                }
            }
        return covering;
        }



    @Override
    public void sendPrivateMessage(Message message, String receiver) throws RemoteException {
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RESET = "\u001B[0m";
        char envelope = '\u2709';
        String bold = "\033[1m";
        String reset = "\033[0m";
        String receiverName =receiver;
        for(ClientControllerInterface c: listener.getPlayers()){
            if(c.getNickname().equals(receiverName)){
                try {
                    c.sendUpdateMessage("\n"+ANSI_CYAN+bold+envelope+"["+ message.getSender().getNickname()+"] to [YOU] : "+message.getText()+"\n"+reset+ANSI_RESET);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**Method that Checks if the Player is trying to cover two corners of the same Card while placing a card
     * @return  true id the position is valid, false otherWise
     * @param playArea of the current player
     * @param column chosen column where to place the card
     * @param row chosen row where to place the card*/
    private boolean ValidTwoCornersSameCard(PlayArea playArea, int row, int column){
        for (int i=row-1; i<=row+1; i++){
            for (int j=column-1; j<=column+1; column++){
                if(i==row || j==column){
                    if (playArea.getCardInPosition(i,j)!=null) // if we have cards in the adjacent positions
                        //we are trying to cover two corners of the same card
                        return false;
                }
            }
        }
        return true;
    }



}

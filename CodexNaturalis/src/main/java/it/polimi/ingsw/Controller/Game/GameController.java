package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Chat;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Model.Enumerations.*;
import it.polimi.ingsw.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.Controller.GameState;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private int playersWhoChoseObjective = 0;

    private transient ExecutorService executor = Executors.newSingleThreadExecutor();

    private Chat chat=new Chat();

    private int currentPlayerIndex = 0;

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
            listener.disconnectPlayers();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
    public void GameLoop(GameStatus status) throws RemoteException {
        switch(status) {
            case SETUP -> {
                try {

                    initializeGame();
                    List<Thread> threads = new ArrayList<>();
                    for (ClientControllerInterface c : listener.getPlayers()) {
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
                    saveGameState();
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
                saveGameState();

            }
            case RUNNING -> {
                while (status.equals(GameStatus.RUNNING)) {
                    ClientControllerInterface currentPlayer = listener.getPlayers().get(currentPlayerIndex);
                    currentPlayerNickname = currentPlayer.getNickname();

                    List<Thread> threads = new ArrayList<>();
                    CountDownLatch latch = new CountDownLatch(1);

                    for (ClientControllerInterface c : listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                if (!c.getNickname().equals(currentPlayerNickname)) {
                                    c.sendUpdateMessage("It's " + currentPlayerNickname + "'s turn!");
                                    c.WhatDoIDoNow("NOT-MY-TURN");
                                } else {
                                    c.WhatDoIDoNow("PLAY-TURN");
                                    latch.countDown();
                                }
                            } catch (RemoteException e) {
                                //todo handle exception
                            }
                        });
                        threads.add(thread);
                        thread.start();
                    }

                    for (Thread thread : threads) {
                        try {
                            latch.await(); // wait for the current player to finish
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Check the score after the current player has finished their turn
                    if (currentPlayer.getPlayer().getScore() >= 20) {
                        setStatus(GameStatus.LAST_CIRCLE);
                        break;
                    }

                    currentPlayerIndex = passPlayerTurn(currentPlayerIndex);
                    saveGameState();
                }
            }
            case LAST_CIRCLE -> {
                if (currentPlayerIndex < (listener.getPlayers().size()-1)) {
                    listener.updatePlayers("We are at the last round of the Game, " + listener.getPlayers().get(currentPlayerIndex).getNickname() + " has reached the maximum score!", listener.getPlayers().get(currentPlayerIndex));
                    listener.getPlayers().get(currentPlayerIndex).sendUpdateMessage("You have reached the maximum score!");
                    listener.updatePlayers("The game will continue until all players have played their last turn.");
                    List<ClientControllerInterface> playersToIterate = new ArrayList<>(listener.getPlayers().subList(currentPlayerIndex + 1, listener.getPlayers().size()));
                    currentPlayerIndex = currentPlayerIndex + 1;

                    while (status.equals(GameStatus.LAST_CIRCLE)) {
                        ClientControllerInterface currentPlayer = listener.getPlayers().get(currentPlayerIndex);
                        currentPlayerNickname = currentPlayer.getNickname();

                        List<Thread> threads = new ArrayList<>();
                        CountDownLatch latch = new CountDownLatch(1);

                        for (ClientControllerInterface c : playersToIterate) {
                            Thread thread = new Thread(() -> {
                                try {
                                    if (!c.getNickname().equals(currentPlayerNickname)) {
                                        c.sendUpdateMessage("It's " + currentPlayerNickname + "'s turn!");
                                        c.WhatDoIDoNow("NOT-MY-TURN");
                                    } else {
                                        c.WhatDoIDoNow("PLAY-TURN");
                                        latch.countDown();
                                    }
                                } catch (RemoteException e) {
                                    //todo handle exception
                                }
                            });
                            threads.add(thread);
                            thread.start();
                        }

                        for (Thread thread : threads) {
                            try {
                                latch.await(); // wait for the current player to finish
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (currentPlayerIndex == listener.getPlayers().size() - 1) {
                            setStatus(GameStatus.FINILIZE);
                            saveGameState();
                            break;
                        }

                        currentPlayerIndex = passPlayerTurn(currentPlayerIndex);
                    }
                }
                setStatus(GameStatus.FINILIZE);
                saveGameState();
            }
            case FINILIZE -> {
                try {
                    List<Thread> threads = new ArrayList<>();
                    for (ClientControllerInterface c : listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                c.WhatDoIDoNow("OBJECTIVE-COUNT");
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
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<ClientControllerInterface> winners = decreeWinner();
                List<ClientControllerInterface> losers = new ArrayList<>(listener.getPlayers());
                losers.removeAll(winners);

                List<Thread> winnerThreads = new ArrayList<>();
                for (ClientControllerInterface winner : winners) {
                    Thread thread = new Thread(() -> {
                        try {
                            winner.WhatDoIDoNow("WINNER");
                        } catch (Exception e) {
                            //todo handle exception
                        }
                    });
                    winnerThreads.add(thread);
                    thread.start();
                }

                List<Thread> loserThreads = new ArrayList<>();
                for (ClientControllerInterface loser : losers) {
                    Thread thread = new Thread(() -> {
                        try {
                            loser.WhatDoIDoNow("LOSER");
                        } catch (Exception e) {
                            //todo handle exception
                        }
                    });
                    loserThreads.add(thread);
                    thread.start();
                }

                try {
                    for (Thread thread : winnerThreads) {
                        thread.join();
                    }
                    for (Thread thread : loserThreads) {
                        thread.join();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
//        System.out.println(client.getNickname());
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
            }, 120, TimeUnit.SECONDS);
        }, 120, TimeUnit.SECONDS);
    }





    /**
     * Passes the turn to the next player in the game.
     * This method increments the index of the last player, wrapping around to the start of the player list if necessary.
     * It then sets the current player in the game model to the new player, sends an update to all players notifying them of the new current player,
     * and starts a new turn timer for the new current player.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */

    private int passPlayerTurn(int currentPlayerIndex) throws RemoteException {
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

    public int getPlayersWhoChoseObjective() throws RemoteException{
        return playersWhoChoseObjective;
    }

    public void incrementPlayersWhoChoseObjective() throws RemoteException{
        playersWhoChoseObjective++;
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


    public ArrayList<ClientControllerInterface> decreeWinner() throws RemoteException {
        ArrayList<ClientControllerInterface> winners = new ArrayList<>();
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

        return winners;
    }

    public String finalRanking() throws RemoteException {
        List<ClientControllerInterface> players = new ArrayList<>(listener.getPlayers());

        players.sort((player1, player2) -> {
            try {
                return player1.getNickname().compareTo(player2.getNickname());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        players.sort((player1, player2) -> {
            try {
                return Integer.compare(player2.getPlayer().getScore(), player1.getPlayer().getScore());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        StringBuilder scores = new StringBuilder();
        for (ClientControllerInterface player : players) {
            scores.append(player.getNickname()).append(" : ").append(player.getPlayer().getScore()).append("\n");
        }

        return scores.toString();
    }

    /**
     * Method that extracts the cards for the first hand of the player
     *
     * @return the hand of cards
     */

    @Override
    public synchronized ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException {
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
        ObjectiveCard card1= (ObjectiveCard) model.getObjectiveCardDeck().drawCard();
        ObjectiveCard card2=(ObjectiveCard) model.getObjectiveCardDeck().drawCard();
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


    public Chat getChat() {
        synchronized (chat) {
            return chat;
        }
    }


    @Override
    public void addMessageToChat(Message message) {
        synchronized (chat) {
            chat.addMessage(message);
        }
    }

    public void saveGameState() throws RemoteException {

        GameState gameState = new GameState(getListener().getPlayers().getFirst().getRound(), getListener().getPlayers(), model, getStatus());

        int round = getListener().getPlayers().getFirst().getRound();

        gameState.setRound(round);
        gameState.setModel(model);
        gameState.setPlayers(getListener().getPlayers());
        gameState.setStatus(getStatus());

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gameState.txt"))) {
            out.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState loadGameState(String nickname) throws RemoteException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gameState.txt"))) {
            GameState gameState = (GameState) in.readObject();

            for (ClientControllerInterface playerController : gameState.getPlayers()) {
                if (playerController.getNickname().equals(nickname)) {
                    // Find the matching player in the game listener's players
                    for (ClientControllerInterface listenerPlayerController : getListener().getPlayers()) {
                        if (listenerPlayerController.getNickname().equals(nickname)) {
                            // Update the player's state
                            listenerPlayerController = (ClientControllerInterface) playerController.getPlayer();
                            break;
                        }
                    }

                    // Update the game state
                    model = gameState.getModel();
                    status = gameState.getStatus();
                    return gameState;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}

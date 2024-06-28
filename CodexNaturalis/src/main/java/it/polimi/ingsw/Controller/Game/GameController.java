package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Cards.*;

import it.polimi.ingsw.Model.Enumerations.*;

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

/**
 * The GameController class is responsible for managing the game logic and coordinating the game flow.
 * It implements Runnable, Serializable, and GameControllerInterface.
 * It extends UnicastRemoteObject to allow remote method invocation.
 *
 * The class contains several fields:
 * - listener: a GameListener object that listens for game events.
 * - availableColors: a list of available colors for the players.
 * - status: the current status of the game.
 * - numPlayers: the number of players in the game.
 * - id: the game ID.
 * - scheduler: a ScheduledExecutorService for scheduling tasks.
 * - currentTimer: a ScheduledFuture representing the current timer task.
 * - currentPlayerNickname: the nickname of the current player.
 * - model: the current game model.
 * - playersWhoChoseObjective: the number of players who have chosen their objective.
 * - executor: an ExecutorService for executing tasks.
 * - chat: a Chat object for managing the game chat.
 * - currentPlayerIndex: the index of the current player in the list of players.
 *
 * The class also contains several methods for managing the game, such as running the game, saving and loading the game state, and managing player turns.
 *
 */
public class GameController extends UnicastRemoteObject implements  Runnable, Serializable, GameControllerInterface {
    /**
    * The GameListener object that listens for game events.
    */
private GameListener listener = new GameListener();

/**
 *A list of available colors for the players. Each color is represented as a PawnColor enum.
 */
private final List<PawnColor> availableColors;

/**
 *An ArrayList of strings representing the nicknames of the players in the game.
 */
private ArrayList<String> nicknames = new ArrayList<>();

/**
 *The current status of the game. The status is represented as a GameStatus enum.
 */
private GameStatus status;

/**
* The number of players in the game. This is a constant value that is set when the game is created.
 */
private final int numPlayers;

/**
* The unique identifier for the game. This is a constant value that is set when the game is created.
 */
private final int id;

/**
 *A ScheduledExecutorService for scheduling tasks. This is a transient field, meaning it is not serialized when the game is saved.
 */
private transient ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

/**
*The nickname of the current player. This is a string that represents the player's chosen nickname.
 */
private String currentPlayerNickname;

/**
 *The current game model. The game model represents the state of the game, including the game board, the players, and the cards.
 */
private PlayGround model;

/**
*The number of players who have chosen their objective. This is an integer that is incremented each time a player chooses their objective.
 */
private int playersWhoChoseObjective = 0;

/**
* An ExecutorService for executing tasks. This is a transient field, meaning it is not serialized when the game is saved.
 */
private transient ExecutorService executor = Executors.newSingleThreadExecutor();

    //private Chat chat=new Chat();

    /**
     * The index of the current player in the list of players.
     * This index is used to keep track of whose turn it is in the game.
     * It is initialized to 0, meaning the first player in the list is the current player at the start of the game.
     * The index is updated each time a player finishes their turn, passing the turn to the next player in the list.
     */
    private int currentPlayerIndex = 0;

    //private final ReentrantLock turnLock = new ReentrantLock();

    /**
     * A list of Thread objects representing the threads running the game loop for each player.
     * Each thread is responsible for managing the game actions of a specific player.
     * The list is used to keep track of all active game loop threads, allowing the game controller to manage them as needed.
     * For example, the game controller can interrupt all game loop threads when a player disconnects or the game ends.
     */
    private List<Thread> gameLoopThreads;

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
            throw new GameInitializationException("Error while initializing model", e);
        }
        availableColors= new ArrayList<>();
        availableColors.addAll(Arrays.asList(PawnColor.values()));

    }

    /**
     * This method is used for custom serialization of the GameController object.
     * It writes the non-transient and non-static fields of the current class to the ObjectOutputStream.
     * The method does not need to concern itself with the state belonging to its superclasses or subclasses.
     * Those states will be handled by the JVM calling their writeObject methods.
     *
     * @param out the ObjectOutputStream to which data is written
     * @throws IOException if I/O errors occur while writing to the ObjectOutputStream
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * This method is used for custom deserialization of the GameController object.
     * It reads the non-transient and non-static fields of the current class from the ObjectInputStream.
     * The method does not need to concern itself with the state belonging to its superclasses or subclasses.
     * Those states will be handled by the JVM calling their readObject methods.
     * After reading the default object, it initializes the transient fields 'scheduler' and 'executor'.
     *
     * @param in the ObjectInputStream from which data is read
     * @throws IOException if I/O errors occur while reading from the ObjectInputStream
     * @throws ClassNotFoundException if the class of a serialized object could not be found
     */
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * This method is the main loop of the game.
     * It continues to run the game until the game status is ENDED.
     * It calls the GameLoop method with the current game status as a parameter.
     * If a RemoteException occurs during the execution of the GameLoop method, it is wrapped in a RuntimeException and rethrown.
     * After the game has ended, it disconnects all players.
     * If a RemoteException occurs during the disconnection of players, it is wrapped in a RuntimeException and rethrown.
     */
    @Override
    public void run() {
        while(!status.equals(GameStatus.ENDED)){
            try {
                GameLoop(status);
            } catch (RemoteException e) {
                throw new GameRunningException("Error while running game loop", e);
            }
        }
        try {
            shutdown();
            System.out.println("Game ended, disconnecting all players.");
        } catch (RemoteException e) {
            throw new GameRunningException("Error while game shutdown", e);
        }

    }

    /**
     * This method controls the main game loop, performing actions based on the current game status.
     *
     * @param status The current status of the game. It can be one of the following:
     *               WAITING - The game is waiting for players to join.
     *               SETUP - The game is being initialized.
     *               INITIAL_CIRCLE - Players are choosing their personal objective cards and playing their initial cards.
     *               RUNNING - The game is in progress and players are taking turns to play.
     *               LAST_CIRCLE - The game is in its final round.
     *               ENDED - The game has ended.
     *
     * The method uses a switch statement to handle different game statuses. In each case, it performs the necessary actions
     * to progress the game. For example, in the SETUP case, it initializes the game and sets the status to INITIAL_CIRCLE.
     * In the RUNNING case, it manages player turns and checks if the game should progress to the LAST_CIRCLE status.
     * In the LAST_CIRCLE case, it handles the final round of the game and sets the status to FINILIZE.
     * In the FINILIZE case, it calculates the final scores and determines the winner(s) of the game, then sets the status to ENDED.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void GameLoop(GameStatus status) throws RemoteException {
        switch(getStatus()) {
            case SETUP -> {
                try {

                    initializeGame();
                    gameLoopThreads = new ArrayList<>();
                    for (ClientControllerInterface c : listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                c.WhatDoIDoNow("INITIALIZE");
                                for(ClientControllerInterface client: listener.getPlayers()) {
                                    client.sendUpdateMessage(c.getNickname() + " has chosen the personal objective card.");
                                    if (getPlayersWhoChoseObjective() < getPlayers().size()) {
                                        c.sendUpdateMessage("Waiting for other players to choose their personal objective card...");
                                    } else {
                                        client.sendUpdateMessage("All players have chosen their personal objective card.");
                                    }
                                }
                            } catch (Exception e) {
                                clientDisconnected(c);
                            }
                        });
                        gameLoopThreads.add(thread);
                        thread.start();
                    }

                    for (Thread thread : gameLoopThreads) {
                        thread.join();
                    }
                    System.out.println("Game is ready to run!");

                    if(getStatus() != GameStatus.FINILIZE)
                        setStatus(GameStatus.INITIAL_CIRCLE);
                } catch (InterruptedException e) {
                    throw new GameSetupException("Error during game setup", e);
                }

            }
            case INITIAL_CIRCLE -> {
                for (ClientControllerInterface c : listener.getPlayers()) {
                    try {
                        for (ClientControllerInterface client : listener.getPlayers()) {
                            if (!client.getNickname().equals(c.getNickname()))
                                client.sendUpdateMessage("It's " + c.getNickname() + "'s turn to play initial card!");
                        }
                        c.WhatDoIDoNow("INITIAL");
                        for (ClientControllerInterface client : listener.getPlayers()) {
                            if (!client.getNickname().equals(c.getNickname()))
                                client.sendUpdateMessage(c.getNickname() + " has played the initial card.");
                        }
                    } catch (Exception e) {
                        clientDisconnected(c);
                    }
                }
                if(getStatus() != GameStatus.FINILIZE)
                    setStatus(GameStatus.RUNNING);
            }
            case RUNNING -> {
                while (getStatus().equals(GameStatus.RUNNING)) {
                    ClientControllerInterface currentPlayer = listener.getPlayers().get(currentPlayerIndex);
                    currentPlayerNickname = currentPlayer.getNickname();

                    gameLoopThreads = new ArrayList<>();

                    for (ClientControllerInterface c : listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                if (!c.getNickname().equals(currentPlayerNickname)) {
                                    c.sendUpdateMessage("It's " + currentPlayerNickname + "'s turn!");
                                } else {
                                    PlayGround playGround = (PlayGround) c.WhatDoIDoNow("PLAY-TURN");
                                    setModel(playGround);
                                    for(ClientControllerInterface client: listener.getPlayers()) {
                                        client.sendUpdateMessage(c.getNickname() + " has played a card.");
                                        client.sendUpdateMessage("This is the current Playground: ");
                                        client.showBoardAndPlayAreas(playGround);
                                    }
                                }
                            } catch (RemoteException e) {
                                clientDisconnected(c);
                            }
                        });
                        gameLoopThreads.add(thread);
                        thread.start();
                    }

                    for (Thread thread : gameLoopThreads) {
                        try {
                            thread.join(); // wait for the current player to finish
                        } catch (InterruptedException e) {
                            throw new GameRunningException("Error while waiting for thread to finish", e);
                        }
                    }


                    if(getStatus() != GameStatus.FINILIZE) {
                        // Check the score after the current player has finished their turn
                        if (currentPlayer.getPlayer().getScore() >= 20) {
                            setStatus(GameStatus.LAST_CIRCLE);
                            break;
                        }
                    }

                    currentPlayerIndex = passPlayerTurn(currentPlayerIndex);
                }
            }
            case LAST_CIRCLE -> {
                if (currentPlayerIndex < (listener.getPlayers().size() - 1)) {
                    listener.getPlayers().forEach(c -> {
                        try {
                            c.sendUpdateMessage("We are at the last round of the Game, " + listener.getPlayers().get(currentPlayerIndex).getNickname() + " has reached the maximum score!");
                        } catch (RemoteException e) {
                            clientDisconnected(c);
                        }
                    });
                    listener.getPlayers().get(currentPlayerIndex).sendUpdateMessage("You have reached the maximum score!");
                    listener.getPlayers().forEach(c -> {
                        try {
                            c.sendUpdateMessage("The game will continue until all players have played their last turn.");
                        } catch (RemoteException e) {
                            clientDisconnected(c);
                        }
                    });
                    List<ClientControllerInterface> playersToIterate = new ArrayList<>(listener.getPlayers().subList(currentPlayerIndex + 1, listener.getPlayers().size()));
                    currentPlayerIndex = currentPlayerIndex + 1;

                    while (getStatus().equals(GameStatus.LAST_CIRCLE)) {
                        ClientControllerInterface currentPlayer = listener.getPlayers().get(currentPlayerIndex);
                        currentPlayerNickname = currentPlayer.getNickname();

                        gameLoopThreads = new ArrayList<>();
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
                                    clientDisconnected(c);
                                }
                            });
                            gameLoopThreads.add(thread);
                            thread.start();
                        }

                        for (Thread thread : gameLoopThreads) {
                            try {
                                latch.await(); // wait for the current player to finish
                            } catch (InterruptedException e) {
                                throw new GameRunningException("Error while waiting for thread to finish", e);
                            }
                        }

                        if(getStatus() != GameStatus.FINILIZE) {
                            if (currentPlayerIndex == listener.getPlayers().size() - 1) {
                                setStatus(GameStatus.FINILIZE);
                                break;
                            }
                        }

                        currentPlayerIndex = passPlayerTurn(currentPlayerIndex);
                    }
                }

                if(getStatus() != GameStatus.FINILIZE)
                    setStatus(GameStatus.FINILIZE);
            }
            case FINILIZE -> {
                try {
                    gameLoopThreads = new ArrayList<>();
                    for (ClientControllerInterface c : listener.getPlayers()) {
                        Thread thread = new Thread(() -> {
                            try {
                                c.WhatDoIDoNow("OBJECTIVE-COUNT");
                            } catch (Exception e) {
                                System.err.println("An error occurred: " + e.getMessage());
                                Thread.currentThread().interrupt();
                            }
                        });
                        gameLoopThreads.add(thread);
                        thread.start();
                    }
                    for (Thread thread : gameLoopThreads) {
                        thread.join();
                    }
                } catch (InterruptedException e) {
                    throw new ObjectiveCountException("Error during Objective points count", e);
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
                            System.err.println("An error occurred: " + e.getMessage());
                            Thread.currentThread().interrupt();
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
                            System.err.println("An error occurred: " + e.getMessage());
                            Thread.currentThread().interrupt();
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
                    throw new FinilizeException("Error during game finalization", e);
                }

                setStatus(GameStatus.ENDED);
            }
        }
    }

    /**
     * This method is used to get the list of available colors that a player can choose for their pawn.
     * The colors are represented as a list of PawnColor enums.
     *
     * @return A list of available PawnColor enums. Each enum represents a color that a player can choose for their pawn.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public List<PawnColor> getAvailableColors() throws RemoteException{
        return this.availableColors;
    }

    /**
     * This method is used to remove a chosen color from the list of available colors.
     * Once a player chooses a color for their pawn, that color is no longer available for other players to choose.
     * This ensures that each player has a unique color for their pawn.
     *
     * @param color The color chosen by the player. This color is represented as a PawnColor enum.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {
        synchronized (availableColors) {
            if (availableColors.contains(color)) {
                availableColors.remove(color);
                List<Thread> threads = new ArrayList<>();
                for (ClientControllerInterface client : listener.getPlayers()) {
                    Thread thread = new Thread(() -> {
                        try {
                            if (client.getPawnColor() == null) {
                                client.displayAvailableColors(availableColors);
                            } else {
                                client.sendUpdateMessage("Waiting for all players to choose their pawn color...");
                            }
                        } catch (RemoteException e) {
                            throw new PawnColorRemovalException("Error during color removal from available colors", e);
                        }
                    });
                    threads.add(thread);
                    thread.start();
                }
            } else {
                System.out.println("Color not available: " + color);
            }
        }
    }

    /**
     * This method is used to set the nickname of the current player.
     * The nickname is used to identify the player in the game.
     *
     * @param currentPlayerNickname The nickname of the current player. This is a string that represents the player's chosen nickname.
     */
    private void setCurrentPlayerNickname(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    /**
     * This method is used to retrieve the current game model.
     * The game model represents the state of the game, including the game board, the players, and the cards.
     *
     * @return The current game model, represented as a PlayGround object.
     */
    public PlayGround getModel() {
        return model;
    }

    /**
     * This method is used to retrieve the ID of the game controller.
     * The ID is a unique identifier for each game controller instance.
     *
     * @return The ID of the game controller, represented as an integer.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public int getId() throws RemoteException {
        return id;
    }

    /**
     * Sets the current game model.
     * The game model represents the state of the game, including the game board, the players, and the cards.
     *
     * @param model The new game model, represented as a PlayGround object.
     */
    public void setModel(PlayGround model) {
        this.model = model;
    }

    /**
     * Retrieves a list of all players in the game.
     * Each player is represented as a Player object.
     *
     * @return An ArrayList of Player objects. Each Player object represents a player in the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        for(ClientControllerInterface c: listener.getPlayers()){
            players.add(c.getPlayer());
        }
        return players;
    }

    /**
     * This method is used to retrieve the number of players in the game.
     * The number of players is represented as an integer.
     *
     * @return The number of players in the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public int getNumPlayers() throws RemoteException {
        return numPlayers;
    }

    /**
     * Retrieves the GameListener of the game.
     * The GameListener is responsible for listening to game events.
     * If the GameListener is null, a new GameListener is created and assigned.
     *
     * @return The GameListener of the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public GameListener getListener() throws RemoteException {
        if (listener == null) {
            System.out.println("Listener is null");
            listener = new GameListener();
        }
        return listener;
    }

    /**
     * This method is used to add a new player to the game.
     * It synchronizes on the current instance of the GameController to ensure thread safety.
     *
     * @param client The client that represents the new player. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public synchronized void addPlayer(ClientControllerInterface client) throws RemoteException{
        listener.getPlayers().add(client);
    }

    /**
     * Retrieves the current status of the game.
     * The game status represents the current phase or state of the game.
     *
     * @return The current status of the game, represented as a GameStatus enum.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public GameStatus getStatus() throws RemoteException {
        return status;  //todo remove this
    }

    /**
     * Sets the current status of the game.
     * The game status represents the current phase or state of the game.
     *
     * @param status The new game status, represented as a GameStatus enum.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void setStatus(GameStatus status) throws RemoteException {
        this.status = status;
    }

    /**
     * Checks if all players have chosen a color for their pawn.
     * Iterates through all players and checks if they have chosen a pawn color.
     * If a player has not chosen a pawn color, the method returns false.
     * If all players have chosen a pawn color, the method returns true.
     *
     * @return A boolean value indicating whether all players have chosen a pawn color.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    private boolean allPlayersReady() throws RemoteException {
        for(ClientControllerInterface c: listener.getPlayers()){
            if(c.getPawnColor()==null)
                return false;
        }
        return true;
    }

    /**
     * Checks if the maximum number of players has been reached.
     * If the number of players in the game is equal to the maximum number of players, the game is ready to run.
     * If the maximum number of players has not been reached, the game waits for more players to join.
     * If all players are ready (i.e., they have chosen their pawn color), the game status is set to SETUP and the game starts running.
     * If not all players are ready, a message is sent to each player prompting them to choose a pawn color.
     * If the maximum number of players has not been reached, a message is sent to all players indicating that the game is waiting for more players to join.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void CheckMaxNumPlayerReached() throws RemoteException {
        if(listener.getPlayers().size()==getNumPlayers()){
            if(allPlayersReady()) {
                setStatus(GameStatus.SETUP);
                ArrayList<String> nicknames2 = new ArrayList<>();
                for (ClientControllerInterface client : listener.getPlayers()) {
                        nicknames2.add(client.getNickname());
                }
                nicknames = nicknames2;
                if (executor.isShutdown()) {
                    executor.execute(this);
                }
            }else {
                for (ClientControllerInterface c : listener.getPlayers()) {
                    if (c.getPawnColor() != null) {
                        c.sendUpdateMessage("Waiting for all players to choose their pawn color...");
                    }else{
                        c.sendUpdateMessage("Please choose a pawn color.");
                    }
                }
            }
        } else {
            listener.getPlayers().forEach(c -> {
                try {
                    c.sendUpdateMessage("Waiting for more players to join the game...");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    /**
     * Notifies all players that a new player has joined the game.
     * The method sends an update message to all players with the list of players in the game.
     * It then prompts the new player to choose a pawn color from the available colors.
     * If the maximum number of players has been reached, the method checks if the game is ready to run.
     *
     * @param newPlayer The new player that has joined the game. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public synchronized void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {
        System.out.println("Notifying players that a new player has joined the game...");
        CheckMaxNumPlayerReached();
    }

    /**
     * Passes the turn to the next player in the game.
     * This method increments the index of the last player, wrapping around to the start of the player list if necessary.
     * It then sets the current player in the game model to the new player, sends an update to all players notifying them of the new current player,
     * and starts a new turn timer for the new current player.
     *
     * @param currentPlayerIndex The index of the current player in the list of players.
     * @return The updated index of the current player after passing the turn.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    private int passPlayerTurn(int currentPlayerIndex) throws RemoteException {
        currentPlayerIndex = (currentPlayerIndex + 1) % listener.getPlayers().size();
        setCurrentPlayerNickname(listener.getPlayers().get(currentPlayerIndex).getNickname());
        return currentPlayerIndex;
    }

    /**
     * This method is responsible for extracting common objective cards from the deck and adding them to the common objectives.
     * The extraction process continues until there are 2 common objective cards.
     * After a card is extracted, it is removed from the objective card deck to ensure it is not drawn again.
     * The method is synchronized to prevent race conditions in multi-threaded environments.
     * The method also synchronizes on the objective card deck to prevent concurrent modifications.
     *
     * Note: The method assumes that the objective card deck contains at least 2 cards.
     *
     * @throws RuntimeException If the objective card deck does not contain enough cards.
     */
    public synchronized void extractCommonObjectiveCards() {
        synchronized (model.getObjectiveCardDeck()) {
            while (model.getCommonObjectivesCards().size() < 2) {
                ObjectiveCard c = (ObjectiveCard) model.getObjectiveCardDeck().drawCard();
                model.addCommonCard(c);
            }
        }
    }

    /**
     * Extracts common gold and resource cards from their respective decks and adds them to the common cards.
     * The method is synchronized on the model object to prevent race conditions in multi-threaded environments.
     * The extraction process continues until there are 2 common gold cards and 2 common resource cards.
     * After a card is extracted, it is removed from its respective deck to ensure it is not drawn again.
     * The extracted cards are added to the common cards in the game model.
     *
     * Note: The method assumes that the gold and resource card decks contain at least 2 cards each.
     */
    public void extractCommonPlaygroundCards() {
        synchronized (model) {
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
     * Extracts an initial card from the deck.
     * The method is synchronized on the initial card deck to prevent race conditions in multi-threaded environments.
     * The extracted card is removed from the deck to ensure it is not drawn again.
     *
     * @return The extracted initial card, represented as an InitialCard object.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public InitialCard extractInitialCard() throws RemoteException{
        synchronized (model.getInitialCardDeck()) {
            return (InitialCard) model.getInitialCardDeck().drawCard();
        }
    }

    /**
     * Retrieves the number of players who have chosen their objective.
     * The number of players who have chosen their objective is represented as an integer.
     *
     * @return The number of players who have chosen their objective.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public int getPlayersWhoChoseObjective() throws RemoteException{
        return playersWhoChoseObjective;
    }

    /**
     * Increments the number of players who have chosen their objective.
     * This method is used when a player chooses their objective.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void incrementPlayersWhoChoseObjective() throws RemoteException{
        playersWhoChoseObjective++;
    }

    /**
     * Initializes the game by extracting common playground cards and common objective cards.
     * This method is synchronized to prevent race conditions in multi-threaded environments.
     * It first calls the method to extract common playground cards which includes gold and resource cards.
     * Then, it calls the method to extract common objective cards.
     * These extracted cards are used as the common cards available to all players during the game.
     *
     * Note: This method assumes that the decks for playground and objective cards are properly initialized and contain enough cards.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    //todo handle exceptions in a better way(?)
    public synchronized void initializeGame() throws RemoteException {
        extractCommonPlaygroundCards();
        extractCommonObjectiveCards();
    }

    /**
     * Determines the winner(s) of the game based on the highest score.
     * This method iterates through all players and compares their scores.
     * If a player's score is higher than the current highest score, the player is added to the list of winners and the highest score is updated.
     * If a player's score is equal to the current highest score, the player is added to the list of winners.
     * The method returns a list of winners, which can contain multiple players in case of a tie.
     *
     * @return An ArrayList of ClientControllerInterface objects representing the winner(s) of the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
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

    /**
     * Generates the final ranking of players based on their scores and nicknames.
     * This method first creates a new list of players from the game listener.
     * Then, it sorts the players in alphabetical order based on their nicknames.
     * After that, it sorts the players in descending order based on their scores.
     * If a RemoteException occurs during the comparison of nicknames or scores, it is wrapped in a RuntimeException and rethrown.
     * Finally, it constructs a string representing the final ranking of players, sorted by score and then alphabetically by their nicknames.
     *
     * @return A string representing the final ranking of players, sorted by score and then alphabetically by their nicknames.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public String finalRanking() throws RemoteException {
        List<ClientControllerInterface> players = new ArrayList<>(listener.getPlayers());

        players.sort((player1, player2) -> {
            try {
                return player1.getNickname().compareTo(player2.getNickname());
            } catch (RemoteException e) {
                throw new PlayerRankingException("Error while comparing player nicknames", e);
            }
        });

        players.sort((player1, player2) -> {
            try {
                return Integer.compare(player2.getPlayer().getScore(), player1.getPlayer().getScore());
            } catch (RemoteException e) {
                throw new PlayerRankingException("Error while comparing player nicknames", e);
            }
        });

        StringBuilder scores = new StringBuilder();
        for (ClientControllerInterface player : players) {
            scores.append(player.getNickname()).append(" : ").append(player.getPlayer().getScore()).append("\n");
        }

        return scores.toString();
    }

    /**
     * This method is responsible for extracting the initial hand cards for a player.
     * It is synchronized to prevent race conditions in multi-threaded environments.
     * The method extracts one card each from the resource and gold card decks and adds them to the player's hand.
     * The extracted cards are removed from their respective decks to ensure they are not drawn again.
     * The player's hand, which contains the extracted cards, is then returned.
     *
     * @return An ArrayList of PlayCard objects representing the player's hand.
     * @throws RemoteException If a remote or network communication error occurs.
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
     * This method is responsible for providing the personal objective cards for a player.
     * It is synchronized to prevent race conditions in multi-threaded environments.
     * The method extracts two objective cards from the objective card deck and adds them to a list.
     * The extracted cards are removed from the deck to ensure they are not drawn again.
     * The list of objective cards, which are the options available to the player, is then returned.
     *
     * @return An ArrayList of ObjectiveCard objects representing the personal objective options for the player.
     * @throws RemoteException If a remote or network communication error occurs.
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

    /**
     * Checks if a move is valid based on the current state of the play area and the card to be placed.
     * This method checks several conditions to determine if a move is valid:
     * - The row and column exist in the play area.
     * - The spot in the play area is not already occupied by another card.
     * - The player is not trying to cover two corners of the same card.
     * - The player is not trying to cover a hidden corner of a neighboring card.
     * If all these conditions are met, the method returns true, indicating that the move is valid.
     * If any of these conditions are not met, the method returns false, indicating that the move is not valid.
     *
     * @param playArea The current state of the play area. This is an instance of PlayArea.
     * @param row The row in the play area where the player wants to place the card.
     * @param column The column in the play area where the player wants to place the card.
     * @param newCard The card that the player wants to place in the play area. This is an instance of SideOfCard.
     * @return A boolean value indicating whether the move is valid. True if the move is valid, false otherwise.
     * @throws RemoteException If a remote or network communication error occurs.
     */
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

    /**
     * Checks if the player is trying to cover two corners of the same card while placing a card.
     * This method iterates through the adjacent positions of the chosen spot in the play area.
     * If any of these positions is occupied by a card, the method returns false, indicating that the player is trying to cover two corners of the same card.
     * If none of these positions is occupied by a card, the method returns true, indicating that the player is not trying to cover two corners of the same card.
     *
     * @param playArea The current state of the play area. This is an instance of PlayArea.
     * @param row The row in the play area where the player wants to place the card.
     * @param column The column in the play area where the player wants to place the card.
     * @return A boolean value indicating whether the player is trying to cover two corners of the same card. True if the player is not trying to cover two corners of the same card, false otherwise.
     */
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

    /**
     * Shuts down the game by disconnecting all players and stopping all tasks.
     * First, it disconnects all players and clears the list of players.
     * Then, it attempts to gracefully shut down the executor service by stopping all tasks.
     * If tasks do not finish within 60 seconds, it forces a shutdown.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void shutdown() throws RemoteException {

        listener.getPlayers().forEach(c -> {
            try {
                c.disconnect();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        listener.getPlayers().clear();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
    /**
     * Handles the disconnection of a client.
     * First, it removes the disconnected client from the list of players.
     * Then, it updates all remaining players about the disconnection.
     * Finally, it sets the game status to FINILIZE and interrupts all game loop threads.
     *
     * @param client The client that has disconnected. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void clientDisconnected(ClientControllerInterface client) {
        try {
            boolean removeSucceded = listener.getPlayers().remove(client);
            listener.getPlayers().forEach(c -> {
                try {
                    c.sendUpdateMessage("Client disconnected... Ending game");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
            setStatus(GameStatus.FINILIZE);
            for (Thread thread : gameLoopThreads) {
                thread.interrupt();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves the nicknames of all players in the game.
     *
     * @return An ArrayList of strings representing the nicknames of all players in the game.
     */
    public ArrayList<String> getNicknames() {
        return nicknames;
    }
}
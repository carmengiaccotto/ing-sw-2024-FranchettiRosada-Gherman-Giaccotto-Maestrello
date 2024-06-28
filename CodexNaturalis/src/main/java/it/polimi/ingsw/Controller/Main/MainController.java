package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Exceptions.GameJoinException;
import it.polimi.ingsw.Exceptions.GameStatusException;
import it.polimi.ingsw.Model.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainController class is responsible for managing the main game logic.
 * It maintains a list of connected clients, a list of running games, and a thread pool for multi-game feature.
 * It also ensures that each client has a unique nickname.
 */
public class MainController extends UnicastRemoteObject implements MainControllerInterface {
    /**ArrayList of Connected Clients*/
    private final ArrayList<ClientControllerInterface> clients;

    /**HashSet of nicknames that have already been chosen by other clients*/
    private final HashSet<String> nicknames = new HashSet<>();

    /**ArrayList of Games that are currently running on the server*/
    private final ArrayList<GameController> runningGames;

    /**Thread pool for Multi-Game advanced feature*/
    private final transient ExecutorService executor;

    private static MainController instance = null;

    /**
     * MainController constructor initializes the list of clients, running games and the executor service.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public MainController() throws RemoteException {
        super();
        this.clients = new ArrayList<ClientControllerInterface>();
        this.runningGames=new ArrayList<>();
        this.executor = Executors.newCachedThreadPool();
    }

    /**
     * Singleton pattern is used to ensure that only one instance of MainController exists.
     * @return The single instance of MainController.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public static MainController getInstance() throws RemoteException {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     * This method returns an association between the Game ID and the number of players that can join that game.
     * It first creates an empty ArrayList of Pair objects, where each Pair contains an Integer representing the Game ID and an Integer representing the number of players.
     * Then, it iterates through each game that is currently waiting for players to join.
     * For each game, it creates a new Pair with the game's ID and the number of players, and adds this Pair to the ArrayList.
     * Finally, it returns the ArrayList of Pairs.
     *
     * @return An ArrayList of Pair objects, where each Pair contains an Integer representing the Game ID and an Integer representing the number of players that can join that game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public synchronized ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        ArrayList<Pair<Integer, Integer>> numPlayers = new ArrayList<>();
        for(GameControllerInterface game:gamesInWaiting()){
            Pair<Integer, Integer> pair= new Pair<>(game.getId(),game.getNumPlayers());
            numPlayers.add(pair);
        }
        return numPlayers;
    }

    /**
     * This method is used to connect a client to the server.
     * It first prints a message to the console indicating that a new client has connected.
     * Then, it adds the client to the list of connected clients.
     *
     * @param client The client that is connecting to the server. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        System.out.println("New Client Connected");
        clients.add(client);
    }

    /**
     * Checks if the provided nickname is unique among the connected clients.
     * <p>
     * This method is used when a client is trying to choose a nickname. It checks if the nickname has already been chosen by another client.
     * If the nickname is already taken, the method returns false, indicating that the player should be sent back to the lobby to choose a new one.
     * If the nickname is not taken, the method returns true, indicating that the client's nickname can be set to the chosen one.
     *
     * @param name The nickname that the client wants to choose.
     * @return A boolean value indicating whether the nickname is unique. Returns true if the nickname is unique, false otherwise.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public synchronized boolean checkUniqueNickName(String name) throws RemoteException {
        return !nicknames.contains(name);
    }

    /**
     * Adds a new nickname to the list of already used nicknames.
     * <p>
     * This method is used when a player has chosen a nickname and it has been confirmed to be unique.
     * The chosen nickname is added to the list of nicknames that have already been used, to ensure that future players cannot choose the same nickname.
     *
     * @param name The nickname that the player has chosen. This is a string.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public synchronized void addNickname(String name, ClientControllerInterface client) throws RemoteException  {
        nicknames.add(name);
        client.setNickname(name);
    }

    /**
     * Allows a client to join an existing game.
     * <p>
     * This method is used when a client wants to join a game that has been created by another client.
     * The client had previously seen a list of the available games and chose one.
     * The method first initializes a GameController object to null.
     * Then, it iterates through each running game.
     * If the ID of a game matches the provided GameID, the game is assigned to the GameController object and the client's game is set to this game.
     * If the GameController object is not null after the iteration, the client is added to the game and the game state is saved.
     *
     * @param client The client that wants to join the game. This is an instance of ClientControllerInterface.
     * @param GameID The ID of the game that the client wants to join.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public void joinGame(ClientControllerInterface client, int GameID) throws RemoteException{
        GameController gameToJoin=null;
        synchronized (new Object()) {
            for (GameController game : runningGames) {
                try {
                    if (game.getId() == GameID) {
                        gameToJoin = game;
                        client.setGame(gameToJoin);
                    }

                } catch (RemoteException e) {
                    throw new GameJoinException("Error while setting game to join", e);
                }
            }

            try {
                if (gameToJoin != null) {
                    gameToJoin.addPlayer(client);
                }
            } catch (RemoteException e) {
                throw new GameJoinException("Error while adding player to game", e);
            }
        }
    }


    /**
     * Provides a list of games that a client can join. Only games that are still waiting for the required number of players are displayed.
     * <p>
     * This method first initializes a HashMap to store the available games.
     * It then retrieves a list of games that are still waiting for players to join.
     * For each game in this list, it creates an ArrayList of the nicknames of the players in the game.
     * If a player has a null nickname, a warning is printed to the console.
     * If a game has a null listener or a null players list, a warning is printed to the console.
     * The game's ID and the list of player nicknames are then added to the HashMap.
     * Finally, the HashMap of available games is returned.
     *
     * @return A Map of available games, where the key is the game's ID and the value is an ArrayList of the nicknames of the players in the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public Map<Integer, ArrayList<String>>  DisplayAvailableGames() throws RemoteException {
        Map<Integer, ArrayList<String>> availableGames;
        synchronized (new Object()) {
            availableGames = new HashMap<>();
            ArrayList<GameControllerInterface> games = gamesInWaiting();
            for (GameControllerInterface game : games) {
                ArrayList<String> players = new ArrayList<>();
                if (game.getListener() != null && game.getListener().getPlayers() != null) {
                    for (ClientControllerInterface player : game.getListener().getPlayers()) {
                        if (player.getNickname() != null) {
                            players.add(player.getNickname());
                        } else {
                            System.out.println("Warning: Player has null nickname");
                        }
                    }
                } else {
                    System.out.println("Warning: Game has null listener or null players list");
                }
                availableGames.put(game.getId(), players);
            }
            return availableGames;
        }
    }


    /**
     * Creates a new game, adds it to the list of running games, and adds the client that created it to the list of players of the game.
     * It also notifies the game that a new player has joined.
     * <p>
     * The method first creates a new GameController object with the specified number of players and the size of the running games list as the game ID.
     * It then submits the new game to the executor service for execution.
     * The new game is added to the list of running games.
     * The client that created the game is added to the list of players of the new game.
     * The client's game is set to the new game.
     * The game state is saved.
     * Finally, the new game is returned.
     *
     * @param client The client that created the game. This is an instance of ClientControllerInterface.
     * @param n The number of players in the new game.
     * @return The new game that was created. This is an instance of GameControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public  synchronized GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException {
        GameController newGame = new GameController(n, runningGames.size());
        executor.submit(newGame);
        runningGames.add(newGame);
        newGame.addPlayer(client);
        client.setGame(newGame);
        return newGame;
    }


    /**
     * Notifies a game that a new player has joined.
     * <p>
     * This method is used when a player has successfully joined a game.
     * It calls the NotifyNewPlayerJoined method of the game, passing the client that joined as an argument.
     * This allows the game to update its state to reflect the new player.
     *
     * @param game The game that the player has joined. This is an instance of GameControllerInterface.
     * @param client The player that has joined the game. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    @Override
    public synchronized void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        game.NotifyNewPlayerJoined(client);
    }

    /**
     * Returns a list of games that are currently in the waiting status, i.e., waiting for players to join.
     * <p>
     * This method first initializes an empty ArrayList to store the games in waiting.
     * It then iterates through each game in the list of running games.
     * If a game's status is WAITING, it is added to the ArrayList of games in waiting.
     * If a RemoteException is thrown during the retrieval of a game's status, it is wrapped in a RuntimeException and rethrown.
     * Finally, the ArrayList of games in waiting is returned.
     *
     * @return An ArrayList of GameControllerInterface objects representing the games that are currently in the waiting status.
     */
    private synchronized ArrayList<GameControllerInterface> gamesInWaiting() throws RemoteException{
        ArrayList<GameControllerInterface> games = new ArrayList<>();
        for(GameController game: runningGames){
            try {
                if(game.getStatus().equals(GameStatus.WAITING))
                    games.add(game);
            } catch (RemoteException e) {
                throw new GameStatusException("Error while getting game status", e);
            }
        }
        return games;
    }

    public void disconnectPlayer(ClientControllerInterface player) throws RemoteException {
        clients.remove(player);


        for (Iterator<GameController> iterator = runningGames.iterator(); iterator.hasNext();) {
            GameController game = iterator.next();

            if (game.getListener().getPlayers().stream().anyMatch(p -> {
                try {
                    return p.getNickname().equals(player.getNickname());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return false;
                }
            })) {
                for (String nickname : game.getNicknames()) {
                    nicknames.remove(nickname);
                }
                iterator.remove();
            }
        }
    }

}

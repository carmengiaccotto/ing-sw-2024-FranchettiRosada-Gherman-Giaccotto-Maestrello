package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Connection.ExecutorBuffer;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Enumerations.GameStatus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController extends UnicastRemoteObject implements MainControllerInterface {
    /**ArrayList of Connected Clients*/
    private final ArrayList<ClientControllerInterface> clients;

    /**HashSet of nicknames that have already been chosen by other clients*/
    private final HashSet<String> nicknames = new HashSet<>();

    /**ArrayList of Games that are currently running on the server*/
    private final ArrayList<GameController> runningGames;

    /**Thread pool for Multi-Game advanced feature*/
    private transient ExecutorService executor;
    /** Thread pool for async communication and task management*/
    private final ExecutorBuffer executorBuffer = new ExecutorBuffer();

    private static MainController instance = null;

    public MainController() throws RemoteException {
        super();
        this.clients = new ArrayList<ClientControllerInterface>();
        this.runningGames=new ArrayList<>();
        this.executor = Executors.newCachedThreadPool();
    }
    public static MainController getInstance() throws RemoteException {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    /**Method that adds the Client to the list of connected Client, and adds it to the lobby
     * @param client  that connected*/
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        System.out.println("New Client Connected");
        clients.add(client);
    }


    /**Method that checks if the Client is trying to choose a nickname that has already been chosen by
     * another client. If the nickname is already taken, the player is sent back to the lobby, in
     * order to choose a new one, else the client's nickname is set to the chosen one
     *
     * @param name nickname that the client wants to choose.
     * */
    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException {
        return !nicknames.contains(name);
    }



    /**
     * Method that adds the nickname to the list of already used nicknames
     *
     * @param name that another player just chose
     * */
    @Override
    public void addNickname(String name) throws RemoteException  {
        nicknames.add(name);
    }


    /**Method that allows the Client to join a game already created by another Client.
     * The Client had previously seen a list of the available games, and chose one. Now is
     * being added to the chosen game
     * @param client to be added to the new game
     * @param GameID id of the game the client chose
     * Once the player has been added to the game, the Game gets notified*/
    @Override
    public GameControllerInterface joinGame(ClientControllerInterface client, int GameID) throws RemoteException {
            GameController gameToJoin=null;
            for(GameController game: runningGames) {
                try {
                    if(game.getId()==GameID)
                        gameToJoin=game;

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                if (gameToJoin != null) {
                    gameToJoin.addPlayer(client);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        return gameToJoin;
    }


    /**
     * Method that gives the Client a list of game it can join. Only games that are waiting to reach the
     * required number of player are displayed
     *
     * @return
     */
    @Override
    public ArrayList<GameControllerInterface> DisplayAvailableGames() throws RemoteException {
        synchronized (new Object()) {
            ArrayList<GameControllerInterface> availableGames = new ArrayList<>();
            for (GameController runningGame : runningGames) {
                try {
                    if (runningGame.getStatus().equals(GameStatus.WAITING)) {
                        availableGames.add(runningGame);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return availableGames;
        }
    }


    /**
     * method that creates a new game, adds it to the list of running games, and adds the Client
     * that created it to the list of players of said game. Notifies the game that a new player joined
     *
     * @param client that created the game
     * @return newGame that was created
     */
    @Override
    public  synchronized GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException {
        GameController newGame = new GameController(n, runningGames.size()+1);
        executor.submit(newGame);
        runningGames.add(newGame);
        newGame.addPlayer(client);
        return newGame;
    }


    /**Method that notifies the game that a new player has joined.
     *@param game to be notified
     *@param client that joined the game*/
    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        game.NotifyNewPlayerJoined(client);
    }


    public HashSet<String> getNicknames() throws RemoteException{
        return nicknames;
    }
}

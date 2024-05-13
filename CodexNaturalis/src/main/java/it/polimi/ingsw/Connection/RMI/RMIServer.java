//This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Connection.VirtualClient;
import it.polimi.ingsw.Connection.VirtualServer;
import it.polimi.ingsw.Model.Enumerations.GameStatus;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The RMIServer class extends the UnicastRemoteObject and implements the GameInterface interface.
 * It represents a server in the RMI connection.
 */
public class RMIServer extends UnicastRemoteObject implements VirtualServer {

    // The singleton instance of the RMIServer
    private static RMIServer serverObject = null;

    //Thread pool for Multi-Game advanced feature
    private ExecutorService executor;


    // The registry for the RMI connection
    private static Registry registry = null;

    private ArrayList<VirtualClient> clients;

    private ArrayList<GameController> runningGames;

    private final Object gameLock = new Object();

    /**
     * The constructor for the RMIServer class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIServer() throws RemoteException {
        super(0);
        clients=new ArrayList<VirtualClient>();
        runningGames=new ArrayList<GameController>();
        executor= Executors.newCachedThreadPool();

    }

    /**
     * Binds the server to the RMI registry.
     * @return RMIServer the singleton instance of the RMIServer
     */
    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(6322);
            getRegistry().rebind("CodexNaturalis", serverObject);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    /**
     * Returns the singleton instance of the RMIServer.
     * @return RMIServer the singleton instance of the RMIServer
     */
    public  static RMIServer getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * Returns the registry associated with the RMI Server.
     * @return Registry the registry associated with the RMI Server
     * @throws RemoteException if the remote invocation fails
     */
    public  static Registry getRegistry() throws RemoteException {
        return registry;
    }



    @Override
    public void connect(VirtualClient client) throws RemoteException {
        System.out.println("New Client Connected");
        clients.add(client);
        addClientToLobby(client);


    }



    @Override
    public  void addClientToLobby(VirtualClient client) throws RemoteException {
        client.setNickname();
        client.JoinOrCreateGame();
        System.out.println("se arrivi fino a qui allora non capisco");

    }

    @Override
    public  boolean checkUniqueNickName(String name) throws RemoteException {
        if (clients.size()>1) {
            for (VirtualClient c : clients) {
                if (name.equals(c.getNickname())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public synchronized void joinGame(VirtualClient client, int GameID) throws RemoteException {
        GameController gameToJoin=null;
        for(GameController game: runningGames)
            if(game.getId()==GameID)
                gameToJoin=game;

        gameToJoin.addPlayer(client);
        NotifyGamePlayerJoined(gameToJoin, client);
    }

    public ArrayList<GameController> DisplayAvailableGames() throws RemoteException{
        ArrayList<GameController> availableGames=new ArrayList<>();
        for(int i=0; i< runningGames.size(); i++) {
            if (runningGames.get(i).getStatus().equals(GameStatus.WAITING)) {
                availableGames.add(runningGames.get(i));
            }
        }
        return availableGames;
    }

    public void createGameSynchronized(VirtualClient client) throws RemoteException {
        Object gameLock= new Object();
        synchronized (gameLock) {
            createGame(client);
            client.getView().waitingForPlayers();
        }
    }

    public void createGame(VirtualClient client) throws RemoteException {

        GameController newGame = new GameController(runningGames.size()+1,client.newGameSetUp());
        executor.submit(newGame);
        runningGames.add(newGame);
        newGame.addPlayer(client);
        NotifyGamePlayerJoined(newGame, client);
    }

    public synchronized void NotifyGamePlayerJoined(GameController game, VirtualClient client) throws RemoteException {
        System.out.println("ci siamo");
        game.NotifyNewPlayerJoined(client);
    }

    public GameController getGame(int id){
        for (GameController game: runningGames)
            if(game.getId()==id)
                return game;
        throw  new IllegalArgumentException("no game found with this id");
    }
}





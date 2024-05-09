//This package contains the classes related to the RMI (Remote Method Invocation) connection.

package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMIServer class extends the UnicastRemoteObject and implements the GameInterface interface.
 * It represents a server in the RMI connection.
 */
public class RMIServer extends UnicastRemoteObject implements GameInterface {

    // The singleton instance of the RMIServer
    private static RMIServer serverObject = null;

    // The game interface for this server
    private final GameInterface gameInt = null;

    // The registry for the RMI connection
    private static Registry registry = null;

    /**
     * The constructor for the RMIServer class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIServer() throws RemoteException {
        super(0);
        GameController game = GameControllerInterface.getInstance();
    }

    /**
     * Binds the server to the RMI registry.
     * @return RMIServer the singleton instance of the RMIServer
     */
    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(6321);
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
    public synchronized static RMIServer getInstance() {
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
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Creates a new game with the given nickname.
     * @param nickname the nickname of the player who wants to create a new game
     * @return GameControllerInterface the controller of the created game
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public GameControllerInterface createGame(String nickname) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.createGame(nickname);
        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){
            }
            System.out.println("[RMI] " + nickname + " created a new game");
        }
        return game;
    }

    /**
     * Allows a player to join an existing game using their nickname.
     * @param nickname the nickname of the player who wants to join an existing game
     * @return GameControllerInterface the controller of the joined game
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public GameControllerInterface joinExistingGame(String nickname) throws RemoteException {
         GameControllerInterface game = serverObject.gameInt.joinExistingGame(nickname);
        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){
            }

            System.out.println("[RMI] " + nickname + " joined a game");
        }
        return game;

    }

    /**
     * Allows a player to reconnect to a game using their nickname.
     * @param nickname the nickname of the player who wants to reconnect
     * @return GameControllerInterface the controller of the reconnected game
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public GameControllerInterface reconnectPlayer(String nickname) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.reconnectPlayer(nickname);

        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){

            }
            System.out.println("[RMI] " + nickname + " reconnected to game");
        }
        return game;

    }

    /**
     * Allows a player to disconnect from a game using their nickname.
     * @param nickname the nickname of the player who wants to disconnect
     * @return GameControllerInterface the controller of the disconnected game
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public GameControllerInterface disconnectPlayer(String nickname) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.disconnectPlayer(nickname);

        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){

            }
            System.out.println("[RMI] " + nickname + "disconnected");
        }
        return game;

    }

}

// This package contains the classes related to the RMI (Remote Method Invocation) connection.

package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The GameInterface interface extends the Remote interface and provides the methods that a game can call remotely.
 */
public interface GameInterface extends Remote {

    /**
     * Creates a new game with the given nickname.
     * @param nickname the nickname of the player who wants to create a new game
     * @return GameControllerInterface the controller of the created game
     * @throws RemoteException if the remote invocation fails
     */
    GameControllerInterface createGame(String nickname) throws RemoteException;

    /**
     * Allows a player to join an existing game using their nickname.
     * @param nickname the nickname of the player who wants to join an existing game
     * @return GameControllerInterface the controller of the joined game
     * @throws RemoteException if the remote invocation fails
     */
    GameControllerInterface joinExistingGame(String nickname) throws RemoteException;

    /**
     * Allows a player to reconnect to a game using their nickname.
     * @param nickname the nickname of the player who wants to reconnect
     * @return GameControllerInterface the controller of the reconnected game
     * @throws RemoteException if the remote invocation fails
     */
    GameControllerInterface reconnectPlayer(String nickname) throws RemoteException;

    /**
     * Allows a player to disconnect from a game using their nickname.
     * @param nickname the nickname of the player who wants to disconnect
     * @return GameControllerInterface the controller of the disconnected game
     * @throws RemoteException if the remote invocation fails
     */
    GameControllerInterface disconnectPlayer(String nickname) throws RemoteException;

}

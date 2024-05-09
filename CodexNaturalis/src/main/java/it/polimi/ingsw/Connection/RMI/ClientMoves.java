// This package contains the classes related to the RMI (Remote Method Invocation) connection.
package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

// Importing the Message class from the model
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

// Importing the necessary RMI classes
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The ClientMoves interface extends the Remote interface and provides the methods that a client can call remotely.
 */
public interface ClientMoves extends Remote {

    /**
     * Connects the client to the server.
     * @throws RemoteException if the remote invocation fails
     */
    void connect() throws RemoteException;

    /**
     * Sends a message from the client to the server.
     * @param m the message to be sent
     * @throws RemoteException if the remote invocation fails
     */
    void sendMessage(Message m) throws RemoteException;

    /**
     * Allows a client to leave the server using their nickname.
     * @param nickname the nickname of the client who wants to leave
     * @throws RemoteException if the remote invocation fails
     */
    void leave(String nickname) throws RemoteException;

    /**
     * Disconnects a client from the server using their nickname.
     * @param nickname the nickname of the client who wants to disconnect
     * @throws RemoteException if the remote invocation fails
     */
    void disconnect(String nickname) throws RemoteException;

}

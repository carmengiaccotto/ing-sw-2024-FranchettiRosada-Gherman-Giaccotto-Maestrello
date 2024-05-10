
// This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes

import it.polimi.ingsw.controller.GameControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMIClient class extends the UnicastRemoteObject and implements the ClientMoves interface.
 * It represents a client in the RMI connection.
 */
public class RMIClient extends UnicastRemoteObject implements ClientMoves {

    // The server that this client is connected to
    GameInterface server;

    // The game controller for this client
    GameControllerInterface gameController = null;

    // The registry for the RMI connection
    private Registry registry;

    // The nickname of this client
    private String nickname;

    /**
     * The constructor for the RMIClient class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIClient() throws RemoteException {
    }

    /**
     * Connects the client to the server.
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public void connect() throws RemoteException {
        // Implementation of the connect method
    }

//    /**
//     * Sends a message from the client to the server.
//     * @param m the message to be sent
//     * @throws RemoteException if the remote invocation fails
//     */
//    @Override
//    public void sendMessage(Message m) throws RemoteException {
//        // Implementation of the sendMessage method
//    }

    /**
     * Allows a client to leave the server using their nickname.
     * @param nickname the nickname of the client who wants to leave
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public void leave(String nickname) throws RemoteException {
        // Implementation of the leave method
    }

    /**
     * Disconnects a client from the server using their nickname.
     * @param nickname the nickname of the client who wants to disconnect
     * @throws RemoteException if the remote invocation fails
     */
    @Override
    public void disconnect(String nickname) throws RemoteException {
        // Implementation of the disconnect method
    }
}

//This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainController;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMIServer class extends the UnicastRemoteObject and implements the GameInterface interface.
 * It represents a server in the RMI connection.
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    // The singleton instance of the RMIServer
    private static RMIServer serverObject = null;

    // The registry for the RMI connection
    private static Registry registry = null;

    //Interface that contains the methods used in this class
    private final MainControllerInterface handler;


    /**
     * The constructor for the RMIServer class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIServer() throws RemoteException {
        super(0);
        handler=new MainController(); //TODO: change to MainControllerInterface

    }

    /**
     * Binds the server to the RMI registry.
     * @return RMIServer the singleton instance of the RMIServer
     */
    public static RMIServer bind() {
        try {
            serverObject = RMIServer.getInstance();
            registry = LocateRegistry.createRegistry(8323);
            getRegistry().rebind("CodexNaturalis", serverObject);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            System.out.println("Exception when binding RMIServer: " + e);
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
                System.out.println("Exception when creating RMIServer: " + e);
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


    /**Method that adds the player to a lobby. Uses MainController method
     * @param client  that just connected*/
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        handler.connect(client);

    }


    /**Method that adds the Client to a lobby. Uses MainController method
     * @param client  new client in the lobby */
    @Override
    public  void addClientToLobby(ClientControllerInterface client) throws RemoteException {
        handler.addClientToLobby(client);

    }

    /**Method that checks if the chosen Nickname is already taken. Uses MainController method
     * @param client that is logging in
     * @param name inserted nickname*/
    @Override
    public boolean checkUniqueNickName(String name, ClientControllerInterface client) throws RemoteException {
         return handler.checkUniqueNickName(name, client);
    }


    /**method that adds the player to the chosen game and notifies the game. Uses MainController method
     * @param client that is joining a new game
     * @param GameID chosen game to join*/
    @Override
    public synchronized void joinGame(ClientControllerInterface client, int GameID) throws RemoteException {
        handler.joinGame(client,GameID);
    }

    /**Method that gives the Client a list of games it can join. Uses MainController method
     * @param client that requests to see the available games*/
    @Override
    public void DisplayAvailableGames(ClientControllerInterface client) throws RemoteException{
       handler.DisplayAvailableGames(client);

    }

    /**Method that creates a new game when requested by a player. Uses MainControllerMethod
     * @param client that wants to create a new game*/
    @Override
    public void createGame(ClientControllerInterface client, int n) throws RemoteException {
        handler.createGame(client, n);
    }

    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        handler.NotifyGamePlayerJoined(game,client);
    }
}





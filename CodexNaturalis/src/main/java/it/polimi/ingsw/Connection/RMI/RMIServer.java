/**
 * This package contains the classes related to the RMI (Remote Method Invocation) connection.
 */

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainController;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

/**
 * The RMIServer class extends the UnicastRemoteObject and implements the MainControllerInterface.
 * It represents a server in the RMI connection.
 * This class is responsible for managing the RMI connection and the game logic.
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * This is a singleton instance of the RMIServer class.
     * It ensures that only one instance of the RMIServer class is created throughout the execution of the program.
     * This instance is used to manage the RMI connection and the game logic.
     */
    private static RMIServer serverObject = null;

    /**
     * This is the registry for the RMI connection.
     * The registry is used to bind the name of the remote object to the remote object itself.
     * The client can then access the remote object using the name in the registry.
     */
    private static Registry registry = null;

    /**
     * This is an interface that contains the methods used in this class.
     * The handler is an instance of MainControllerInterface which is used to manage the game logic.
     */
    private MainControllerInterface handler;

    /**
     * The default constructor for the RMIServer class.
     * It initializes the handler with a new MainController.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIServer() throws RemoteException {
        super(0);
        handler=new MainController();
    }

    /**
     * The constructor for the RMIServer class with a MainControllerInterface parameter.
     * It initializes the handler with the provided MainControllerInterface.
     * @param mainController The MainControllerInterface to be used as the handler.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIServer(MainControllerInterface mainController) throws RemoteException {
        super(0);
        this.handler = mainController;
    }

    /**
     * Binds the server to the RMI registry.
     * @param localAddress The local address to bind the server to.
     * @return RMIServer the singleton instance of the RMIServer
     */
    public static RMIServer bind(String localAddress) {
        try {
            System.setProperty("java.rmi.server.hostname", localAddress);
            serverObject = RMIServer.getInstance();
            registry = LocateRegistry.createRegistry(8359);
            getRegistry().rebind("rmi://" + localAddress + ":8344/CodexNaturalis", serverObject);
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
     * If the instance does not exist, it creates a new one.
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
     * Disconnects a player from the game.
     * This method is a part of the MainControllerInterface.
     * It delegates the disconnection process to the handler.
     *
     * @param player The player to be disconnected.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public void disconnectPlayer(ClientControllerInterface player) throws RemoteException {
        handler.disconnectPlayer(player);
    }

    /**
     * Returns the registry associated with the RMI Server.
     * @return Registry the registry associated with the RMI Server
     * @throws RemoteException if the remote invocation fails
     */
    public  static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Returns the number of required players for the game.
     * This method is a part of the MainControllerInterface.
     * @return ArrayList<Pair<Integer, Integer>> The number of required players.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        return handler.numRequiredPlayers();
    }

    /**
     * Connects a client to the server.
     * This method is a part of the MainControllerInterface.
     * @param client The client that just connected.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        handler.connect(client);

    }

    /**
     * Checks if the chosen nickname is unique.
     * This method is a part of the MainControllerInterface.
     * @param name The chosen nickname.
     * @return boolean True if the nickname is unique, false otherwise.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    @Override
    public boolean checkUniqueNickName(String name) throws IOException, ClassNotFoundException {
         return handler.checkUniqueNickName(name);
    }

    /**
     * Adds a player to a chosen game and notifies the game.
     * This method is a part of the MainControllerInterface.
     * @param client The client that is joining a new game.
     * @param GameID The ID of the chosen game to join.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public synchronized void joinGame(ClientControllerInterface client, int GameID) throws RemoteException {
        handler.joinGame(client,GameID);
    }

    /**
     * Returns a list of games that the client can join.
     * This method is a part of the MainControllerInterface.
     * @return Map A list of available games.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public Map DisplayAvailableGames() throws RemoteException{
       return handler.DisplayAvailableGames();

    }

    /**
     * Creates a new game when requested by a player.
     * This method is a part of the MainControllerInterface.
     * @param client The client that wants to create a new game.
     * @param n The number of players in the new game.
     * @return GameControllerInterface The interface of the created game.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException {
        return handler.createGame(client, n);
    }

    /**
     * Notifies the game when a player has joined.
     * This method is a part of the MainControllerInterface.
     * @param game The game that the player has joined.
     * @param client The client that has joined the game.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        handler.NotifyGamePlayerJoined(game,client);
    }

    /**
     * Adds a nickname to the server.
     * This method is a part of the MainControllerInterface.
     * @param name The nickname to be added.
     * @throws RemoteException If the remote invocation fails.
     */
    @Override
    public void addNickname(String name, ClientControllerInterface client) throws RemoteException {
        handler.addNickname(name, client);
    }

    /**
     * Sets the handler for the RMIServer.
     * The handler is an instance of MainControllerInterface which is used to manage the game logic.
     * @param handler The MainControllerInterface instance to be used as the handler.
     */
    public void setHandler(MainControllerInterface handler){
        this.handler = handler;
    }
}





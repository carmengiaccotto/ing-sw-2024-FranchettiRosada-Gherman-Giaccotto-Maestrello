package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Interface for the main controller of the game.
 *
 * This interface defines the methods that must be implemented by the main controller of the game.
 * The main controller is responsible for managing the game, including creating games, adding players to games, and notifying games of new players.
 * It extends the Remote and Serializable interfaces, allowing it to be used in a distributed system and to have its state persisted.
 */
public interface MainControllerInterface extends Remote, Serializable {

    /**
     * Returns the number of required players for each game.
     *
     * @return An ArrayList of Pair objects, where each Pair contains the game ID and the number of required players for that game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException;

    /**
     * Connects a client to the game.
     *
     * This method is used when a client wants to connect to the game.
     * It takes as an argument an instance of ClientControllerInterface representing the client.
     *
     * @param client The client that wants to connect to the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void connect(ClientControllerInterface client) throws RemoteException;

    /**
     * Checks if a nickname is unique.
     *
     * This method is used to ensure that each player has a unique nickname.
     * It takes as an argument a String representing the nickname to check.
     *
     * @param name The nickname to check.
     * @return true if the nickname is unique, false otherwise.
     * @throws RemoteException If a remote or network communication error occurs.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    boolean checkUniqueNickName(String name) throws RemoteException, IOException, ClassNotFoundException;

    /**
     * Allows a client to join a game.
     *
     * This method is used when a client wants to join a specific game.
     * It takes as arguments an instance of ClientControllerInterface representing the client and an integer representing the game ID.
     *
     * @param client The client that wants to join the game.
     * @param GameID The ID of the game that the client wants to join.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void joinGame(ClientControllerInterface client, int GameID) throws RemoteException;

    /**
     * Displays the available games.
     *
     * This method is used to get a list of the games that are currently available for players to join.
     *
     * @return A Map representing the available games.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    Map DisplayAvailableGames() throws RemoteException;

    /**
     * Creates a new game.
     *
     * This method is used when a client wants to create a new game.
     * It takes as arguments an instance of ClientControllerInterface representing the client and an integer representing the number of players in the game.
     *
     * @param client The client that wants to create the game.
     * @param n The number of players in the game.
     * @return The new game that was created. This is an instance of GameControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException;

    /**
     * Notifies a game that a new player has joined.
     *
     * This method is used when a player has successfully joined a game.
     * It calls the NotifyNewPlayerJoined method of the game, passing the client that joined as an argument.
     * This allows the game to update its state to reflect the new player.
     *
     * @param game The game that the player has joined. This is an instance of GameControllerInterface.
     * @param client The player that has joined the game. This is an instance of ClientControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException;

    /**
     * Adds a nickname to the list of nicknames.
     *
     * This method is used when a player wants to set their nickname.
     * It takes as an argument a String representing the nickname to add.
     *
     * @param name The nickname to add.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void addNickname(String name) throws RemoteException;

    /**
     * Returns an instance of MainControllerInterface.
     *
     * This method is used to get an instance of MainControllerInterface.
     * It is a static method, so it can be called without creating an instance of the class it is in.
     *
     * @return An instance of MainControllerInterface.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    static MainControllerInterface getInstance() throws RemoteException {
        return null;
    }
}

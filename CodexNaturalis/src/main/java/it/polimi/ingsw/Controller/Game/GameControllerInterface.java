package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the contract for a GameController.
 * It extends the Remote interface to support remote method invocation.
 */
public interface GameControllerInterface extends Remote {

    /**
     * Retrieves the game listener.
     *
     * @return the game listener.
     */
    public GameListener getListener() throws RemoteException;

    /**
     * Retrieves the number of players.
     *
     * @return the number of players.
     */
    public int getNumPlayers() throws RemoteException;

    /**
     * Adds a player to the game.
     *
     * @param client the client controller of the player to be added.
     */
    public void addPlayer(ClientControllerInterface client) throws RemoteException;

    /**
     * Retrieves the current game status.
     *
     * @return the current game status.
     */
    public GameStatus getStatus() throws RemoteException;

    /**
     * Sets the game status.
     *
     * @param status the new game status.
     */
    public void setStatus(GameStatus status) throws RemoteException;

    /**
     * Checks if the maximum number of players has been reached.
     */
    public void CheckMaxNumPlayerReached() throws RemoteException;

    /**
     * Retrieves a list of available pawn colors.
     *
     * @return a list of available pawn colors.
     */
    public List<PawnColor> AvailableColors() throws RemoteException;

    /**
     * Notifies that a new player has joined the game.
     *
     * @param newPlayer the client controller of the new player.
     */
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException;

    /**
     * Retrieves the ID of the game controller.
     *
     * @return the ID of the game controller.
     */
    public int getId() throws RemoteException;

    /**
     * Receives a message from a client.
     *
     * @param c the command received.
     * @param client the client controller that sent the command.
     * @throws RemoteException if a remote or network communication error occurs.
     */
    public void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException;

    /**
     * Retrieves the model of the playground.
     *
     * @return the model of the playground.
     */
    public PlayGround getModel() throws RemoteException;

    /**
     * Sets the model of the playground.
     *
     * @param model the new model of the playground.
     */
    public void setModel(PlayGround model) throws RemoteException;


    void removeAvailableColor(PawnColor color) throws RemoteException;
}
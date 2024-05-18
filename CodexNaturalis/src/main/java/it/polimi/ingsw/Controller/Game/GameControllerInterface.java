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
    public GameListener getListener();

    /**
     * Retrieves the number of players.
     *
     * @return the number of players.
     */
    public int getNumPlayers();

    /**
     * Adds a player to the game.
     *
     * @param client the client controller of the player to be added.
     */
    public void addPlayer(ClientControllerInterface client);

    /**
     * Retrieves the current game status.
     *
     * @return the current game status.
     */
    public GameStatus getStatus();

    /**
     * Sets the game status.
     *
     * @param status the new game status.
     */
    public void setStatus(GameStatus status);

    /**
     * Checks if the maximum number of players has been reached.
     */
    public void CheckMaxNumPlayerReached();

    /**
     * Retrieves a list of available pawn colors.
     *
     * @param clients the list of client controllers.
     * @return a list of available pawn colors.
     */
    public List<PawnColor> AvailableColors(List<ClientControllerInterface> clients);

    /**
     * Notifies that a new player has joined the game.
     *
     * @param newPlayer the client controller of the new player.
     */
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer);

    /**
     * Retrieves the ID of the game controller.
     *
     * @return the ID of the game controller.
     */
    public int getId();

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
    public PlayGround getModel();

    /**
     * Sets the model of the playground.
     *
     * @param model the new model of the playground.
     */
    public void setModel(PlayGround model);
}
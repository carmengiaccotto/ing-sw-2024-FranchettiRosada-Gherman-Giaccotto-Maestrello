package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface defines the contract for a GameController.
 * It extends the Remote interface to support remote method invocation.
 */
public interface GameControllerInterface extends Remote, Serializable {

    /**
     * Retrieves the game listener.
     *
     * @return the game listener.
     */
    GameListener getListener() throws RemoteException;

    /**
     * Retrieves the number of players.
     *
     * @return the number of players.
     */
    int getNumPlayers() throws RemoteException;

    /**
     * Adds a player to the game.
     *
     * @param client the client controller of the player to be added.
     */
    void addPlayer(ClientControllerInterface client) throws RemoteException;

    /**
     * Retrieves the current game status.
     *
     * @return the current game status.
     */
    GameStatus getStatus() throws RemoteException;

    /**
     * Sets the game status.
     *
     * @param status the new game status.
     */
    void setStatus(GameStatus status) throws RemoteException;

    /**
     * Checks if the maximum number of players has been reached.
     */
    void CheckMaxNumPlayerReached() throws RemoteException;

    /**
     * Retrieves a list of available pawn colors.
     *
     * @return a list of available pawn colors.
     */
    List<PawnColor> AvailableColors() throws RemoteException;

    /**
     * Notifies that a new player has joined the game.
     *
     * @param newPlayer the client controller of the new player.
     */
    void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException;

    /**
     * Retrieves the ID of the game controller.
     *
     * @return the ID of the game controller.
     */
    int getId() throws RemoteException;

    /**
     * Receives a message from a client.
     *
     * @param c the command received.
     * @param client the client controller that sent the command.
     * @throws RemoteException if a remote or network communication error occurs.
     */
    void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException;

    /**
     * Retrieves the model of the playground.
     *
     * @return the model of the playground.
     */
    PlayGround getModel() throws RemoteException;

    /**
     * Sets the model of the playground.
     *
     * @param model the new model of the playground.
     */
    void setModel(PlayGround model) throws RemoteException;


    void removeAvailableColor(PawnColor color) throws RemoteException;


    ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException;

    List<PawnColor> getAvailableColors() throws RemoteException;

    ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException;


    InitialCard extractInitialCard() throws RemoteException;
}
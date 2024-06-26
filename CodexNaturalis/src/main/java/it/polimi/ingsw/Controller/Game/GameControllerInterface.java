package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface defines the contract for a GameController.
 * It extends the Remote interface to support remote method invocation.
 * It also extends Serializable to allow objects of classes implementing this interface to be serialized.
 */
public interface GameControllerInterface extends Remote, Serializable {

    /**
     * Retrieves the game listener.
     *
     * @return the game listener.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    GameListener getListener() throws RemoteException;

    /**
     * Retrieves the number of players.
     *
     * @return the number of players.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    int getNumPlayers() throws RemoteException;

    /**
     * Adds a player to the game.
     *
     * @param client the client controller of the player to be added.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void addPlayer(ClientControllerInterface client) throws RemoteException;

    /**
     * Retrieves the current game status.
     *
     * @return the current game status.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    GameStatus getStatus() throws RemoteException;

    /**
     * Sets the game status.
     *
     * @param status the new game status.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void setStatus(GameStatus status) throws RemoteException;

    /**
     * Checks if the maximum number of players has been reached.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void CheckMaxNumPlayerReached() throws RemoteException;

    /**
     * Notifies that a new player has joined the game.
     *
     * @param newPlayer the client controller of the new player.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException;

    /**
     * Retrieves the unique identifier of the game controller.
     *
     * @return the ID of the game controller.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    int getId() throws RemoteException;

    /**
     * Retrieves the current state of the playground.
     *
     * @return the model of the playground.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    PlayGround getModel() throws RemoteException;

    /**
     * Updates the state of the playground.
     *
     * @param model the new model of the playground.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void setModel(PlayGround model) throws RemoteException;

    /**
     * Retrieves the list of players in the game.
     *
     * @return An ArrayList of Player objects representing the players in the game.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    ArrayList<Player> getPlayers() throws RemoteException;;

    /**
     * Removes a color from the list of available colors for the pawns.
     *
     * @param color The color to be removed.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void removeAvailableColor(PawnColor color) throws RemoteException;

    /**
     * Extracts the initial hand cards for a player.
     *
     * @return An ArrayList of PlayCard objects representing the player's hand.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException;

    /**
     * Retrieves the list of available colors for the pawns.
     *
     * @return A List of PawnColor enumerations representing the available colors.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    List<PawnColor> getAvailableColors() throws RemoteException;

    /**
     * Provides the personal objective cards for a player.
     *
     * @return An ArrayList of ObjectiveCard objects representing the personal objective options for the player.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException;

    /**
     * Extracts an initial card for the game.
     *
     * @return An InitialCard object representing the initial card.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    InitialCard extractInitialCard() throws RemoteException;

    /**
     * Retrieves the number of players who have chosen their objective.
     *
     * @return The number of players who have chosen their objective.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    int getPlayersWhoChoseObjective() throws RemoteException;

    /**
     * Increments the count of players who have chosen their objective.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    void incrementPlayersWhoChoseObjective() throws RemoteException;

    /**
     * Checks if a move is valid.
     *
     * @param playArea The current state of the play area. This is an instance of PlayArea.
     * @param row The row in the play area where the player wants to place the card.
     * @param column The column in the play area where the player wants to place the card.
     * @param newCard The new card the player wants to place.
     * @return A boolean value indicating whether the move is valid. True if the move is valid, false otherwise.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    boolean isValidMove(PlayArea playArea, int row, int column, SideOfCard newCard) throws RemoteException;

    /**
     * Generates the final ranking of the players at the end of the game.
     *
     * @return A string representing the final ranking of the players.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    String finalRanking() throws RemoteException;

}
package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Represents the state of a game.
 *
 * This class is used to keep track of the current state of a game.
 * It includes information such as the current round, the players in the game, the model of the game, and the status of the game.
 */
public class GameState implements Serializable {
    private int round;
    private List<ClientControllerInterface> players;
    private PlayGround model;
    private GameStatus status;

    /**
     * Constructs a new GameState with the specified round, players, model, and status.
     *
     * @param round The current round of the game.
     * @param players The players in the game.
     * @param model The model of the game.
     * @param status The status of the game.
     */
    public GameState(int round,  List<ClientControllerInterface> players, PlayGround model, GameStatus status) {
        this.round = round;
        this.players = players;
        this.model = model;
        this.status = status;
    }

    /**
     * Returns the status of the game.
     *
     * @return The status of the game.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the game.
     *
     * @param status The new status of the game.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Returns the current round of the game.
     *
     * @return The current round of the game.
     */
    public int getRound() {
        return round;
    }

    /**
     * Sets the current round of the game.
     *
     * @param round The new round of the game.
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Returns the players in the game.
     *
     * @return The players in the game.
     */
    public List<ClientControllerInterface> getPlayers() {
        return players;
    }

    /**
     * Sets the players in the game.
     *
     * @param players The new players of the game.
     */
    public void setPlayers( List<ClientControllerInterface> players) {
        this.players = players;
    }

    /**
     * Returns the model of the game.
     *
     * @return The model of the game.
     */
    public PlayGround getModel() {
        return model;
    }

    /**
     * Returns the player with the specified nickname.
     *
     * @param nickname The nickname of the player to return.
     * @return The player with the specified nickname, or null if no such player exists.
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public Player getPlayerByNickname(String nickname) throws RemoteException {
        for (ClientControllerInterface playerController : getPlayers()) {
            if (playerController.getNickname().equals(nickname)) {
                return playerController.getPlayer();
            }
        }
        return null;
    }

    /**
     * Sets the model of the game.
     *
     * This method is used to update the model of the game.
     * It takes as an argument an instance of PlayGround representing the new model of the game.
     *
     * @param model The new model of the game.
     */
    public void setModel(PlayGround model) {
        this.model = model;
    }
}

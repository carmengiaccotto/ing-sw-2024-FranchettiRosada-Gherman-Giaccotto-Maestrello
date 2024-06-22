package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    private int round;
    private List<ClientControllerInterface> players;
    private PlayGround model;

    private GameStatus status;

    public GameState(int round,  List<ClientControllerInterface> players, PlayGround model, GameStatus status) {
        this.round = round;
        this.players = players;
        this.model = model;
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    // Getter e setter per players
    public  List<ClientControllerInterface> getPlayers() {
        return players;
    }

    public void setPlayers( List<ClientControllerInterface> players) {
        this.players = players;
    }

    public PlayGround getModel() {
        return model;
    }

    public void setModel(PlayGround model) {
        this.model = model;
    }

}

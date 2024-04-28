package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;

import java.util.List;

/** Exceptions need to be added. Methods to manage connection and
 * disconnection of a Player need to be added.  */

public class GameController implements Runnable {

    private Playground model;

    @Override
    public void run() {

    }

    /** Add a player to Game's lobby. The player will be asked to set a nickname */
    public void addPlayerToLobby(Player p) throws MaxNumPlayersReached {
    }

    /** Check if the nickname is valid and unique*/
    public void checkUniqueNickname(){

    }

    /** Return a list containing all the current players of the game (Online and Offline)*/
    public List <Player> getPlayers() {
        return model.getPlayers();
    }

    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

    /** Reconnect a player to the Game unless the game is already over*/
    public void reconnectPlayer(Player p) throws  GameEndedException {
    }

    private void extractCommonObjectiveCards() {
    }

    private void extractCommonPlaygroundCards() {

    }

    private void extractPlayerCard(){

    }


    private void extractOrderPlayers() {

    }

    private void AvalaibleColors(){

    }

    /** Method to be called by the first Player that enter in the lobby*/
    private void chooseNumOfPlayers(){

    }

    public synchronized void sentMessage() {
    }

    private void checkObjectivePoints() {

    }


    public void executePlayerTurn() {
    }


    public int getGameId() {
        return model.getGameId();
    }

    public void initializeGame(){

    }

    /** Method that checks for each turn if a player reached 20 points*/
    public void checkPoints(){

    }

    public void FinalizeGame(){

    }

    public void decreeWinner(){

    }

    /**
     * Return the GameStatus of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

}



















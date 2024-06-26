package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the methods that a client controller should implement.
 * It extends Remote and Serializable interfaces for remote method invocation and object serialization.
 */
public interface ClientControllerInterface extends Remote, Serializable {

    /**
     * Sets the server for the client controller.
     *
     * @param server The main controller interface for the server.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    public void setServer(MainControllerInterface server) throws RemoteException;

    /**
     * Sets the view for the client controller.
     *
     * @param view The user interface for the client.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void setView(UserInterface view) throws RemoteException;

    /**
     * Disconnects the client from the server.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void disconnect() throws RemoteException;

    /**
     * This method is used to choose the color of the pawn for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void ChoosePawnColor() throws RemoteException;

    /**
     * This method is used to join a game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void JoinGame() throws RemoteException;

    /**
     * This method is used to get the nickname of the player.
     * @return String representing the nickname of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    String getNickname() throws RemoteException;

    /**
     * This method is used to set the nickname of the player.
     * @param nickname The nickname to be set for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void setNickname(String nickname) throws RemoteException;

    /**
     * This method is used to join or create a game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void JoinOrCreateGame() throws RemoteException;

    void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException;

    /**
     * This method is used to choose a nickname for the player.
     * @return String representing the chosen nickname.
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    String ChooseNickname() throws IOException, ClassNotFoundException;

    /**
     * This method is used to set up a new game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void  newGameSetUp() throws RemoteException;

    /**
     * This method is used to make the player wait.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void Wait() throws RemoteException;

    /**
     * This method is used to get the color of the pawn of the player.
     * @return PawnColor representing the color of the pawn of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    PawnColor getPawnColor() throws RemoteException;

    /**
     * Sets the personal objective card for the player.
     *
     * @param objectiveCard The objective card to be set for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException;

    /**
     * Retrieves the player.
     *
     * @return Player object representing the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    Player getPlayer() throws RemoteException;

    /**
     * Displays the board and play areas.
     *
     * @param m The playground to be displayed.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void showBoardAndPlayAreas(PlayGround m) throws RemoteException;

    /**
     * Allows the player to choose a card to draw.
     *
     * @param m The playground where the card is to be drawn.
     * @return PlayGround object after the card has been drawn.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    PlayGround chooseCardToDraw(PlayGround m) throws RemoteException;

    /**
     * Sends an update message.
     *
     * @param message The message to be sent.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void sendUpdateMessage(String message) throws RemoteException;

    /**
     * Connects the client to the server.
     *
     * @param ip The IP address of the server.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void connect(String ip)  throws RemoteException;

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to be added to the player's hand.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void addCardToHand(PlayCard card) throws RemoteException;

    /**
     * Retrieves the score of the player.
     *
     * @return int representing the score of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    int getScore() throws RemoteException;

    /**
     * Retrieves the current round of the game.
     *
     * @return int representing the current round of the game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    int getRound() throws RemoteException;

    /**
     * Sets the current round of the game.
     *
     * @param round The round to be set for the game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void setRound(int round) throws RemoteException;

    /**
     * Sets the game controller for the client.
     *
     * @param game The game controller to be set for the client.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void setGame(GameControllerInterface game) throws RemoteException;

    /**
     * Retrieves the personal objective card of the player.
     *
     * @return ObjectiveCard representing the personal objective card of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    ObjectiveCard getPersonalObjectiveCard() throws RemoteException;

    /**
     * This method is used to join a lobby.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void JoinLobby() throws RemoteException;

    /**
     * This method is used to instruct the player on what to do next.
     * @param doThis A string representing the instruction for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    void WhatDoIDoNow (String doThis) throws RemoteException;
}
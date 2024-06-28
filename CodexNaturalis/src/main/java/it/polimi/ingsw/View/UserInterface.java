package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Cards.*;

import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the UserInterface interface for the Codex Naturalis game.
 * It defines the methods that must be implemented by any class that provides a user interface for the game.
 * The methods in this interface allow the user to interact with the game, such as choosing cards, selecting positions, and displaying the game board.
 */
public interface UserInterface {

//    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException;
//    public void choosePersonalObjectiveCard(String nickname);
//    public void playCard(String nickname);


    /**
     * This method prompts the user to choose a card to draw.
     * @return A String representing the chosen card.
     */
    String chooseCardToDraw ();

    /**
     * This method prompts the user to choose a card to play from their hand.
     * @param cardInHand An ArrayList of PlayCard objects representing the cards in the user's hand.
     * @return An integer representing the index of the chosen card in the list.
     */
    int chooseCardToPlay(ArrayList<PlayCard> cardInHand);

    /**
     * This method prompts the user to choose a side.
     * @return A String representing the chosen side.
     */
    String chooseSide();

    /**
     * This method prompts the user to choose a position for a card on the play area.
     * @param playArea A PlayArea object representing the current state of the play area.
     * @return A Pair of integers representing the chosen position (row, column).
     */
    Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea);

    /**
     * This method prompts the user to select a nickname.
     * @return A String representing the chosen nickname.
     */
    String selectNickName();

    /**
     * This method prompts the user to choose whether to create a new game or join an existing one.
     * @return An integer representing the user's choice (1 for create, 2 for join).
     */
    int createOrJoin();

    /**
     * This method prompts the user to enter the maximum number of players for a game.
     * @return An integer representing the maximum number of players.
     */
    int MaxNumPlayers();

    /**
     * This method displays the available games that the user can join, along with the number of players in each game.
     * @param games A Map where the keys are game IDs and the values are ArrayLists of Strings representing the nicknames of the players in each game.
     * @param numPlayers An ArrayList of Pairs where each Pair contains a game ID and the number of players in that game.
     * @return An integer representing the ID of the chosen game.
     * @throws RemoteException If a remote method invocation fails.
     */
    int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException;

    /**
     * This method prints the current state of the game board to the console.
     * It displays information about the opponents and the current player (me).
     *
     * @param model A PlayGround object representing the current state of the game board.
     * @param opponents An ArrayList of Player objects representing the opponents in the game.
     * @param me A Player object representing the current player.
     */
    void printBoard(PlayGround model, ArrayList<Player> opponents, Player me);

    /**
     * This method prompts the user to choose a personal Objective Card from a list of available objective cards.
     *
     * @param objectives An ArrayList of ObjectiveCard objects representing the available objective cards.
     * @return An integer representing the index of the chosen objective card in the list.
     */
    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives);

    /**
     * This method displays the initial card to the user.
     *
     * @param card An InitialCard object representing the initial card.
     */
    void showInitialCard(InitialCard card);

    /**
     * This method displays the available colors that the user can choose from.
     *
     * @param availableColors A List of PawnColor objects representing the available colors.
     */
    void displayAvailableColors(List<PawnColor> availableColors);

    /**
     * This method notifies the user that the system is waiting for other players to join the game.
     */
    void waitingForPlayers();

    /**
     * This method prints a given message to the console.
     *
     * @param message A String representing the message to be printed.
     */
    void printMessage(String message);

    /**
     * This method prompts the user to select a command when it's their turn.
     *
     * @param IsMyTurn A Boolean indicating whether it's the user's turn.
     * @return A Command enum representing the chosen command by the user.
     */
    Command receiveCommand(Boolean IsMyTurn);

    /**
     * This method displays a given string to the user.
     *
     * @param s A String representing the string to be displayed.
     */
    void showString(String s);

    /**
     * This method prompts the user to input a number.
     *
     * @return An integer representing the user's input.
     */
    int getInput();
}
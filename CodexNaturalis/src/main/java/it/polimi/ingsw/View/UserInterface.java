package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Message;
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

public interface UserInterface {

//    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException;
//    public void choosePersonalObjectiveCard(String nickname);
//

    //
//    public void playCard(String nickname);
//
    String chooseCardToDraw ();
    int chooseCardToPlay(ArrayList<PlayCard> cardInHand);
    String chooseSide();

    Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea);

    String selectNickName();

    int createOrJoin();

    int MaxNumPlayers();

    int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException;

    void printBoard(PlayGround model, ArrayList<Player> opponents, Player me, ArrayList<Message> myChat);

    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives);

    void showInitialCard(InitialCard card);

    int  displayAvailableColors(List<PawnColor> availableColors);

    void waitingForPlayers();


    void printMessage(String message);

    Command receiveCommand();

    ArrayList<String> viewChat(ArrayList<Message> myChat, Player player);

    void showString(String s);

    Pair<String, String> sendChatMessage(ArrayList<Player> players);
}
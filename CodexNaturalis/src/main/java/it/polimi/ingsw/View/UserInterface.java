package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

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

    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives);

    void showInitialCard(InitialCard card);

    int  displayAvailableColors(List<PawnColor> availableColors);

    void waitingForPlayers();

    void printObjectives(ObjectiveCard card);

    void printMessage(String message);

    Command receiveCommand();

    void showCardsInHand(ArrayList<PlayCard> cards);

    void showBoardAndPlayers(ClientControllerInterface me, PlayGround model, ArrayList<ClientControllerInterface> opponents ) throws RemoteException;

    void showOpponentPlayArea(ClientControllerInterface opponent);

    void showPlayerInfo(ClientControllerInterface client);

    void showCommonCards(ArrayList<ResourceCard> cards, ArrayList<GoldCard> goldCards, Deck resourceDeck, Deck goldDeck);

    void showMyPlayArea(PlayArea playArea);


    void viewChat();
}
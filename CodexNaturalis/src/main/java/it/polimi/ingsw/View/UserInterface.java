package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

import java.rmi.RemoteException;
import java.util.ArrayList;

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

    public ArrayList<Integer> choosePositionCardOnArea(PlayArea playArea);

    String selectNickName();

    int createOrJoin();

    int MaxNumPlayers();

    int displayavailableGames(ArrayList<GameControllerInterface> games) throws RemoteException;

    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives);
    void playInitialCard(SideOfCard side, PlayArea playArea);

    int  displayAvailableColors(ArrayList<PawnColor> availableColors);

    void waitingForPlayers();

    void printObjectives(ObjectiveCard card);

    void printMessage(String message);

    Command receiveCommand();

    void showCardsInHand(ArrayList<PlayCard> cards);

    void showOpponentPlayArea(ClientControllerInterface opponent);

    void showPlayerInfo(ClientControllerInterface client);

    void showCommonCards(ArrayList<ResourceCard> cards, ArrayList<GoldCard> goldCards, Deck resourceDeck, Deck goldDeck);

    void showMyPlayArea(PlayArea playArea);


}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.ObjectiveCard;
import it.polimi.ingsw.model.Cards.PlayCard;
import it.polimi.ingsw.model.Cards.SideOfCard;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.model.Enumerations.PawnColor;
import it.polimi.ingsw.model.Enumerations.Side;
import it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import it.polimi.ingsw.model.PlayGround.PlayArea;
import it.polimi.ingsw.model.PlayGround.Player;

import java.rmi.Remote;
import java.util.List;

public interface GameControllerInterface extends  Remote{
    //Player Configuration Methods
    void SetPlayerPawnColorChoice(PawnColor color);
    void addPlayerToLobby(Player p) throws MaxNumPlayersException;
    boolean checkUniqueNickname(String name);
    void reconnectPlayer(Player p);
    ObjectiveCard getPersonalObjective(String nickname);


    //PlayGrounds Objects Methods
    void extractCommonObjectiveCards();
    void extractCommonPlaygroundCards();
    void extractPlayerHandCards();

    //Game playing methods
    List<PawnColor> AvailableColors();
    void addPersonalObjectiveCardPoints();
    void sentMessage(Message m);
    boolean scoreMaxReached();
    void initializeGame();
    void FinalizeGame();
    String decreeWinner();
    boolean isMyTurn(String nickname);
    void addCommonObjectiveCardsPoints();
    SideOfCard playerChoosesCardToPlay(PlayCard card, Side side);

    //Game Rules
    boolean ValidTwoCornersSameCard(PlayArea playArea, int row1, int column1);
    boolean ValidPositionCardOnArea(PlayArea playArea, int row1, int column1, SideOfCard newCard);
    boolean notTryingToCoverHiddenCorners(PlayArea playArea, int row1, int column1, SideOfCard newCard);


    //Game Object methods
    public static GameController getInstance() {
        return null;
    }
    int getGameId();








}

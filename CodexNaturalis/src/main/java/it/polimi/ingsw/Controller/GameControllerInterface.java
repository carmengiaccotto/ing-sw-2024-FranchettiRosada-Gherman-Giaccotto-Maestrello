package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Connection.VirtualClient;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Exceptions.MaxNumPlayersException;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.rmi.Remote;
import java.util.List;

public interface GameControllerInterface extends  Remote{
    //Player Configuration Methods
    List<VirtualClient> getPlayers();
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

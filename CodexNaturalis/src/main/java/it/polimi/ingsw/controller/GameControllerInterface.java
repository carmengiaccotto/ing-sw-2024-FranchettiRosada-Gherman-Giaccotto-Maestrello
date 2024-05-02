package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.*;

import java.util.List;

public interface GameControllerInterface {
     void addPlayerToLobby(Player p) throws MaxNumPlayersException;
     void SetPlayerPawnColorChoice(PawnColor chosenColor);
     boolean checkUniqueNickname(String name);
     void reconnectPlayer(Player p);
    void extractCommonObjectiveCards();
    void extractCommonPlaygroundCards();
    void extractPlayerHandCards();
    List<PawnColor> AvailableColors();
    void addPersonalObjectiveCardPoints();

    void addCommonObjectiveCardsPoints();
    int getGameId();
    boolean scoreMaxReached();
    void initializeGame();
    void FinalizeGame();
    String decreeWinner();

    GameStatus getStatus();

}

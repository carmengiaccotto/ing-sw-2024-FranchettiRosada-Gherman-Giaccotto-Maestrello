package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.*;
import java.rmi.Remote;

import java.util.List;

public interface GameControllerInterface extends  Remote{

    void addPlayerToLobby(Player p) throws MaxNumPlayersException;
     void SetPlayerPawnColorChoice(PawnColor chosenColor);
     boolean checkUniqueNickname(String name);
     void reconnectPlayer(Player p);
    void extractCommonObjectiveCards();
    void extractCommonPlaygroundCards();
    void extractPlayerHandCards();

    public static GameController getInstance() {
        return null;
    }

    List<PawnColor> AvailableColors();
    void addPersonalObjectiveCardPoints();
    boolean isMyTurn(String nickname);
    void sentMessage(Message m);

    void addCommonObjectiveCardsPoints();
    int getGameId();
    boolean scoreMaxReached();
    void initializeGame();
    void FinalizeGame();
    String decreeWinner();

}

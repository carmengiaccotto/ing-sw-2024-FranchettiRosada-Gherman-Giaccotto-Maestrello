package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

import java.util.ArrayList;

public interface UserInterface {

//    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException;
//    public void choosePersonalObjectiveCard(String nickname);
//

//
//    public void playCard(String nickname);
//
//    public void drawCard(String nickname);
//
//    public void showBoardAndPlayAreas();

    String selectNickName();

    int createOrJoin();

    int MaxNumPlayers();

    int displayavailableGames(ArrayList<GameController> games);

    String ChooseSideInitialCard(InitialCard c);
    void playInitialCard(SideOfCard side, PlayArea playArea);


    int  displayAvailableColors(ArrayList<PawnColor> availableColors);
    void waitingForPlayers();

    void printObjectives(ObjectiveCard card);

    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives);

}

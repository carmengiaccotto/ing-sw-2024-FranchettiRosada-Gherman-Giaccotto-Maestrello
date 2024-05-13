package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumerations.PawnColor;

import java.util.ArrayList;

public interface UserInterface {

//    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException;
//    public void choosePersonalObjectiveCard(String nickname);
//
//    public void playInitialCard(InitialCard c, String nickname);
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


    int  displayAvailableColors(ArrayList<PawnColor> availableColors);
    void waitingForPlayers();

}

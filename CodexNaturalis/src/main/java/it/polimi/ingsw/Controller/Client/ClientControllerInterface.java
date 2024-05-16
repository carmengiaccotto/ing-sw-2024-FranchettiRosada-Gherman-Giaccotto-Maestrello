package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ClientControllerInterface extends Remote {
    void setView(UserInterface view);

    void disconnect() throws RemoteException;

    void ChoosePawnColor(ArrayList<PawnColor> availableColors) throws RemoteException;

    void getGameToJoin(ArrayList<GameController> availableGames) throws RemoteException;

    String getNickname();

    void JoinOrCreateGame() throws RemoteException;

    void ChooseNickname() throws RemoteException;

    void  newGameSetUp() throws RemoteException;

    void Wait() throws RemoteException;

    PawnColor getPawnColor() throws RemoteException;

    void updatePlayers(List<ClientControllerInterface> players)throws RemoteException;

    void setPersonalObjectiveCard(ObjectiveCard objectiveCard);
    Player getPlayer() throws RemoteException;

    void showBoardAndPlayAreas(PlayGround m) throws RemoteException;

    PlayCard chooseCardToDraw(PlayGround m) throws RemoteException;

    Command receiveCommand();

    String chooseSideInitialCard(InitialCard c);

    int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) throws RemoteException;

}

package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClient extends Remote, Serializable {
    void askTypeOfView() throws RemoteException;

    void connect() throws RemoteException;

    void disconnect() throws RemoteException;

    void setPawnColor(ArrayList<PawnColor> availableColors) throws RemoteException;

    String getNickname() throws RemoteException;

    void setNickname() throws RemoteException;

    void JoinOrCreateGame() throws RemoteException;

    int newGameSetUp() throws RemoteException;

    void getGameToJoin() throws RemoteException;

    Player getPlayer() throws RemoteException;
    GameController getGame() throws RemoteException;

    void updatePlayers(List<VirtualClient> players) throws RemoteException;

    UserInterface getView() throws RemoteException;

    void Wait() throws RemoteException;
}

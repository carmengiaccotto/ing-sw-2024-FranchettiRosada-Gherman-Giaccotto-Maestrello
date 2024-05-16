package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Controller.GameController;

import java.rmi.RemoteException;

public interface MainControllerInterface {
    void connect(VirtualClient client) throws RemoteException;
    void addClientToLobby(VirtualClient client) throws RemoteException;
    void checkUniqueNickName(String name, VirtualClient client) throws RemoteException;
    void joinGame(VirtualClient client, int GameID) throws RemoteException;
    void DisplayAvailableGames(VirtualClient client) throws RemoteException;
    void createGame(VirtualClient client) throws RemoteException;
    void NotifyGamePlayerJoined(GameController game, VirtualClient client) throws RemoteException;

}

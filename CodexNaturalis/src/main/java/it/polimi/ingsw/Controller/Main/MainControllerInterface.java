package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {
    void connect(ClientControllerInterface client) throws RemoteException;
    void addClientToLobby(ClientControllerInterface client) throws RemoteException;
    boolean checkUniqueNickName(String name, ClientControllerInterface client) throws RemoteException;
    void joinGame(ClientControllerInterface client, int GameID) throws RemoteException;
    void DisplayAvailableGames(ClientControllerInterface client) throws RemoteException;
    void createGame(ClientControllerInterface client, int n) throws RemoteException;
    void NotifyGamePlayerJoined(GameController game, ClientControllerInterface client) throws RemoteException;

    static MainControllerInterface getInstance() throws RemoteException {
        return null;
    }
}

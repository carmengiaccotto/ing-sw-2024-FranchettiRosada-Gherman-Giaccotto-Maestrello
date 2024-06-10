package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

public interface MainControllerInterface extends Remote, Serializable {
    void connect(ClientControllerInterface client) throws RemoteException;

    boolean checkUniqueNickName(String name) throws RemoteException, IOException, ClassNotFoundException;

    GameControllerInterface joinGame(ClientControllerInterface client, int GameID) throws RemoteException;

    ArrayList<GameControllerInterface> DisplayAvailableGames() throws RemoteException;

    GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException;

    void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException;

    HashSet<String> getNicknames() throws RemoteException;

    void addNickname(String name) throws RemoteException;

    static MainControllerInterface getInstance() throws RemoteException {
        return null;
    }
}

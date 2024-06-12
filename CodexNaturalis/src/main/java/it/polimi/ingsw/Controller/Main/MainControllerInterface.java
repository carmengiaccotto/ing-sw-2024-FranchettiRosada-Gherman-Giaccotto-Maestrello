package it.polimi.ingsw.Controller.Main;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface MainControllerInterface extends Remote, Serializable {
    ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException;

    void connect(ClientControllerInterface client) throws RemoteException;

    boolean checkUniqueNickName(String name) throws RemoteException, IOException, ClassNotFoundException;

    void joinGame(ClientControllerInterface client, int GameID) throws RemoteException;

    Map DisplayAvailableGames() throws RemoteException;

    GameControllerInterface createGame(ClientControllerInterface client, int n) throws RemoteException;

    void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException;

    void addNickname(String name) throws RemoteException;

    static MainControllerInterface getInstance() throws RemoteException {
        return null;
    }
}

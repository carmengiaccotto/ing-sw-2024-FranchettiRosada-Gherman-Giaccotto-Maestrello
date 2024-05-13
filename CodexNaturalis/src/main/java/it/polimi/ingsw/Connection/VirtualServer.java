package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Controller.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VirtualServer extends  Remote{
    void connect(VirtualClient client)throws  RemoteException;
    void addClientToLobby(VirtualClient client)throws RemoteException;
    boolean checkUniqueNickName(String name)throws RemoteException;
    void joinGame(VirtualClient client, int GameID) throws RemoteException;
    void createGame(VirtualClient client) throws RemoteException;
    void createGameSynchronized(VirtualClient client) throws RemoteException;
    ArrayList<GameController> DisplayAvailableGames() throws RemoteException;







}

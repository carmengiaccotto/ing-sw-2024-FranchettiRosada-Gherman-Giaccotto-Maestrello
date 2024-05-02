package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {

    GameControllerInterface createGame(String nickname) throws RemoteException;

    GameControllerInterface joinSpecificGame(String nickname, Integer id) throws RemoteException;

    GameControllerInterface reconnectPlayer(String nickname, Integer id) throws RemoteException;

    GameControllerInterface disconnectPlayer(String nickname, Integer id) throws RemoteException;

}

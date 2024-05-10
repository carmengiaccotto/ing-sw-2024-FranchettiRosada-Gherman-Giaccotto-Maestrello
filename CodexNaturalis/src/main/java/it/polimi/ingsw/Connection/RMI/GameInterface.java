package it.polimi.ingsw.Connection.RMI;

import it.polimi.ingsw.controller.GameControllerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {

    GameControllerInterface createGame(String nickname) throws RemoteException;

    GameControllerInterface joinExistingGame(String nickname) throws RemoteException;

    GameControllerInterface reconnectPlayer(String nickname) throws RemoteException;

    GameControllerInterface disconnectPlayer(String nickname) throws RemoteException;

}

package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {
    void connect(ClientMoves client) throws RemoteException;

    void createGame(String nickname) throws RemoteException;

    void joinSpecificGame(String nickname, Integer id) throws RemoteException;

    void reconnectPlayer(String nickname, Integer id) throws RemoteException;

    void disconnectFromGame(String nickname, Integer id) throws RemoteException;

}

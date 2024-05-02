package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientMoves extends Remote {

    void connect() throws RemoteException;

    boolean isPlayerTurn() throws RemoteException;

    void sendMessage(Message m) throws RemoteException;

    void leave(String nickname, int id) throws RemoteException;

    void disconnect(String nickname, int id) throws RemoteException;

}

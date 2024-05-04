package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientMoves extends Remote {

    void connect() throws RemoteException;

    void sendMessage(Message m) throws RemoteException;

    void leave(String nickname) throws RemoteException;

    void disconnect(String nickname) throws RemoteException;

}

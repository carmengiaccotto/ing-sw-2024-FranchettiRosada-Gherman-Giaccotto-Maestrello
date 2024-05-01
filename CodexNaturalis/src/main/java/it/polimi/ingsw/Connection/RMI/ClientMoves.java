package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientMoves extends Remote {

    void connect() throws RemoteException;

    void showUpdate() throws RemoteException;

    void isPlayerTurn() throws RemoteException;

    void sendMessage() throws RemoteException;

    void disconnect() throws RemoteException;


}

package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientConnection extends Remote {
    void sendResponse(Response response) throws RemoteException;
    void setRMIServer(RMIServerConnection rmiServer) throws RemoteException;
}

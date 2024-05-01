package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import java.rmi.RemoteException;

public class RMIServer implements GameInterface {


    @Override
    public void connect(ClientMoves client) throws RemoteException {

    }

    @Override
    public void createGame(String nickname) throws RemoteException {

    }

    @Override
    public void joinSpecificGame(String nickname, Integer id) throws RemoteException {

    }

    @Override
    public void reconnectPlayer(String nickname, Integer id) throws RemoteException {

    }

    @Override
    public void disconnectFromGame(String nickname, Integer id) throws RemoteException {

    }
}

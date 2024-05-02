package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientMoves {

    // Add draw method, place card

    // add 'leave game' to the server

    GameInterface server;

    GameControllerInterface gameController = null;

    private Registry registry;

    private String nickname;

    public RMIClient(GameInterface server, Registry registry) throws RemoteException {
        this.server = server;
        this.registry = registry;
    }

    public RMIClient(int port, GameInterface server, Registry registry) throws RemoteException {
        super(port);
        this.server = server;
        this.registry = registry;
    }

    public RMIClient(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, GameInterface server, Registry registry) throws RemoteException {
        super(port, csf, ssf);
        this.server = server;
        this.registry = registry;
    }

    @Override
    public void connect() throws RemoteException {

    }

    @Override
    public boolean isPlayerTurn() throws RemoteException {
        return gameController.isMyTurn(nickname);
    }

    @Override
    public void sendMessage(Message m) throws RemoteException {
        gameController.sentMessage(m);
    }

    @Override
    public void leave(String nickname, int id) throws RemoteException {

        registry = LocateRegistry.getRegistry("127.0.0.1", 4321);
        try {
            server = (GameInterface) registry.lookup("CodexNaturalis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        server.leaveGame(nickname, id);
        gameController = null;
        nickname = null;
    }

    @Override
    public void disconnect(String nickname, int id) throws RemoteException {
        registry = LocateRegistry.getRegistry("127.0.0.1", 4321);
        try {
            server = (GameInterface) registry.lookup("CodexNaturalis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        server.disconnectPlayer(nickname, id);
    }
}

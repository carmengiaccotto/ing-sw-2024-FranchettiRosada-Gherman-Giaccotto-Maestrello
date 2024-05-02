package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class RMIServer extends UnicastRemoteObject implements GameInterface {

    private final List<ClientMoves> clients = new ArrayList<>();

    private static final RMIServer serverObject = null;

    private final GameInterface gameInt;

    public RMIServer(GameInterface gameInt) throws RemoteException {
        this.gameInt = gameInt;
    }

    public RMIServer(int port, GameInterface gameInt) throws RemoteException {
        super(port);
        this.gameInt = gameInt;
    }

    public RMIServer(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, GameInterface gameInt) throws RemoteException {
        super(port, csf, ssf);
        this.gameInt = gameInt;
    }

    @Override
    public GameControllerInterface createGame(String nickname) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.createGame(nickname);
        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){
            }
            System.out.println("[RMI] " + nickname + " created a new game");
        }
        return game;
    }

    @Override
    public GameControllerInterface joinSpecificGame(String nickname, Integer id) throws RemoteException {
         GameControllerInterface game = serverObject.gameInt.joinSpecificGame(nickname, id);
        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){
            }

            System.out.println("[RMI] " + nickname + " joined to specific game with id: " + id);
        }
        return game;

    }

    @Override
    public GameControllerInterface reconnectPlayer(String nickname, Integer id) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.reconnectPlayer(nickname, id);

        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){

            }
            System.out.println("[RMI] " + nickname + " reconnected to game with id: " + id);
        }
        return game;

    }

    @Override
    public GameControllerInterface disconnectPlayer(String nickname, Integer id) throws RemoteException {
        GameControllerInterface game = serverObject.gameInt.disconnectPlayer(nickname, id);

        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            }catch (RemoteException e){

            }
            System.out.println("[RMI] " + nickname + "disconnected");
        }
        return game;

    }

    @Override
    public GameControllerInterface leaveGame(String nickname, Integer id) throws RemoteException{

    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIServer extends UnicastRemoteObject implements GameInterface {

    private static RMIServer serverObject = null;

    private GameInterface gameInt = null;

    private static Registry registry = null;

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

    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(4321);
            getRegistry().rebind("CodexNaturalis", serverObject);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    /**
     * @return the istance of the RMI Server
     */
    public synchronized static RMIServer getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * @return the registry associated with the RMI Server
     * @throws RemoteException
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(0);
        GameController game = GameControllerInterface.getInstance();
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
        serverObject.gameInt.leaveGame(nickname, id);

        return null;
    }
}

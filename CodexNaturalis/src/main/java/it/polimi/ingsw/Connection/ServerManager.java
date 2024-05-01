package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
    private final SocketServer socketServer;
    private final RMIServer rmiServer;

    protected String serverIP;

    protected ServerManager() throws UnknownHostException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            System.err.println("Impossibile ottenere l'indirizzo IP locale: " + e.getMessage());
            throw e;
        }
        socketServer = new SocketServer(12345);

        GameController game = new GameController();
        rmiServer = new RMIServer(game);

    }
    public static void main(String[] args) throws UnknownHostException, RemoteException {
        ServerManager manager=new ServerManager();
        manager.startBothServers();



    }


    public void startSocketServer() {
        socketServer.startServer();
    }

    public void startRMIServer() throws RemoteException {

        rmiServer.startServer();
        System.out.println("RMI Server pronto.");
    }

    public void startBothServers() {
        System.out.println("ServerAddress: "+serverIP);
        startSocketServer();
        try {
            startRMIServer();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}

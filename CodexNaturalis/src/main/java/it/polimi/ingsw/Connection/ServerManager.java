package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
    private SocketServer socketServer;
    private RMIServer rmiServer;
    protected String serverIP;

    protected ServerManager(String serverIP) throws RemoteException, UnknownHostException {
        this.serverIP= InetAddress.getLocalHost().getHostAddress();
        socketServer = new SocketServer(12345);
        GameController gameServer = new GameController();
        rmiServer = new RMIServer(gameServer);

    }

    public String getServerIP() {
        return serverIP;
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

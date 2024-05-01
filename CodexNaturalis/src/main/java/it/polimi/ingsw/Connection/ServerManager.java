package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
    private SocketServer socketServer;
    private RMIServer rmiServer;

    private static GameController game;
    protected String serverIP;

    protected ServerManager() throws RemoteException, UnknownHostException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            System.err.println("Impossibile ottenere l'indirizzo IP locale: " + e.getMessage());
            throw e;
        }
        socketServer = new SocketServer(12345);

        this.game=new GameController();
        rmiServer = new RMIServer(this.game);

    }
    public static void main(String[] args) throws UnknownHostException, RemoteException {
        ServerManager manager=new ServerManager();
        manager.startBothServers();



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

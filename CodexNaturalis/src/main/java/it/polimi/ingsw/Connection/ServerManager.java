package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI.RMIServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
   // private final SocketServer socketServer;
    private final RMIServer rmiServer;

    protected String serverIP;

    protected ServerManager() throws UnknownHostException, RemoteException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            System.err.println("Impossible to obtain local IP address: " + e.getMessage());
            throw e;
        }
        //socketServer = new SocketServer(12345);

        rmiServer = new RMIServer();

    }
    public static void main(String[] args) throws UnknownHostException, RemoteException {
        ServerManager manager=new ServerManager();
        manager.startBothServers();



    }


   // public void startSocketServer() {
   //     socketServer.startServer();
   // }

    public void startRMIServer() throws RemoteException {
        rmiServer.bind();
    }

    public void startBothServers() {
        System.out.println("ServerAddress: "+serverIP);
        //startSocketServer();
        try {
            startRMIServer();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}

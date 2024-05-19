package it.polimi.ingsw.Connection;



import it.polimi.ingsw.Connection.RMI.RMIServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
//    private final SocketServer socketServer;
    private final RMIServer rmiServer;

    protected String serverIP;

    protected ServerManager() throws UnknownHostException, RemoteException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Impossible to obtain local IP address: " + e.getMessage());
            throw e;
        }

        rmiServer = RMIServer.getInstance();
    }
    public static void main(String[] args) throws IOException {
        ServerManager manager=new ServerManager();
        System.out.println("Server Address: "+ manager.serverIP);
        manager.rmiServer.bind();
//      manager.socketServer.startServer();

    }



}

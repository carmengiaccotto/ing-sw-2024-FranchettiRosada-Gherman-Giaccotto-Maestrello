package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIServer;
import it.polimi.ingsw.Connection.Socket.SocketServer;
import it.polimi.ingsw.controller.MainController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
    private final SocketServer socketServer;
    private final RMIServer rmiServer;

    private static MainController principal = new MainController();


    protected String serverIP;

    protected ServerManager() throws UnknownHostException, RemoteException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            System.err.println("Impossible to obtain local IP address: " + e.getMessage());
            throw e;
        }
        socketServer= new SocketServer(12345);
        rmiServer = new RMIServer();

    }
//    public static void main(String[] args) throws IOException {
//        ServerManager manager=new ServerManager();
//        System.out.println("Server Address: "+ manager.serverIP);
//        RMIServer.bind();
//        manager.socketServer.startServer();
//        principal.run();
//    }



}

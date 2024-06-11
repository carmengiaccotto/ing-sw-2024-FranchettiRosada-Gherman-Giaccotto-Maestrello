package it.polimi.ingsw.Connection;


import it.polimi.ingsw.Connection.RMI.RMIServer;
import it.polimi.ingsw.Connection.Socket.Server.SocketServer;
import it.polimi.ingsw.Controller.Main.MainController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ServerManager {
    private final SocketServer socketServer;
    private final RMIServer rmiServer;
    private MainController mainController;

    protected String serverIP;

    protected ServerManager() throws UnknownHostException, RemoteException {
        try {
            this.serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Impossible to obtain local IP address: " + e.getMessage());
            throw e;
        }

        mainController = new MainController();
        rmiServer = RMIServer.getInstance();
        rmiServer.setHandler(mainController);
        socketServer = new SocketServer();
        socketServer.setHandler(mainController);
    }
    public static void main(String[] args) throws IOException {
        ServerManager manager=new ServerManager();
        System.out.println("Server Address: "+ manager.serverIP);
        RMIServer.bind();
        manager.socketServer.startServer();

    }


}

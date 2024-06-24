package it.polimi.ingsw.Connection;


import it.polimi.ingsw.Connection.RMI.RMIServer;
import it.polimi.ingsw.Connection.Socket.Server.SocketServer;
import it.polimi.ingsw.Controller.Main.MainController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Enumeration;

public class ServerManager {
    private final SocketServer socketServer;
    private final RMIServer rmiServer;
    private MainController mainController;

    protected String serverIP;

    protected ServerManager() throws UnknownHostException, RemoteException {
        try {
//            this.serverIP = InetAddress.getLocalHost().getHostAddress();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) { NetworkInterface networkInterface = interfaces.nextElement();
                // Skip loopback interfaces
                if (networkInterface.isLoopback() || !networkInterface.isUp()) { continue; }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                // Filter out IPv6 addresses
                    if (address.isLoopbackAddress() || address.isAnyLocalAddress() || address.isLinkLocalAddress() || address.isMulticastAddress()) { continue; }
                    System.out.println("Interface: " + networkInterface.getDisplayName());
                    System.out.println("IP Address: " + address.getHostAddress());
                    this.serverIP = address.getHostAddress();
                    break;
                }
            }

            } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        mainController = new MainController();
        rmiServer = RMIServer.getInstance();
        rmiServer.setHandler(mainController);
        socketServer = new SocketServer();
        socketServer.setMainController(mainController);
    }
    public static void main(String[] args) throws IOException {
        ServerManager manager=new ServerManager();
        System.out.println("Server Address: "+ manager.serverIP);
        RMIServer.bind(manager.serverIP);
        manager.socketServer.startServer();

    }


}

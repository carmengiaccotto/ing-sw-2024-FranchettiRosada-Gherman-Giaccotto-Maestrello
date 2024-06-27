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

/**
 * This class manages the server connections for the application.
 * It handles both RMI and Socket connections.
 */
public class ServerManager {

    // Socket server instance
    private final SocketServer socketServer;

    // RMI server instance
    private final RMIServer rmiServer;

    // Main controller instance
    private MainController mainController;

    // IP address of the server
    protected String serverIP;

    /**
     * Constructor for the ServerManager class.
     * It initializes the main controller, RMI server, and socket server.
     * It also determines the server IP address.
     *
     * @throws UnknownHostException if the local host name could not be resolved into an address
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    protected ServerManager() throws UnknownHostException, RemoteException {

        try {
            // Get all network interfaces
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

        // Initialize main controller
        mainController = new MainController();
        // Initialize RMI server and set handler
        rmiServer = RMIServer.getInstance();
        rmiServer.setHandler(mainController);
        // Initialize socket server and set main controller
        socketServer = new SocketServer();
        socketServer.setMainController(mainController);
    }

    /**
     * Main method to start the server.
     * It creates a new ServerManager instance, binds the RMI server to the server IP, and starts the socket server.
     *
     * @param args command line arguments
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public static void main(String[] args) throws IOException {
        ServerManager manager=new ServerManager();
        System.out.println("Server Address: "+ manager.serverIP);
        RMIServer.bind(manager.serverIP);
        manager.socketServer.startServer();

    }


}

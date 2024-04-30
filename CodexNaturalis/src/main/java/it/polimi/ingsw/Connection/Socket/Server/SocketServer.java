package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.Server;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Server.GameHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private static int NumPlayers;
    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private static List<Socket> playerSockets = new ArrayList<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(NumPlayers);

    public SocketServer( int port) {
        NumPlayers =4;
        this.port = port;
    }
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("Server Running...");

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Player accepted");

                GameHandler gameHandler = new GameHandler(clientSocket);// giusto?
                //threadPool.execute(() -> gameHandler.handleConnection(clientSocket)); VirtualView come la gestiamo?
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

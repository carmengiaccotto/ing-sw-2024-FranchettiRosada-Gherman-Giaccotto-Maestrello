package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Server.ClientHandler;
import it.polimi.ingsw.Controller.Main.MainController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Thread {

    public static final int SERVERPORT = 2970;
    public static final String SERVERIP = "localhost";

    public static ServerSocket server;
    private ArrayList<ClientHandler> clientHandler;
    private MainController handler;


    public void startServer() throws IOException {
        try {
            server = new ServerSocket(SERVERPORT);
            System.out.println("socket server ready on port: " + SERVERPORT);
            clientHandler = new ArrayList<>();

            while(true) {
                try {
                    Socket s = server.accept();
                    ClientHandler c = new ClientHandler(s);
                    clientHandler.add(c);
                    c.setMainController(handler);
                    Thread thread = new Thread(c);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();
    }

    public void setHandler(MainController handler){this.handler = handler;}

//    public void stopConnection() {
//        if (clientHandler != null)
//            for (ClientHandler c : clientHandler) {
//                c.interruptThread();
//            }
//        this.interrupt();
//    }

}


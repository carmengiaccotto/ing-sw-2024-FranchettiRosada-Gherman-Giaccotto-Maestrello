package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer extends Thread {

    public static final int SERVERPORT = 2970;

    public static ServerSocket server;
    private MainController mainController;


    public void startServer() throws IOException {
        try {
            server = new ServerSocket(SERVERPORT);
            System.out.println("socket server ready on port: " + SERVERPORT);

            while (true) {
                try {
                    Socket socket = server.accept();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ServerCallsToClient client = new ServerCallsToClient(oos, ois, mainController);

//                    ClientHandler c = new ClientHandler(s);
//                    clientHandler.add(c);
//                    c.setMainController(handler);
//                    Thread thread = new Thread(c);
//                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

//    public void stopConnection() {
//        if (clientHandler != null)
//            for (ClientHandler c : clientHandler) {
//                c.interruptThread();
//            }
//        this.interrupt();
//    }

}


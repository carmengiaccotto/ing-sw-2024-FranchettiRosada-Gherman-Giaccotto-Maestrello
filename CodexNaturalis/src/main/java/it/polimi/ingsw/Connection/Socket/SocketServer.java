package it.polimi.ingsw.Connection.Socket;

import it.polimi.ingsw.Controller.Main.MainController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer extends Thread {

    public static final int SERVERPORT = 2970;

    public static final String SERVERIP = "localhost";

    public static ServerSocket server = null;
    public static Socket clientSocket = null;
    //private ObjectOutputStream out;
    //private ObjectInputStream in;

    private ArrayList<ClientHandler> clientHandler;

    private MainController handler;


    public void startServer() throws IOException {
        try {

            server = new ServerSocket(SERVERPORT);

            System.out.println("socket server ready on port: "+SERVERPORT);
            clientHandler = new ArrayList<>();

            try {
                while (true) {
                    Socket s = server.accept();
                    ClientHandler c = new ClientHandler(s);
                    clientHandler.add(c);
                    clientHandler.get(clientHandler.size()-1).setMainController(handler);
                    clientHandler.get(clientHandler.size()-1).run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




            //this.start();
            //clientSocket = server.accept();


            //out = new ObjectOutputStream(clientSocket.getOutputStream());
            //in = new ObjectInputStream(clientSocket.getInputStream());

            //handler.connect(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void run() {
//
//    }

    public void stopConnection() {
        if (clientHandler != null)
            for (ClientHandler c : clientHandler) {
                c.interruptThread();
            }
        this.interrupt();
    }


    public void setHandler(MainController handler){
        this.handler = handler;
    }



//    ArrayList<GameControllerInterface> games;
//    private BufferedInputStream in;
//
//    public SocketServer(int port){
//        this.port=port;
//    }
//    public void startServer()  {
//        ExecutorService executor= Executors.newCachedThreadPool();
//        try {
//            serverSocket= new ServerSocket(this.port);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("socket server ready on port: "+port);
//        while(true){
//            try {
//                Socket socket=serverSocket.accept();
//                executor.submit(new SocketClientHandler(socket));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("new client connected");
//
//        }
//
//
//
//    }
//
//
//    @Override
////    public GameControllerInterface createGame(String nickname) throws RemoteException {
////        requests.createGame(nickname);
////    }
//
//    @Override
//    public GameControllerInterface joinExistingGame(String nickname) throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public GameControllerInterface reconnectPlayer(String nickname) throws RemoteException {
//        return null;
//    }
//
//    @Override
//    public GameControllerInterface disconnectPlayer(String nickname) throws RemoteException {
//        return null;
//    }
}


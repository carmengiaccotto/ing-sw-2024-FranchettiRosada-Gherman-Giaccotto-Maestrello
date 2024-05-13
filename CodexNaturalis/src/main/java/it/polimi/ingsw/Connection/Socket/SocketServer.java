package it.polimi.ingsw.Connection.Socket;

//public class SocketServer implements GameInterface {
//    ArrayList<GameControllerInterface> games;
//    private final int port;
//    private ServerSocket serverSocket;
//    private BufferedInputStream in;
//    private MainController requests;
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
//}


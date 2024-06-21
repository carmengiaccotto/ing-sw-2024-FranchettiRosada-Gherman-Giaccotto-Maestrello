//package it.polimi.ingsw.Connection.Socket.Client;
//
//import it.polimi.ingsw.Connection.Socket.Messages.GenericMessage;
//import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//public class ReceiveFromServer implements Runnable{
//
//    private ClientControllerInterface clientController;
//
//    private Socket server;
//    private ObjectInputStream in;
//
//    public ReceiveFromServer(Socket s) throws IOException {
//        this.server = s;
//        this.in = new ObjectInputStream(server.getInputStream());
//    }
//
//    @Override
//    public void run() {
//        GenericMessage receiveMessage;
//        while(true){
//            try {
//                receiveMessage = (GenericMessage) in.readObject();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//
//
//        }
//
//    }
//
//    public ClientControllerInterface getClientController() {
//        return clientController;
//    }
//
//    public void setClientController(ClientControllerInterface clientController) {
//        this.clientController = clientController;
//    }
//
//}

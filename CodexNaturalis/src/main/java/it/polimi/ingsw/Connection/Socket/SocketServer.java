package it.polimi.ingsw.Connection.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer  {
    private int port;
    private ServerSocket serverSocket;

    public SocketServer(int port){
        this.port=port;
    }
    public void startServer() throws IOException {
        serverSocket= new ServerSocket(this.port);
        System.out.println("socket server ready on port: "+port);
        Socket socket=serverSocket.accept();
        System.out.println("new client connected");


    }


}


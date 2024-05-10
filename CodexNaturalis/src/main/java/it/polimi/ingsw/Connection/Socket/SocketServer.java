package it.polimi.ingsw.Connection.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer implements Runnable {

    private ArrayList<SocketConnectionHandler> connectedPlayers;
    @Override
    public void run(){
        try {
            ServerSocket server = new ServerSocket(4789);
            Socket client=server.accept();
            SocketConnectionHandler handler=new SocketConnectionHandler(client);
            connectedPlayers.add(handler);
        }catch (IOException e){
            System.out.println("Unable to run Server");
        }


    }



}


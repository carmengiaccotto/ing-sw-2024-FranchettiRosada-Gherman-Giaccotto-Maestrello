package it.polimi.ingsw.Connection.Socket;

import it.polimi.ingsw.Connection.Socket.ClientToControllerMessage.ClientToControllerMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient{
    private String ip;
    private int port;
    private Socket client;

    public SocketClient(){
    }

    public void start() throws IOException {
        Socket socket=new Socket(ip, port);
        System.out.println("Connected client");
        Scanner socketIn=new Scanner(socket.getInputStream());
        PrintWriter socketOut=new PrintWriter(socket.getOutputStream());
        Scanner stdin=new Scanner(System.in);

    }



    public void connect() {

    }

    public void disconnect() {

    }

    public void sendMessage(ClientToControllerMessage m){


    }


//     public void receiveMessage(ControllerToClientMessages m){
//
//
//     }
}

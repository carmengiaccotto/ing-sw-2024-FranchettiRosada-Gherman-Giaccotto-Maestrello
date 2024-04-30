package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    public SocketClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }
    public void connectToServer() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Connected");

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void disconnectFromServer() {

        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}

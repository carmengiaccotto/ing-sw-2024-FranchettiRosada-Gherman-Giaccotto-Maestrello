package CodexNaturalis.src.main.java.it.polimi.ingsw.Client.Socket;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Client.ClientConnectionInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.Client.ClientToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection implements ClientConnectionInterface {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String serverAddress;
    private int port;

    public SocketClientConnection(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }
    @Override
    public void connect() throws IOException {
        socket = new Socket(serverAddress, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }



    @Override
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }

    }

    @Override
    public void sendMessage(ClientToServerMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();

    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        return (Message) inputStream.readObject();
    }
}

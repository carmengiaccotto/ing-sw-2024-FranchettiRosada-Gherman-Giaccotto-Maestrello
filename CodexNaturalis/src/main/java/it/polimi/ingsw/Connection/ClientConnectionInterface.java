package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import java.io.IOException;

public interface ClientConnectionInterface {
    void connect() throws IOException;
    void disconnect() throws IOException;
    void sendMessage(ClientToServerMessage message) throws IOException;
    Message receiveMessage() throws IOException, ClassNotFoundException;


}

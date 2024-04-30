package CodexNaturalis.src.main.java.it.polimi.ingsw.Client;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

import java.io.IOException;

public interface ClientConnectionInterface {
    void connect() throws IOException;
    void disconnect() throws IOException;
    void sendMessage(ClientToServerMessage message) throws IOException;
    Message receiveMessage() throws IOException, ClassNotFoundException;


}

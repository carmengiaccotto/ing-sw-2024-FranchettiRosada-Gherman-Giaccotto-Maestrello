package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientActions;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

public class ClientChatMessage extends ClientToControllerMessage {
    private String senderNickName;
    //sender
    private ClientActions action;

    private Message messageToSend;
    //Aggiungi parametro per capire a chi mandare?

    public ClientChatMessage(Message messageToSend, String senderNickName) {
        super(senderNickName);
        this.action = ClientActions.CHAT;
        this.messageToSend = messageToSend;

    }


    public void execute(GameControllerInterface game){
        game.sentMessage(messageToSend);
    }
}

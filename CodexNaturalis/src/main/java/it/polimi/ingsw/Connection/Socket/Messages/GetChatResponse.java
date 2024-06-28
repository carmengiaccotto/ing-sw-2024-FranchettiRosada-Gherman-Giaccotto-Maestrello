package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.Chat.Chat;

public class GetChatResponse extends GenericMessage {
    private final Chat chat;

    public GetChatResponse(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }
}

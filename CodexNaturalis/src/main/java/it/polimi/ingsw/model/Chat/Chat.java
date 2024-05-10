package it.polimi.ingsw.model.Chat;

import it.polimi.ingsw.model.DefaultValue;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> message;

    /**
     * Constructor
     */
    public Chat() {
        message = new ArrayList<>();
    }

    /**
     *
     * @return the list of messages
     */
    public ArrayList<Message> getMessage() {
        return message;
    }

    /**
     * Adds a message
     * @param m message param
     */
    public void addMessage(Message m) {
        if (message.size() > DefaultValue.max_messagesShown)
            message.remove(0);
        message.add(m);
    }

    /**
     * Sets the chat messages
     *
     * @param message
     */
    public void setMessage(ArrayList<Message> message) {
        this.message = message;
    }


}

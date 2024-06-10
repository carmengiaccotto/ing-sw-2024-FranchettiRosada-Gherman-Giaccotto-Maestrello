package it.polimi.ingsw.Model.Chat;


import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
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
        int max_messagesShown = 1000;
        if (message.size() == max_messagesShown)
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

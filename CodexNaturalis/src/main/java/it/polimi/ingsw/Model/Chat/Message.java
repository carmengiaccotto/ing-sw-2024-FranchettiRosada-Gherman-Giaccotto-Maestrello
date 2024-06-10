package it.polimi.ingsw.Model.Chat;

import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    private String text;
    private Player sender;
    private LocalTime time;

    /**
     * Constructor
     */
    public Message(String text) {
        this.time = null;
        this.text = text;
        this.sender = null;
    }

    /**
     *
     * @return the message's text content
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text in the message to the param
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return the message's sender
     */
    public Player getSender() {
        return sender;
    }

    /**
     * Sets the message's sender
     * @param sender sender
     */
    public void setSender(Player sender) {
        this.sender = sender;
    }

    /**
     *
     * @return the message's time of sending
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the message time to the parameter
     * @param time param
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     *
     * @return "everyone" (everyone is a receiver)
     */
    public void whoIsReceiver() {
    }
}

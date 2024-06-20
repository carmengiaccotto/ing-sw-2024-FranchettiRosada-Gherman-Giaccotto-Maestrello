package it.polimi.ingsw.Model.Chat;

import it.polimi.ingsw.Model.PlayGround.Player;

public class PrivateMessage extends Message{

    private Player sender;
    private String receiver;
    /**
     * Constructor
     *
     * @param text
     */
    public PrivateMessage(String text) {
        super(text);
        this.sender=super.getSender();
        this.receiver=null;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

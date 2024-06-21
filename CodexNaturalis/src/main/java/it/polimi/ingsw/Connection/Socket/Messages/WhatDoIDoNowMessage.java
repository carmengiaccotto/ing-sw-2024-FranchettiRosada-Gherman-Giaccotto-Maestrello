package it.polimi.ingsw.Connection.Socket.Messages;

public class WhatDoIDoNowMessage extends GenericMessage {
    private String doThis;

    public WhatDoIDoNowMessage(String doThis) {
        super();
        this.doThis = doThis;
    }

    public String getDoThis() {
        return doThis;
    }
}

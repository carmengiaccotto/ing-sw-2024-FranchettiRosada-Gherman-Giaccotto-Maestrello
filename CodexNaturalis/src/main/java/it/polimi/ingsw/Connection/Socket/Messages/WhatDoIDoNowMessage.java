package it.polimi.ingsw.Connection.Socket.Messages;

public class WhatDoIDoNowMessage extends GenericMessage {
    private final String doThis;

    public WhatDoIDoNowMessage(String doThis) {
        this.doThis = doThis;
    }

    public String getDoThis() {
        return doThis;
    }
}

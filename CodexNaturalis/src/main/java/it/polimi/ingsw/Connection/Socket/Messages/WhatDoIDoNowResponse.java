package it.polimi.ingsw.Connection.Socket.Messages;

public class WhatDoIDoNowResponse extends GenericMessage {
    private Object object;
    public WhatDoIDoNowResponse(Object object) {
        this.object = object;
    }
    public Object getObject() {
        return object;
    }
}

package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a "What Do I Do Now" response message.
 * It does not contain any additional fields or methods.
 */
public class WhatDoIDoNowResponse extends GenericMessage {
    private Object object;
    public WhatDoIDoNowResponse(Object object) {
        this.object = object;
    }
    public Object getObject() {
        return object;
    }
}
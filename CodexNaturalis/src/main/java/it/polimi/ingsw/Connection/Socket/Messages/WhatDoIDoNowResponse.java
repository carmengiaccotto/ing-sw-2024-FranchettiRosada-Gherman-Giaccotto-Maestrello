package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a "What Do I Do Now" response message.
 * It does not contain any additional fields or methods.
 */
public class WhatDoIDoNowResponse extends GenericMessage {
        /**
        * Represents an object that can be any type.
         */
    private Object object;

    /**
     * Constructs a new WhatDoIDoNowResponse with the specified object.
     *
     * @param object The object to be stored in this WhatDoIDoNowResponse.
     */
    public WhatDoIDoNowResponse(Object object) {
        this.object = object;
    }

    /**
     * Returns the object stored in this WhatDoIDoNowResponse.
     *
     * @return The object stored in this WhatDoIDoNowResponse.
     */
    public Object getObject() {
        return object;
    }
}
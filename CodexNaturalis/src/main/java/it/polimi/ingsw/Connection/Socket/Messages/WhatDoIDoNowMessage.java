package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a "What Do I Do Now" message.
 * It contains a String object representing the action to be performed.
 */
public class WhatDoIDoNowMessage extends GenericMessage {
    // The action to be performed.
    private final String doThis;

    /**
     * Constructs a new WhatDoIDoNowMessage with the specified action.
     * @param doThis the action to be performed
     */
    public WhatDoIDoNowMessage(String doThis) {
        this.doThis = doThis;
    }

    /**
     * Returns the action to be performed.
     * @return the action to be performed
     */
    public String getDoThis() {
        return doThis;
    }
}

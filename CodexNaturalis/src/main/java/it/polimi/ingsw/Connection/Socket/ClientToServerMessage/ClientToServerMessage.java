package it.polimi.ingsw.Connection.Socket.ClientToServerMessage;

import java.io.Serializable;

public abstract class ClientToServerMessage implements Serializable {
    private final String message;
    private Object object;

    private boolean isMessageForMainController;

    public ClientToServerMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public boolean isMessageForMainController(){
        return isMessageForMainController;
    }

}

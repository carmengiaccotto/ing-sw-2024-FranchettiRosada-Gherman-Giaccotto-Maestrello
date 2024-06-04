package it.polimi.ingsw.Connection.Socket.ServerToClientMessage;

import java.io.Serializable;

public class ServerToClientMessage implements Serializable {
    private final String message;
    private Object object;
    public ServerToClientMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public void setObject(Object o){
        this.object = o;
    }

}

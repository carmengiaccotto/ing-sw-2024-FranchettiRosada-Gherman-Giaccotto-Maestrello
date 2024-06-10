package it.polimi.ingsw.Connection.Socket;

import java.io.Serializable;

public class GenericMessage implements Serializable {
    private final String message;
    private Object object;

    //private boolean isForMainController;

    //public void setIsForMainController(boolean b){this.isForMainController = b;}
    //public boolean getIsForMainController(){return isForMainController;}
    public GenericMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public void setObject(Object o){
        this.object = o;
    }
    public Object getObject(){
        return object;
    }

}

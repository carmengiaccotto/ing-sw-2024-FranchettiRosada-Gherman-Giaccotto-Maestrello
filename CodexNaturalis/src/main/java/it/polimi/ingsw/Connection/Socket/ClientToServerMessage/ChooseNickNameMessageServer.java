package it.polimi.ingsw.Connection.Socket.ClientToServerMessage;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;

import java.io.IOException;
import java.io.Serializable;

public class ChooseNickNameMessageServer extends ClientToServerMessage implements Serializable {

    private String Nickname;
    //private ClientControllerInterface client;
    //private MainControllerInterface controller;


    public ChooseNickNameMessageServer(String nickname) {
        super("ChooseNickname");
        //this.client = p;
        this.Nickname = nickname;
    }
    public void execute(GameControllerInterface game) throws IOException, ClassNotFoundException {

        //controller.checkUniqueNickName(Nickname, client);

    }

    public String getNickname(){
        return Nickname;
    }
}

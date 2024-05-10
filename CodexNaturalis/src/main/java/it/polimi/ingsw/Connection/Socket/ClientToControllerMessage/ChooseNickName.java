package it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import it.polimi.ingsw.Connection.Socket.ClientActions;
import it.polimi.ingsw.controller.GameControllerInterface;
//Questo è un metodo gestito dal main?
public class ChooseNickName  {
    private String Nickname;
    private ClientActions action;

    public ChooseNickName(String nickname, ClientActions action) {
        this.Nickname = nickname;
        this.action=ClientActions.SELECT_NICKNAME;
    }

    public void execute(GameControllerInterface game){

    }
}

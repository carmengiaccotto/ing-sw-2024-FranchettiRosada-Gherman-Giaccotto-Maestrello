package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientActions;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
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

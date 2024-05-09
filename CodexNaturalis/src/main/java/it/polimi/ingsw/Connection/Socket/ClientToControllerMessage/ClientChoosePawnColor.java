package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientActions;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;

public class ClientChoosePawnColor extends ClientToControllerMessage {
    private ClientActions action;
    private PawnColor color;

    public ClientChoosePawnColor(PawnColor color, String senderNickName) {
        super(senderNickName);
        this.color = color;
        this.action=ClientActions.CHOOSE_PAWN_COLOR;
    }


    @Override
    public void execute(GameControllerInterface game) {
       game.SetPlayerPawnColorChoice(color);
    }
}

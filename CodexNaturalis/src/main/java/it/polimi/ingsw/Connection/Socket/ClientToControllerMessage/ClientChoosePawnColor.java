package it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import it.polimi.ingsw.Connection.Socket.ClientActions;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Enumerations.PawnColor;

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
 //      game.setPlayerPawnColorChoice(color);
    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.Client.Socket;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Client.ClientToServerMessage;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;

public class SocketChoosePawnColorMessage extends ClientToServerMessage {

    private final PawnColor chosenColor;
    public SocketChoosePawnColorMessage(PawnColor chosenColor, String nickname){
        this.chosenColor=chosenColor;
        this.SenderNickname=nickname;
        //typeOfMessage=ClientToServerMessageType.PAWN_COLOR_CHOICE; serve?
    }

    @Override
    public void execute(GameControllerInterface gameController) {
        gameController.SetPlayerPawnColorChoice(chosenColor);

    }
}

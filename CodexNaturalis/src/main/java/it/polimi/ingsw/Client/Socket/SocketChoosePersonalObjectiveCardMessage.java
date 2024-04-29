package CodexNaturalis.src.main.java.it.polimi.ingsw.Client.Socket;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Client.ClientToServerMessage;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;

public class SocketChoosePersonalObjectiveCardMessage extends ClientToServerMessage {

    private final ObjectiveCard ChosenObjectiveCard;

    public SocketChoosePersonalObjectiveCardMessage(ObjectiveCard chosenObjectiveCard, String senderNickname) {
        ChosenObjectiveCard = chosenObjectiveCard;
        this.SenderNickname=senderNickname;
    }

    @Override
    public void execute(GameControllerInterface gameController) {
        gameController.

    }
}

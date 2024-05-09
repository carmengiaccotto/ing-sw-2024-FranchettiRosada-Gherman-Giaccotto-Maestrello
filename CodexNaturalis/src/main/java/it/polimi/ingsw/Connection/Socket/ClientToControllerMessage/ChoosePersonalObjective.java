package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Socket.ClientActions;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;

public class ChoosePersonalObjective extends ClientToControllerMessage{
    private ObjectiveCard chosenObjective;
    private ClientActions action;

    public ChoosePersonalObjective(ObjectiveCard chosenObjective, String senderNickName) {
        super(senderNickName);
        this.chosenObjective = chosenObjective;
        this.action=ClientActions.CHOOSE_OBJECTIVE_CARD;
    }

    @Override
    public void execute(GameControllerInterface game) {
        //game.

    }
}

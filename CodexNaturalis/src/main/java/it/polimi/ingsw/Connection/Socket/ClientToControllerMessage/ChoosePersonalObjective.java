package it.polimi.ingsw.Connection.Socket.ClientToControllerMessage;

import it.polimi.ingsw.Connection.Socket.ClientActions;
import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.model.Cards.ObjectiveCard;

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

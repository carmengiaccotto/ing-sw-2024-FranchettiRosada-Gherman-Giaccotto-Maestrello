package it.polimi.ingsw.Connection.Socket.ClientToServerMessage;

import it.polimi.ingsw.Connection.Socket.ClientActions;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;

public class ChoosePersonalObjectiveMessage extends ClientToControllerMessage{
    private ObjectiveCard chosenObjective;
    private ClientActions action;

    public ChoosePersonalObjectiveMessage(ObjectiveCard chosenObjective, String senderNickName) {
        super(senderNickName);
        this.chosenObjective = chosenObjective;
        this.action=ClientActions.CHOOSE_OBJECTIVE_CARD;
    }

    @Override
    public void execute(GameControllerInterface game) {
        //game.

    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.Client;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;

public abstract class ClientToServerMessage {

    protected String SenderNickname;
    protected ClientToServerMessageType typeOfMessage;

    public abstract void execute(GameControllerInterface gameController);
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.View;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Card;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface UserInterface {

    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException;
    public void choosePersonalObjectiveCard(String nickname);

    public void playInitialCard(Card c);

    public void playCard(String nickname);

    public void drawCard();

    public void showBoardAndPlayAreas();

}

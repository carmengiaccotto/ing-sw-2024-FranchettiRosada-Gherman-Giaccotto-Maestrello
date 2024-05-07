package CodexNaturalis.src.main.java.it.polimi.ingsw.View;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public abstract class UserInterface {

    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException {

    }
}

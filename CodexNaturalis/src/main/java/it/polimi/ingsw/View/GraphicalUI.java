package it.polimi.ingsw.View;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import it.polimi.ingsw.model.Exceptions.NotReadyToRunException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GraphicalUI implements UserInterface{

    public void run() {

    }


    @Override
    public void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException, IOException {

    }

    @Override
    public void choosePersonalObjectiveCard(String nickname) {

    }

    @Override
    public void playInitialCard(Card c) {

    }

    @Override
    public void playCard(String nickname) {

    }

    @Override
    public void drawCard() {

    }

    @Override
    public void showBoardAndPlayAreas() {

    }
}

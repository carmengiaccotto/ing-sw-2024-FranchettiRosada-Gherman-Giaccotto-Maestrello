
// This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes


import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The RMIClient class extends the UnicastRemoteObject and implements the ClientMoves interface.
 * It represents a client in the RMI connection.
 */

//estende ConnectionInterface
public class RMIClient extends UnicastRemoteObject implements Serializable, ClientControllerInterface {
    private MainControllerInterface server;
    private ClientControllerInterface controller;
    private Registry registry;



    /**
     * The constructor for the RMIClient class.
     * @throws RemoteException if the remote object cannot be created
     */

    public RMIClient() throws RemoteException {
        //this.server=server;
        this.controller=new ClientController();
    }

    public void setController(ClientControllerInterface clientController){
        this.controller = clientController;
    }


    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        controller.setServer(server);
    }

    /**Method that sets the view of the player to the chosen type. Uses ClientController implementation
     * @param view  GUI or TUI*/
    @Override
    public void setView(UserInterface view) throws RemoteException {
        controller.setView(view);
    }


    public void disconnect() throws RemoteException {

    }

    /**
     * Method that allows the player to choose its pawn color from a list of available ones. Uses ClientController implementation.
     */
    @Override
    public void ChoosePawnColor() throws RemoteException {
        controller.ChoosePawnColor();
    }


    /**
     * Method that allows the player to choose which game to join from a list of Games created y other players.
     * Uses ClientController Implementation
     */
    @Override
    public void JoinGame() throws RemoteException {
        controller.JoinGame();

    }

    /**Nickname getter method*/
    @Override
    public String getNickname() throws RemoteException {
        return controller.getNickname();
    }

    @Override
    public void setNickname(String nickname) throws RemoteException {
        controller.setNickname(nickname);
    }

    @Override
    public void JoinOrCreateGame() throws RemoteException {
        controller.JoinOrCreateGame();

    }

    @Override
    public String ChooseNickname() throws IOException, ClassNotFoundException {
        return controller.ChooseNickname();

    }

    @Override
    public void newGameSetUp() throws RemoteException {
        controller.newGameSetUp();

    }


    @Override
    public void Wait() throws RemoteException {
        controller.Wait();

    }

    @Override
    public PawnColor getPawnColor() throws RemoteException {
        return controller.getPawnColor();
    }

    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {
        controller.setPersonalObjectiveCard(objectiveCard);

    }

    @Override
    public Player getPlayer() throws RemoteException {
        return controller.getPlayer();
    }

    @Override
    public void showBoardAndPlayAreas(PlayGround m) throws RemoteException {

    }

    @Override
    public PlayGround chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
        controller.sendUpdateMessage(message);
    }

    @Override
    public  void connect(String ipAddress) throws RemoteException{
        boolean connected = false;
        int attempt = 1;

        while (!connected && attempt <= 4) {
            try {
                Thread connectionThread = new Thread(() -> {
                    // ...
                    try {
                        registry = LocateRegistry.getRegistry(ipAddress, 8344);
                        Object obj = registry.lookup("CodexNaturalis");
                        Class<?>[] interfaces = obj.getClass().getInterfaces();
                        server = (MainControllerInterface) obj;
                        setServer(server);

                    } catch (Exception e) {
                        System.out.println("[ERROR] Connecting to server: \n\tClient exception: " + e + "\n");
                    }
                });
                connectionThread.start();
                connectionThread.join(1000);

                if (server != null) {
                    connected = true;
                } else {
                    System.out.println("[#" + attempt + "]Waiting to reconnect to Server on port: '" + 8344 + "' with name: '" + "CodexNaturalis" + "'");
                    attempt++;
                    if (attempt <= 4) {
                        System.out.println("Retrying...");
                        Thread.sleep(1000);
                    } else {
                        System.out.println("Give up!");
                        System.exit(-1);
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            controller.connect(ipAddress);
        }
    }

    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
        controller.addCardToHand(card);
    }

    @Override
    public int getScore() throws RemoteException {
        return controller.getScore();
    }

    @Override
    public int getRound() throws RemoteException
    {
        return controller.getRound();
    }

    @Override
    public void setRound(int round) throws RemoteException {
        controller.setRound(round);
    }


    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        controller.setGame(game);
    }

    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return controller.getPersonalObjectiveCard();
    }

    @Override
    public void JoinLobby() throws RemoteException {
        controller.JoinLobby();
    }

    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {
        controller.WhatDoIDoNow(doThis);
    }


}


// This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes


import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * The RMIClient class extends the UnicastRemoteObject and implements the ClientMoves interface.
 * It represents a client in the RMI connection.
 */
public class RMIClient extends UnicastRemoteObject implements Serializable, ClientControllerInterface {
    private MainControllerInterface server;
    private ClientControllerInterface controller;
    private Registry registry;



    /**
     * The constructor for the RMIClient class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIClient(MainControllerInterface server) throws RemoteException {
        this.server=server;
        this.controller=new ClientController(server);
    }



    public  void connect() {
        boolean connected = false;
        int attempt = 1;

        while (!connected && attempt <= 4) {
            try {
                Thread connectionThread = new Thread(() -> {
                    try {
                        registry = LocateRegistry.getRegistry("127.0.0.1", 6322);
                        server = (MainControllerInterface) registry.lookup("CodexNaturalis");
                        System.out.println("Client ready");
                    } catch (Exception e) {
                        System.out.println("[ERROR] Connecting to server: \n\tClient exception: " + e + "\n");
                    }
                });
                connectionThread.start();
                connectionThread.join(1000);

                if (server != null) {
                    connected = true;
                } else {
                    System.out.println("[#" + attempt + "]Waiting to reconnect to Server on port: '" + 6322 + "' with name: '" + "CodexNaturalis" + "'");
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
        }

        try {
            server.connect(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
     /**Method that sets the view of the player to the chosen type. Uses ClientController implementation
      * @param view  GUI or TUI*/
    @Override
    public void setView(UserInterface view) {
        controller.setView(view);
    }


    public void disconnect() throws RemoteException {

    }

    /**Method that allows the player to choose its pawn color from a list of available ones. Uses ClientController implementation.
     * @param availableColors colors that have not already been taken by other players*/
    @Override
    public void ChoosePawnColor(ArrayList<PawnColor> availableColors) throws RemoteException {
        controller.ChoosePawnColor(availableColors);
    }


    /**Method that allows the player to choose which game to join from a list of Games created y other players.
     * Uses ClientController Implementation
     * @param availableGames list of games the player can join*/
    @Override
    public void getGameToJoin(ArrayList<GameController> availableGames) throws RemoteException {
        controller.getGameToJoin(availableGames);

    }

    /**Nickname getter method*/
    @Override
    public String getNickname() {
        return controller.getNickname();
    }

    @Override
    public void JoinOrCreateGame() throws RemoteException {
        controller.JoinOrCreateGame();

    }

    @Override
    public void ChooseNickname() throws RemoteException {
        controller.ChooseNickname();

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
    public void updatePlayers(List<ClientControllerInterface> players) throws RemoteException {
        controller.updatePlayers(players);

    }


    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) {
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
    public PlayCard chooseCardToDraw(PlayGround m) throws RemoteException {
        return null;
    }

    @Override
    public Command receiveCommand() {
        return null;
    }

    @Override
    public String chooseSideInitialCard(InitialCard c) {
        return null;
    }

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) throws RemoteException {
        return 0;
    }


}

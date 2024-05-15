
// This package contains the classes related to the RMI (Remote Method Invocation) connection.

package it.polimi.ingsw.Connection.RMI;

// Importing the necessary classes


import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Connection.VirtualClient;
import it.polimi.ingsw.Connection.VirtualServer;
import it.polimi.ingsw.View.TUI.TUI;
import it.polimi.ingsw.View.UserInterface;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The RMIClient class extends the UnicastRemoteObject and implements the ClientMoves interface.
 * It represents a client in the RMI connection.
 */
public class RMIClient extends UnicastRemoteObject implements VirtualClient, Serializable {
    private VirtualServer server;
    private GameController game;

    private UserInterface view;

    // The registry for the RMI connection
    private Registry registry;

    // The nickname of this client
    private String nickname;

    private final Player player;


    /**
     * The constructor for the RMIClient class.
     * @throws RemoteException if the remote object cannot be created
     */
    public RMIClient(VirtualServer server) throws RemoteException {
        this.server=server;
        this.player=new Player();
        this.game=new GameController(0,2);
    }


    @Override
    public  void askTypeOfView() {
        System.out.println("Select View Type: ");
        System.out.println("1. TUI");
        System.out.println("2. GUI");
        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();
        switch (choice){
            case 1-> this.view=new TUI();
            default->{
                System.out.println("Invalid Choice ");
                askTypeOfView();
            }
        }

    }

    @Override
    public  void connect() {
        boolean connected = false;
        int attempt = 1;

        while (!connected && attempt <= 4) {
            try {
                Thread connectionThread = new Thread(() -> {
                    try {
                        registry = LocateRegistry.getRegistry("127.0.0.1", 6322);
                        server = (VirtualServer) registry.lookup("CodexNaturalis");
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
            askTypeOfView();
            server.connect(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() throws RemoteException {

    }


    @Override
    public void setPawnColor(ArrayList<PawnColor> availableColors) {
        int choice = view.displayAvailableColors(availableColors);
        if(choice<=0 || choice> availableColors.size()){
            System.out.println( "Invalid color, please select a new One");
            setPawnColor(availableColors);
        }
        else
            player.setPawnColor(availableColors.get(choice-1));


    }



    @Override
    public  String getNickname() {
        return this.nickname;
    }

    @Override
    public void setNickname() throws RemoteException {
        String nickname=view.selectNickName();
        if(server.checkUniqueNickName(nickname)){
            this.nickname=nickname;
            this.player.setNickname(nickname);

        }
        else
            setNickname();

    }


    @Override
    public  void JoinOrCreateGame () throws RemoteException {
        int choice= view.createOrJoin();
        switch (choice) {
            case 0 -> server.createGame(this);
            case 1 -> {
                ArrayList<GameController> availableGames= server.DisplayAvailableGames();
                if (!availableGames.isEmpty()) {
                    getGameToJoin();
                } else {
                    System.out.println("No game Available, please create a new one.");
                    server.createGameSynchronized(this);
                }
            }
            default->{
                System.out.println("Invalid choice ");
                JoinOrCreateGame();
            }
        }
    }

    @Override
    public int newGameSetUp() {
        int n=view.MaxNumPlayers();
        if(n>=2 && n<=4) {
            return n;
        }
        else{
            System.out.println("Invalid number of players");
            newGameSetUp();
            return 0;
        }

    }



    public void getGameToJoin() throws RemoteException {
        ArrayList<GameController> availableGames= server.DisplayAvailableGames();
        if (availableGames.isEmpty()) {
            System.out.println("Sorry, there are no Available games, you have to start a new one");
            server.createGameSynchronized(this);
        } else {
            int chosenGame = view.displayavailableGames(availableGames);
            if (chosenGame == 0) {
                server.createGameSynchronized(this);
            } else if (chosenGame < 0 || chosenGame > availableGames.size()) {
                System.out.println("Invalid game, please select a valid one, or start a new game");
                JoinOrCreateGame();

            } else {
                game= availableGames.get(chosenGame - 1);
                server.joinGame(this, game.getId());
            }
        }
    }
    public GameController getGame() throws RemoteException{
        return this.game;
    }

    @Override
    public void updatePlayers(List<VirtualClient> players) throws RemoteException {
        this.game.setPlayers(players);
    }

    @Override
    public UserInterface getView() throws RemoteException {
        return this.view;
    }

    @Override
    public void Wait() throws RemoteException {
        view.waitingForPlayers();
    }


    public Player getPlayer() throws RemoteException{
        return this.player;
    }



}

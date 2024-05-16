package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumerations.GameStatus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class MainController implements MainControllerInterface{
    /**ArrayList of Connected Clients*/
    private final ArrayList<VirtualClient> clients;

    /**ArrayList of Games that are currently running on the server*/
    private final ArrayList<GameController> runningGames;

    /**Thread pool for Multi-Game advanced feature*/
    private ExecutorService executor;
    /** Thread pool for async communication and task management*/
    private final ExecutorBuffer executorBuffer = new ExecutorBuffer();

    public MainController() {
        this.clients = new ArrayList<>();
        this.runningGames=new ArrayList<>();
    }


    /**Method that adds the Client to the list of connected Client, and adds it to the lobby
     * @param client  that connected*/
    @Override
    public void connect(VirtualClient client) throws RemoteException {
        System.out.println("New Client Connected");
        clients.add(client);
        addClientToLobby(client);
    }
    /**Method that adds the Client to a lobby.
     * @param client  new client in the lobby */
    @Override
    public void addClientToLobby(VirtualClient client) throws RemoteException {
        Runnable task=()-> {
            try {
                client.setNickname();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            try {
                client.JoinOrCreateGame();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        };
        executorBuffer.execute(task);

    }


    /**Method that checks if the Client is trying to choose a nickname that has already been chosen by
     * another client. If the nickname is already taken, the player is sent back to the lobby, in
     * order to choose a new one, else the client's nickname is set to the chosen one
     * @param client that is currently choosing the nickname
     * @param name nickname that the client wants to choose*/
    @Override
    public void checkUniqueNickName(String name, VirtualClient client) throws RemoteException {
        Runnable task=()-> {
            boolean b = clients.size() > 1;
            {
                for (VirtualClient c : clients) {
                    try {
                        if (name.equals(c.getNickname())) {
                            //TODO manda messaggio di errore alla view client.getView.UnavailableNickName()
                            addClientToLobby(client); //we add the client back to the lobby, where it can choose another nickname
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            //TODO
            //client.setNickname(name); //setting the client's nickname
        };
        executorBuffer.execute(task);
    }


    /**Method that allows the Client to join a game already created by another Client.
     * The Client had previously seen a list of the available games, and chose one. Now is
     * being added to the chosen game
     * @param client to be added to the new game
     * @param GameID id of the game the client chose
     * Once the player has been added to the game, the Game gets notified*/
    @Override
    public void joinGame(VirtualClient client, int GameID) throws RemoteException {
        Runnable task=()-> {
            GameController gameToJoin=null;
            for(GameController game: runningGames)
                if(game.getId()==GameID)
                    gameToJoin=game;

            gameToJoin.addPlayer(client);
            try {
                NotifyGamePlayerJoined(gameToJoin, client);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        };
        executorBuffer.execute(task);


    }


    /**Method that gives the Client a list of game it can join. Only games that are waiting to reach the
     * required number of player are displayed
     * @param client that wants to see the available games*/
    @Override
    public void DisplayAvailableGames(VirtualClient client) throws RemoteException {
        Runnable task=()-> {
            synchronized (new Object()) {
                ArrayList<GameController> availableGames = new ArrayList<>();
                for (GameController runningGame : runningGames)
                    if (runningGame.getStatus().equals(GameStatus.WAITING)) {
                        availableGames.add(runningGame);
                    }
                try {
                    client.getGameToJoin(availableGames); //TODO fix client method
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        executorBuffer.execute(task);
    }


    /**method that creates a new game, adds it to the list of running games, and adds the Client
     * that created it to the list of players of said game. Notifies the game that a new player joined
     * @param client that created the game*/
    @Override
    public  synchronized void createGame(VirtualClient client) throws RemoteException {
        GameController newGame = new GameController(runningGames.size()+1,client.newGameSetUp());
        executor.submit(newGame);
        runningGames.add(newGame);
        newGame.addPlayer(client);
        NotifyGamePlayerJoined(newGame, client);
    }


    /**Method that notifies the game that a new player has joined.
     *@param game to be notified
     *@param client that joined the game*/
    @Override
    public void NotifyGamePlayerJoined(GameController game, VirtualClient client) throws RemoteException {
        game.NotifyNewPlayerJoined(client);
    }


}

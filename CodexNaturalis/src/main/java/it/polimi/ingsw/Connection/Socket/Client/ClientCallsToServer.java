package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Game.GameListener;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Chat.Chat;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ClientCallsToServer implements MainControllerInterface, GameControllerInterface {
    private final ClientControllerInterface clientController;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ClientListener listener;

    public ClientCallsToServer(ObjectOutputStream oos, ObjectInputStream ois, ClientControllerInterface clientController) {
        this.clientController = clientController;
        this.oos = oos;
        this.ois = ois;
    }

    public void connect(String ip){
        try {
            listener = new ClientListener(oos, ois, (ClientController) clientController);
            listener.start();

            clientController.connect(ip);

//            Thread thread = new Thread(serverHandler);
//            thread.start();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendMessage(GenericMessage message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        try {
            sendMessage(new NumRequiredPlayersMessage());
            NumRequiredPlayersResponse response = listener.getNumRequiredPlayersResponse();
            return response.getPlayers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param client
     * @throws RemoteException
     */
    @Override
    public void connect(ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new ConnectMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param name
     * @return
     * @throws RemoteException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException, IOException{
        sendMessage(new CheckUniqueNickNameMessage(name));
        CheckUniqueNickNameResponse response = listener.getCheckUniqueNickNameResponse();
        return response.getIsUnique();
    }

    /**
     * @param client
     * @param GameID
     */
    @Override
    public void joinGame(ClientControllerInterface client, int GameID) {
        try {
            sendMessage(new JoinGameMessage(GameID));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<Integer, ArrayList<String>> DisplayAvailableGames() throws RemoteException {
        try {
            sendMessage(new DisplayAvailableGamesMessage());
            DisplayAvailableGamesResponse response = listener.getDisplayAvailableGamesResponse();
            return response.getAvailableGames();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param client
     * @param maxNumberOfPlayers
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface createGame(ClientControllerInterface client, int maxNumberOfPlayers) throws RemoteException {
        try {
            sendMessage(new CreateGameMessage(maxNumberOfPlayers));
            CreateGameResponse response = listener.createGameResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return  null;
    }

    /**
     * @param game
     * @param client
     * @throws RemoteException
     */
    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new NotifyGamePlayerJoinedMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param name
     * @throws RemoteException
     */
    @Override
    public void addNickname(String name) throws RemoteException {
        try {
            sendMessage(new AddNicknameMessage(name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the game listener.
     *
     * @return the game listener.
     */
    @Override
    public GameListener getListener() throws RemoteException {
        return null;
    }

    /**
     * Retrieves the number of players.
     *
     * @return the number of players.
     */
    @Override
    public int getNumPlayers() throws RemoteException {
        return 0;
    }

    /**
     * Adds a player to the game.
     *
     * @param client the client controller of the player to be added.
     */
    @Override
    public void addPlayer(ClientControllerInterface client) throws RemoteException {

    }

    /**
     * Retrieves the current game status.
     *
     * @return the current game status.
     */
    @Override
    public GameStatus getStatus() throws RemoteException {
        return null;
    }

    /**
     * Sets the game status.
     *
     * @param status the new game status.
     */
    @Override
    public void setStatus(GameStatus status) throws RemoteException {

    }

    /**
     * Checks if the maximum number of players has been reached.
     */
    @Override
    public void CheckMaxNumPlayerReached() throws RemoteException {

    }

    /**
     * Notifies that a new player has joined the game.
     *
     * @param newPlayer the client controller of the new player.
     */
    @Override
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {

    }

    /**
     * Retrieves the ID of the game controller.
     *
     * @return the ID of the game controller.
     */
    @Override
    public int getId() throws RemoteException {
        return 0;
    }

    /**
     * Retrieves the model of the playground.
     *
     * @return the model of the playground.
     */
    @Override
    public PlayGround getModel() throws RemoteException {
        try {
            sendMessage(new GetModelMessage());
            GetModelResponse response = listener.getModelResponse();
            return response.getModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the model of the playground.
     *
     * @param model the new model of the playground.
     */
    @Override
    public void setModel(PlayGround model) throws RemoteException {

    }

    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        AtomicReference<GetPlayersResponse> response = new AtomicReference<>();
        try {
            Thread thread = new Thread(() -> {
                try {
                    sendMessage(new GetPlayersMessage());
                    response.set(listener.getPlayersResponse());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join();
            return response.get().getPlayer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {
        try {
            sendMessage(new RemoveAvailableColorMessage(color));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException {
        return null;
    }

    @Override
    public List<PawnColor> getAvailableColors() throws RemoteException {
        try {
            sendMessage(new GetAvailableColorsMessage());
            GetAvailableColorsResponse response = listener.getAvailableColorsResponse();
            return response.getAvailableColors();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException {
        return null;
    }

    @Override
    public InitialCard extractInitialCard() throws RemoteException {
        try {
            sendMessage(new ExtractInitialCardMessage());
            ExtractInitialCardResponse response = listener.getInitialCardResponse();
            return response.getCard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPlayersWhoChoseObjective() throws RemoteException {
        return 0;
    }

    @Override
    public void incrementPlayersWhoChoseObjective() throws RemoteException {

    }

    @Override
    public boolean isValidMove(PlayArea playArea, int row, int column, SideOfCard newCard) throws RemoteException {
        return false;
    }

    @Override
    public void addMessageToChat(Message message) throws RemoteException {

    }

    @Override
    public Chat getChat() throws RemoteException {
        return null;
    }

    @Override
    public void sendPrivateMessage(Message message, String first) throws RemoteException {

    }

    @Override
    public String finalRanking() throws RemoteException {
        return null;
    }

    @Override
    public GameState loadGameState(String nickname) throws RemoteException {
        return null;
    }

    @Override
    public void saveGameState() throws RemoteException {

    }

    @Override
    public void GameLoop(GameStatus status) throws RemoteException {

    }
}

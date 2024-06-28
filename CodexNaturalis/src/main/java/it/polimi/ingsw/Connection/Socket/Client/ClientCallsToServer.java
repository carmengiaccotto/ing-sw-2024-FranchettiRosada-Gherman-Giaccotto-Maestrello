package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Game.GameListener;
import it.polimi.ingsw.Controller.Game.GameListenerForClientSocket;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for handling client calls to the server.
 * It implements the MainControllerInterface and GameControllerInterface.
 * It uses ObjectOutputStream and ObjectInputStream for communication.
 */
public class ClientCallsToServer implements MainControllerInterface, GameControllerInterface {

    // The client controller interface
    private final ClientControllerInterface clientController;

    // The ObjectOutputStream for sending objects to the server
    private final ObjectOutputStream oos;

    // The ObjectInputStream for receiving objects from the server
    private final ObjectInputStream ois;

    // The client listener for handling server responses
    private ClientListener clientListener;

    /**
     * Class Constructor
     *
     * Initializes a new ClientCallsToServer with the given ObjectOutputStream, ObjectInputStream, and ClientControllerInterface.
     * The ObjectOutputStream and ObjectInputStream are used for communication with the server.
     * The ClientControllerInterface is used to control the client.
     *
     * @param oos The ObjectOutputStream for sending objects to the server.
     * @param ois The ObjectInputStream for receiving objects from the server.
     * @param clientController The ClientControllerInterface for controlling the client.
     */
    public ClientCallsToServer(ObjectOutputStream oos, ObjectInputStream ois, ClientControllerInterface clientController) {
        this.clientController = clientController;
        this.oos = oos;
        this.ois = ois;
    }

    /**
     * This method is used to establish a connection to the server.
     * It initializes a new ClientListener with the ObjectOutputStream, ObjectInputStream, and ClientController.
     * The ClientListener is then started, and the clientController is used to connect to the server using the provided IP address.
     *
     * @param ipAddress The IP address of the server to connect to.
     */
    public void connect(String ipAddress) {
        try {
            // Initialize a new ClientListener with the ObjectOutputStream, ObjectInputStream, and ClientController
            clientListener = new ClientListener(oos, ois, (ClientController) clientController);
            // Start the ClientListener
            clientListener.start();

            // Use the clientController to connect to the server using the provided IP address
            clientController.connect(ipAddress);

        } catch (IOException e) {
            // Print the stack trace for any IOExceptions
            e.printStackTrace();
        }
    }

    /**
     * This method is used to send a message to the server.
     * It takes a GenericMessage object as a parameter, which is then written to the ObjectOutputStream.
     * The ObjectOutputStream is then flushed to ensure that the message is sent immediately.
     *
     * The method is synchronized on the ObjectOutputStream to prevent multiple threads from sending messages at the same time.
     *
     * @param message The GenericMessage object to be sent to the server.
     * @throws IOException If an I/O error occurs while writing to the ObjectOutputStream.
     */
    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (oos) {
            oos.writeObject(message);
            oos.flush();
        }
    }

    /**
     * This method is used to get the number of required players for the game.
     * It sends a NumRequiredPlayersMessage to the server and waits for a response.
     * The response contains an ArrayList of pairs, where each pair represents the minimum and maximum number of players required for a game.
     *
     * @return An ArrayList of pairs, where each pair represents the minimum and maximum number of players required for a game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public ArrayList<Pair<Integer, Integer>> numRequiredPlayers() throws RemoteException {
        try {
            // Send a NumRequiredPlayersMessage to the server
            sendMessage(new NumRequiredPlayersMessage());
            // Wait for a response from the server
            NumRequiredPlayersResponse response = clientListener.getNumRequiredPlayersResponse();
            // Return the ArrayList of pairs from the response
            return response.getPlayers();
        } catch (IOException ex) {
            // Print the stack trace for any IOExceptions
            ex.printStackTrace();
        }
        // Return null if an exception occurs
        return null;
    }

    /**
     * This method is used to establish a connection to the server from the client side.
     * It sends a ConnectMessage to the server to initiate the connection process.
     *
     * @param client The ClientControllerInterface instance representing the client attempting to connect.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
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
     * This method is used to check if a nickname is unique.
     * It sends a CheckUniqueNickNameMessage to the server with the nickname to be checked.
     * The server responds with a CheckUniqueNickNameResponse, which contains a boolean indicating whether the nickname is unique.
     *
     * @param name The nickname to be checked for uniqueness.
     * @return A boolean indicating whether the nickname is unique.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws IOException If an I/O error occurs while sending the message to the server.
     */
    @Override
    public boolean checkUniqueNickName(String name) throws RemoteException, IOException {
        sendMessage(new CheckUniqueNickNameMessage(name));
        CheckUniqueNickNameResponse response = clientListener.getCheckUniqueNickNameResponse();
        return response.getIsUnique();
    }

    /**
     * This method is used to join a game on the server.
     * It sends a JoinGameMessage to the server with the GameID of the game to be joined.
     * The server responds with a JoinGameResponse, which contains the status of the join request.
     *
     * @param client The ClientControllerInterface instance representing the client attempting to join the game.
     * @param GameID The ID of the game to be joined.
     */
    @Override
    public void joinGame(ClientControllerInterface client, int GameID) {
        try {
            // Send a JoinGameMessage to the server with the GameID of the game to be joined
            sendMessage(new JoinGameMessage(GameID));
            // Wait for a response from the server
            JoinGameResponse response = clientListener.getJoinGameResponse();
        } catch (IOException ex) {
            // Print the stack trace for any IOExceptions
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to display the available games on the server.
     * It sends a DisplayAvailableGamesMessage to the server and waits for a response.
     * The response contains a Map where the key is the game ID and the value is an ArrayList of the nicknames of the players in that game.
     *
     * @return A Map where the key is the game ID and the value is an ArrayList of the nicknames of the players in that game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public Map<Integer, ArrayList<String>> DisplayAvailableGames() throws RemoteException {
        try {
            // Send a DisplayAvailableGamesMessage to the server
            sendMessage(new DisplayAvailableGamesMessage());
            // Wait for a response from the server
            DisplayAvailableGamesResponse response = clientListener.getDisplayAvailableGamesResponse();
            // Return the Map of available games from the response
            return response.getAvailableGames();
        } catch (IOException ex) {
            // Print the stack trace for any IOExceptions
            ex.printStackTrace();
        }
        // Return null if an exception occurs
        return null;
    }

    /**
     * This method is used to create a new game on the server.
     * It sends a CreateGameMessage to the server with the maximum number of players for the new game.
     * The server responds with a CreateGameResponse, which contains the status of the game creation request.
     *
     * @param client The ClientControllerInterface instance representing the client attempting to create the game.
     * @param maxNumberOfPlayers The maximum number of players for the new game.
     * @return GameControllerInterface instance representing the newly created game. Returns null if the game creation was unsuccessful.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public GameControllerInterface createGame(ClientControllerInterface client, int maxNumberOfPlayers) throws RemoteException {
        try {
            sendMessage(new CreateGameMessage(maxNumberOfPlayers));
            CreateGameResponse response = clientListener.createGameResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to notify the server that a player has joined a game.
     * It sends a NotifyGamePlayerJoinedMessage to the server.
     * The server responds with a NotifyGamePlayerJoinedResponse.
     *
     * @param game The GameControllerInterface instance representing the game that the player has joined.
     * @param client The ClientControllerInterface instance representing the client that has joined the game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void NotifyGamePlayerJoined(GameControllerInterface game, ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new NotifyGamePlayerJoinedMessage());
            NotifyGamePlayerJoinedResponse response = clientListener.getNotifyGamePlayerJoinedResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to add a nickname for a client.
     * It sends an AddNicknameMessage to the server with the nickname to be added.
     * The server responds with an appropriate response.
     *
     * @param name The nickname to be added for the client.
     * @param client The ClientControllerInterface instance representing the client for which the nickname is to be added.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void addNickname(String name, ClientControllerInterface client) throws RemoteException {
        try {
            sendMessage(new AddNicknameMessage(name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to disconnect a player from the server.
     * It sends a DisconnectMessage to the server.
     * The server responds with an appropriate response.
     *
     * @param player The ClientControllerInterface instance representing the player to be disconnected.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void disconnectPlayer(ClientControllerInterface player) throws RemoteException {
        try {
            sendMessage(new DisconnectMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to retrieve the game listener.
     * It returns a new instance of GameListenerForClientSocket, which is initialized with the ObjectOutputStream and the client listener.
     * The ObjectOutputStream is used for sending objects to the server, and the client listener is used for handling server responses.
     *
     * @return A new instance of GameListenerForClientSocket.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public GameListener getListener() throws RemoteException {
//        try {
//            sendMessage(new GetListenerMessage());
//            GetListenerResponse response = listener.getListenerResponse();
//            return response.getGameListener();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return new GameListenerForClientSocket(oos, clientListener);
    }

    /**
     * This method is used to retrieve the number of players in the game.
     * Currently, it is hardcoded to return 0 and does not provide the actual number of players.
     *
     * @return The number of players in the game. Currently, this method returns 0.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public int getNumPlayers() throws RemoteException {
        return 0;
    }

    /**
     * This method is used to add a player to the game.
     * It takes a ClientControllerInterface instance representing the player to be added.
     * Currently, the method is empty and does not perform any operations.
     *
     * @param client The ClientControllerInterface instance representing the player to be added.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void addPlayer(ClientControllerInterface client) throws RemoteException {

    }

    /**
     * This method is used to retrieve the current status of the game.
     * It sends a GetStatusMessage to the server and waits for a response.
     * The server responds with a GetStatusResponse, which contains the current status of the game.
     *
     * @return The current status of the game as a GameStatus enum.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public GameStatus getStatus() throws RemoteException {
        try {
            sendMessage(new GetStatusMessage());
            GetStatusResponse response = clientListener.getStatusResponse();
            return response.getStatus();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to set the current status of the game.
     * It takes a GameStatus enum as a parameter, which represents the new status to be set.
     * Currently, the method is empty and does not perform any operations.
     *
     * @param status The new status of the game as a GameStatus enum.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void setStatus(GameStatus status) throws RemoteException {

    }

    /**
     * This method is used to check if the maximum number of players has been reached in the game.
     * Currently, the method is empty and does not perform any operations.
     *
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void CheckMaxNumPlayerReached() throws RemoteException {

    }

    /**
     * This method is used to notify the game that a new player has joined.
     * It takes a ClientControllerInterface instance representing the new player.
     * Currently, the method is empty and does not perform any operations.
     *
     * @param newPlayer The ClientControllerInterface instance representing the new player.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer) throws RemoteException {

    }

    /**
     * This method is used to retrieve the ID of the game controller.
     * Currently, it is hardcoded to return 0 and does not provide the actual ID of the game controller.
     *
     * @return The ID of the game controller. Currently, this method returns 0.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public int getId() throws RemoteException {
        return 0;
    }

    /**
     * This method is used to retrieve the model of the playground.
     * It sends a GetModelMessage to the server and waits for a response.
     * The server responds with a GetModelResponse, which contains the model of the playground.
     *
     * @return The model of the playground as a PlayGround object.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public PlayGround getModel() throws RemoteException {
        try {
            sendMessage(new GetModelMessage());
            GetModelResponse response = clientListener.getModelResponse();
            return response.getModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to set the model of the playground.
     * It takes a PlayGround object as a parameter, which represents the new model to be set.
     * Currently, the method is empty and does not perform any operations.
     *
     * @param model The new model of the playground as a PlayGround object.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public void setModel(PlayGround model) throws RemoteException {

    }

    /**
     * This method is used to retrieve the list of players in the game.
     * It sends a GetPlayersMessage to the server and waits for a response.
     * The server responds with a GetPlayersResponse, which contains the list of players.
     *
     * @return An ArrayList of Player objects representing the players in the game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public ArrayList<Player> getPlayers() throws RemoteException {
        try {
            sendMessage(new GetPlayersMessage());
            GetPlayersResponse response = clientListener.getPlayersResponse();
            return response.getPlayer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to remove an available color from the game.
     * It sends a RemoveAvailableColorMessage to the server with the color to be removed.
     * If an IOException occurs while sending the message, it throws a RuntimeException.
     *
     * @param color The PawnColor enum representing the color to be removed.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public void removeAvailableColor(PawnColor color) throws RemoteException {
        try {
            sendMessage(new RemoveAvailableColorMessage(color));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to extract the hand cards of a player.
     * It sends an ExtractPlayerHandCardsMessage to the server and waits for a response.
     * The server responds with an ExtractPlayerHandCardsResponse, which contains the hand cards of the player.
     *
     * @return An ArrayList of PlayCard objects representing the hand cards of the player.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public ArrayList<PlayCard> extractPlayerHandCards() throws RemoteException {
        try {
            sendMessage(new ExtractPlayerHandCardsMessage());
            ExtractPlayerHandCardsResponse response = clientListener.extractPlayerHandCardsResponse();
            return response.extractPlayerHandCards();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method is used to retrieve the available colors for the pawns in the game.
     * It sends a GetAvailableColorsMessage to the server and waits for a response.
     * The server responds with a GetAvailableColorsResponse, which contains the list of available colors.
     *
     * @return A List of PawnColor enums representing the available colors for the pawns.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public List<PawnColor> getAvailableColors() throws RemoteException {
        try {
            sendMessage(new GetAvailableColorsMessage());
            GetAvailableColorsResponse response = clientListener.getAvailableColorsResponse();
            return response.getAvailableColors();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method is used to retrieve the personal objective cards of a player.
     * It sends a GetPersonalObjectiveCardsMessage to the server and waits for a response.
     * The server responds with a GetPersonalObjectiveCardsResponse, which contains the personal objective cards of the player.
     *
     * @return An ArrayList of ObjectiveCard objects representing the personal objective cards of the player.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public ArrayList<ObjectiveCard> getPersonalObjective() throws RemoteException {
        try {
            sendMessage(new GetPersonalObjectiveCardsMessage());
            GetPersonalObjectiveCardsResponse response = clientListener.getPersonalObjectiveCardsResponse();
            return response.getPersonalObjectiveCards();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to extract the initial card of a player.
     * It sends an ExtractInitialCardMessage to the server and waits for a response.
     * The server responds with an ExtractInitialCardResponse, which contains the initial card of the player.
     *
     * @return An InitialCard object representing the initial card of the player.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public InitialCard extractInitialCard() throws RemoteException {
        try {
            sendMessage(new ExtractInitialCardMessage());
            ExtractInitialCardResponse response = clientListener.getInitialCardResponse();
            return response.getCard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to retrieve the number of players who have chosen their objective in the game.
     * It sends a GetPlayersWhoChoseObjectiveMessage to the server and waits for a response.
     * The server responds with a GetPlayersWhoChoseObjectiveResponse, which contains the number of players who have chosen their objective.
     *
     * @return An integer representing the number of players who have chosen their objective.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public int getPlayersWhoChoseObjective() throws RemoteException {
        try {
            sendMessage(new GetPlayersWhoChoseObjectiveMessage());
            GetPlayersWhoChoseObjectiveResponse response = clientListener.getPlayersWhoChoseObjectiveResponse();
            return response.getPlayersWhoChoseObjective();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to increment the count of players who have chosen their objective in the game.
     * It sends an IncrementPlayersWhoChoseObjectiveMessage to the server and waits for a response.
     * The server responds with an IncrementPlayersWhoChoseObjectiveResponse.
     *
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public void incrementPlayersWhoChoseObjective() throws RemoteException {
        try {
            sendMessage(new IncrementPlayersWhoChoseObjectiveMessage());
            IncrementPlayersWhoChoseObjectiveResponse response = clientListener.getIncrementPlayersWhoChoseObjectiveResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to check if a move is valid in the game.
     * It sends an IsValidMoveMessage to the server with the current state of the play area, the position of the move, and the new card to be placed.
     * The server responds with an IsValidMoveResponse, which contains a boolean indicating whether the move is valid.
     *
     * @param playArea The PlayArea object representing the current state of the play area.
     * @param row The row index of the move.
     * @param column The column index of the move.
     * @param newCard The SideOfCard object representing the new card to be placed.
     * @return A boolean indicating whether the move is valid.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public boolean isValidMove(PlayArea playArea, int row, int column, SideOfCard newCard) throws RemoteException {
        try {
            List<List<SideOfCard>> cardsOnPlayArea = playArea.getCardsOnArea();
            Map<Symbol, Integer> symbolsOnPlayArea = playArea.getSymbols();
            sendMessage(new IsValidMoveMessage(cardsOnPlayArea, symbolsOnPlayArea, row, column, newCard));
            IsValidMoveResponse response = clientListener.isValidMoveResponse();
            return response.isValidMove();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to retrieve the final ranking of the game.
     * It sends a FinalRankingMessage to the server and waits for a response.
     * The server responds with a FinalRankingResponse, which contains the final ranking of the game.
     *
     * @return A string representing the final ranking of the game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     * @throws RuntimeException If an IOException occurs while sending the message to the server.
     */
    @Override
    public String finalRanking() throws RemoteException {
        try {
            sendMessage(new FinalRankingMessage());
            FinalRankingResponse response = clientListener.getFinalRankingResponse();
            return response.getFinalRanking();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class is responsible for handling server calls to the client.
 * It implements the ClientControllerInterface and is Serializable.
 */
public class ServerCallsToClient implements ClientControllerInterface, Serializable {

    // ObjectOutputStream used to send objects to the client.
private transient final ObjectOutputStream oos;

// ServerListener that listens for incoming messages from the client.
private transient ServerListener serverListener;

// MainControllerInterface that controls the main operations of the server.
private transient MainControllerInterface mainController;

// GameControllerInterface that controls the game operations of the server.
private transient GameControllerInterface gameController;

// String that stores the nickname of the client.
private String nickname;

    /**
     * Constructor for the ServerCallsToClient class.
     * It initializes the ObjectOutputStream, ObjectInputStream and the MainControllerInterface.
     * It also starts the server listener.
     * @param oos ObjectOutputStream for sending objects to the client.
     * @param ois ObjectInputStream for receiving objects from the client.
     * @param mainController MainControllerInterface for controlling the main operations.
     * @throws IOException if an I/O error occurs when creating the server listener.
     */
    public ServerCallsToClient(ObjectOutputStream oos, ObjectInputStream ois, MainControllerInterface mainController) throws IOException {
        this.oos = oos;
        this.mainController = mainController;
        serverListener = new ServerListener(oos, ois, this, mainController);
        serverListener.start();
    }

    /**
     * This method is used to send a message to the client.
     * It is synchronized on the ObjectOutputStream to prevent multiple threads from sending messages at the same time.
     * @param message The message to be sent to the client.
     * @throws IOException if an I/O error occurs when writing the object to the ObjectOutputStream.
     */
    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (oos) {
            oos.writeObject(message);
            oos.flush();
        }
    }

    /**
     * Sets the server for this client.
     * This method is overridden from the ClientControllerInterface.
     * @param server The MainControllerInterface object to be set as the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        return;
    }

    /**
     * Sets the view for this client.
     * This method is overridden from the ClientControllerInterface.
     * @param view The UserInterface object to be set as the view.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setView(UserInterface view) throws RemoteException {
        return;
    }

    /**
     * Disconnects this client from the server.
     * This method sends a DisconnectMessage to the server and waits for a DisconnectResponse.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void disconnect() throws RemoteException {
        try {
            sendMessage(new DisconnectMessage());
            DisconnectResponse response = serverListener.getDisconnectResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Requests the server to choose a pawn color.
     * This method sends a ChoosePawnColorMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void ChoosePawnColor() throws RemoteException {
        try {
            sendMessage(new ChoosePawnColorMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Requests the server to join a game.
     * This method sends a JoinGameToClientMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void JoinGame() throws RemoteException {
        try {
            sendMessage(new JoinGameToClientMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the nickname of this client.
     * This method is overridden from the ClientControllerInterface.
     * @return The nickname of this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public String getNickname() throws RemoteException {
//        try {
//            sendMessage(new GetNickNameMessage());
//            GetNickNameResponse response = serverListener.getNicknameResponse();
//            return response.getNickName();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
        return nickname;
    }

    /**
     * Sets the nickname for this client.
     * This method is overridden from the ClientControllerInterface.
     * It sends a SetNickNameMessage to the server with the new nickname.
     * @param nickname The new nickname to be set for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setNickname(String nickname) throws RemoteException {
        this.nickname = nickname;
        try {
            sendMessage(new SetNickNameMessage(nickname));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Requests the server to join or create a game.
     * This method sends a JoinOrCreateGameMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void JoinOrCreateGame() throws RemoteException {
        try {
            sendMessage(new JoinOrCreateGameMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Displays the available colors for the pawns.
     * This method is overridden from the ClientControllerInterface.
     * @param availableColors A list of available colors for the pawns.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {
        try {
            sendMessage(new DisplayAvailableColorsMessage(availableColors));
            DisplayAvailableColorsResponse response = serverListener.diplayAvailableColorsResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Requests the server to choose a nickname.
     * This method sends a ChooseNicknameMessage to the server and waits for a ChooseNicknameResponse.
     * @return The chosen nickname.
     * @throws IOException If an I/O error occurs when sending the message or receiving the response.
     * @throws ClassNotFoundException If the ChooseNicknameResponse class is not found.
     */
    @Override
    public String ChooseNickname() throws IOException, ClassNotFoundException {
        try {
            sendMessage(new ChooseNicknameMessage());
            ChooseNicknameResponse response = serverListener.chooseNicknameResponse();
            return response.getNickname();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Requests the server to set up a new game.
     * This method sends a NewGameSetUpMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void newGameSetUp() throws RemoteException {
        try {
            sendMessage(new NewGameSetUpMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Requests the server to wait.
     * This method sends a WaitMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void Wait() throws RemoteException {
        try {
            sendMessage(new WaitMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the color of the pawn for this client.
     * This method sends a GetPawnColorMessage to the server and waits for a GetPawnColorResponse.
     * @return The color of the pawn for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public PawnColor getPawnColor() throws RemoteException {
        try {
            sendMessage(new GetPawnColorMessage());
            GetPawnColorResponse response = serverListener.getPawnColorResponse();
            return response.getPawnColor();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the personal objective card for this client.
     * This method sends a SetPersonalObjectiveCardMessage to the server with the objective card.
     * @param objectiveCard The ObjectiveCard to be set for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {
        try {
            sendMessage(new SetPersonalObjectiveCardMessage(objectiveCard));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the Player object for this client.
     * This method sends a GetPlayerMessage to the server and waits for a GetPlayerResponse.
     * @return The Player object for this client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public Player getPlayer() throws RemoteException {
        try {
            sendMessage(new GetPlayerMessage());
            GetPlayerResponse response = serverListener.getPlayerResponse();
            return response.getPlayer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows the board and play areas to the client.
     * This method sends a ShowBoardAndPlayAreasMessage to the server with the playGround.
     * @param playGround The PlayGround to be shown to the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void showBoardAndPlayAreas(PlayGround playGround) throws RemoteException {
        try {
            sendMessage(new ShowBoardAndPlayAreasMessage(playGround));
            ShowBoardAndPlayAreasResponse response = serverListener.showBoardAndPlayAreasResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Requests the server to choose a card to draw.
     * This method sends a ChooseCardToDrawMessage to the server with the playGround and waits for a response.
     * @param playGround The PlayGround from which the card is to be drawn.
     * @return The updated PlayGround after the card has been drawn.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public PlayGround chooseCardToDraw(PlayGround playGround) throws RemoteException {
        try {
            sendMessage(new ChooseCardToDrawMessage(playGround));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    /**
//     * @return
//     * @throws RemoteException
//     */
//    @Override
//    public SideOfCard chooseCardToPlay() throws RemoteException {
//        try {
//            sendMessage(new ChooseCardToPlayMessage());
//            ChooseCardToPlayResponse response = serverListener.chooseCardToPlayResponse();
//            return response.getSideOfCard();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Sends an update message to the client.
     * This method sends an UpdateMessage to the server with the message.
     * @param message The update message to be sent to the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
        try {
            sendMessage(new UpdateMessage(message));
            UpdateMessageResponse response = serverListener.sendMessageResponse();
        } catch (IOException ex) {
        }
    }

    /**
     * Connects this client to the server.
     * This method sends a ConnectMessage to the server with the ipAddress.
     * @param ipAddress The IP address of the server to connect to.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void connect(String ipAddress) throws RemoteException {
        try {
            sendMessage(new ConnectMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds a card to the client's hand.
     * This method sends an AddCardToHand message to the server with the card to be added.
     * @param card The PlayCard to be added to the client's hand.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
        try {
            sendMessage(new AddCardToHand(card));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the score of the client.
     * This method sends a GetScoreMessage to the server and waits for a GetScoreResponse.
     * @return The score of the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public int getScore() throws RemoteException {
        try {
            sendMessage(new GetScoreMessage());
            GetScoreResponse response = serverListener.gerScoreResponse();
            return response.getScore();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves the current round of the game.
     * This method sends a GetRoundMessage to the server and waits for a GetRoundResponse.
     * @return The current round of the game.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public int getRound() throws RemoteException {
        try {
            sendMessage(new GetRoundMessage());
            GetRoundResponse response = serverListener.getRoundResponse();
            return response.getRound();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets the current round of the game.
     * This method is currently empty and does not perform any operations.
     * @param round The round to be set.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setRound(int round) throws RemoteException {

    }

    /**
     * Sets the game controller for this client.
     * This method also sets the game controller for the server listener.
     * @param game The GameControllerInterface to be set as the game controller.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        this.gameController = game;
        serverListener.setGamecontroller((GameController) game);
    }

    /**
     * Retrieves the personal objective card of the client.
     * This method sends a GetPersonalObjectiveCardMessage to the server and waits for a GetPersonalObjectiveCardResponse.
     * @return The personal objective card of the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        try {
            sendMessage(new GetPersonalObjectiveCardMessage());
            GetPersonalObjectiveCardResponse response = serverListener.getPersonalObjectiveCardResponse();
            return response.getPersonalObjectiveCard();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Requests the server to join the lobby.
     * This method sends a JoinLobbyMessage to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void JoinLobby() throws RemoteException {
        try {
            sendMessage(new JoinLobbyMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends a request to the server about what to do next.
     * This method sends a WhatDoIDoNowMessage to the server with the request and waits for a WhatDoIDoNowResponse.
     * @param doThis The request to be sent to the server.
     * @return Object An object representing the result of the move.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public Object WhatDoIDoNow(String doThis) throws RemoteException {
        try {
            sendMessage(new WhatDoIDoNowMessage(doThis));
            WhatDoIDoNowResponse response = serverListener.whatDoIDoNowResponse();
            return response.getObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

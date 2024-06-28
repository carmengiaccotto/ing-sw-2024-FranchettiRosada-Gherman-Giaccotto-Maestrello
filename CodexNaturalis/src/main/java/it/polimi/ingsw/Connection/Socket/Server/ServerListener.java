package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameListener;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.*;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a server listener that extends the Thread class and implements Serializable.
 * It contains a ClientControllerInterface object, a GameController object, an ObjectInputStream object,
 * an ObjectOutputStream object, a MainControllerInterface object, and several response objects and lock objects.
 */
public class ServerListener extends Thread implements Serializable {
    // The client controller interface.
    private final ClientControllerInterface client;
    // The game controller.
    private GameController gamecontroller;
    // The object input stream.
    private final ObjectInputStream inputStream;
    // The object output stream.
    private final ObjectOutputStream outputStream;
    // The main controller interface.
    private final MainControllerInterface mainController;
    // The get nickname response.
    private GetNickNameResponse getNicknameResponse;
    // The lock object for the get nickname response.
    private final Object getNicknameResponseLockObject = new Object();
    // The choose nickname response.
    private ChooseNicknameResponse chooseNicknameResponse;
    // The lock object for the choose nickname response.
    private final Object chooseNicknameResponseLockObject = new Object();
    // The get pawn color response.
    private GetPawnColorResponse getPawnColorResponse;
    // The lock object for the get pawn color response.
    private final Object getPawnColorResponseLockObject = new Object();
    // The choose card to play response.
    private ChooseCardToPlayResponse chooseCardToPlayResponse;
    // The lock object for the choose card to play response.
    private final Object chooseCardToPlayResponseLockObject = new Object();
    // The get player response.
    private GetPlayerResponse getPlayerResponse;
    // The lock object for the get player response.
    private final Object getPlayerResponseLockObject = new Object();
    // The get score response.
    private GetScoreResponse getScoreResponse;
    // The lock object for the get score response.
    private final Object getScoreResponseLockObject = new Object();
    // The get round response.
    private GetRoundResponse getRoundResponse;
    // The lock object for the get round response.
    private final Object getRoundResponseLockObject = new Object();
    // The get personal objective card response.
    private GetPersonalObjectiveCardResponse getPersonalObjectiveCardResponse;
    // The lock object for the get personal objective card response.
    private final Object getPersonalObjectiveCardResponseLockObject = new Object();
    // The what do I do now response.
    private WhatDoIDoNowResponse whatDoIDoNowResponse;
    // The lock object for the what do I do now response.
    private final Object whatDoIDoNowResponseLockObject = new Object();
    // The disconnect response.
    private DisconnectResponse disconnectResponse;
    // The lock object for the disconnect response.
    private final Object disconnectResponseLockObject = new Object();
    private DisplayAvailableColorsResponse displayAvailableColorsResponse;
    private final Object diplayAvailableColorsResponseLockObject = new Object();
    private UpdateMessageResponse sendMessageResponse;
    private final Object sendMessageResponseLockObject = new Object();
    private ShowBoardAndPlayAreasResponse showBoardAndPlayAreasResponse;
    private final Object showBoardAndPlayAreasResponseLockObject = new Object();

    /**
     * Constructs a ServerListener with the specified output stream, input stream, client controller interface, and main controller interface.
     * @param oos the ObjectOutputStream to be used for sending messages
     * @param ois the ObjectInputStream to be used for receiving messages
     * @param client the ClientControllerInterface to be used for client-side operations
     * @param mainController the MainControllerInterface to be used for main controller operations
     */
    public ServerListener(ObjectOutputStream oos, ObjectInputStream ois, ClientControllerInterface client, MainControllerInterface mainController) {
        this.client = client;
        this.mainController = mainController;
        this.outputStream = oos;
        this.inputStream = ois;
    }

    /**
     * Sets the game controller for this server listener.
     * @param game the GameController to be set
     */
    public void setGamecontroller(GameController game) {
        this.gamecontroller = game;
    }

    /**
     * Sends a GenericMessage object to the client.
     * This method is synchronized on the outputStream object to prevent multiple threads from sending messages at the same time.
     * @param message the GenericMessage object to be sent
     * @throws IOException if an I/O error occurs when writing the object to the ObjectOutputStream
     */
    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(message);
            outputStream.flush();
        }
    }

    /**
     * The main execution method for the ServerListener thread.
     * This method continuously reads GenericMessage objects from the input stream and processes them based on their type.
     * Each message is processed in a new thread, which is scheduled to run after a delay of 100 milliseconds.
     * The method also handles disconnection of the client and any exceptions that occur during message processing.
     */
    public void run() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
            while (true) {
                GenericMessage message = (GenericMessage) inputStream.readObject();
                Thread thread = new Thread(() -> {
                    if (message instanceof NumRequiredPlayersMessage) {
                        ArrayList<Pair<Integer, Integer>> numRequiredPlayers = null;
                        try {
                            numRequiredPlayers = mainController.numRequiredPlayers();
                            sendMessage(new NumRequiredPlayersResponse(numRequiredPlayers));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof ConnectMessage) {
                        try {
                            mainController.connect(client);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetAvailableColorsMessage) {
                        List<PawnColor> color = null;
                        try {
                            color = gamecontroller.getAvailableColors();
                            sendMessage(new GetAvailableColorsResponse(color));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof CheckUniqueNickNameMessage) {
                        boolean isUnique = false;
                        try {
                            isUnique = mainController.checkUniqueNickName(((CheckUniqueNickNameMessage) message).getNickname());
                            sendMessage(new CheckUniqueNickNameResponse(isUnique));
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof JoinGameMessage) {
                        try {
                            mainController.joinGame(client, ((JoinGameMessage) message).getGameId());
                            sendMessage(new JoinGameResponse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof DisplayAvailableGamesMessage) {
                        Map availableGames = null;
                        try {
                            availableGames = mainController.DisplayAvailableGames();
                            sendMessage(new DisplayAvailableGamesResponse(availableGames));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof CreateGameMessage) {
                        try {
                            mainController.createGame(client, ((CreateGameMessage) message).getMaxNumberOfPlayers());
                            sendMessage(new CreateGameResponse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof NotifyGamePlayerJoinedMessage) {
                        try {
                            mainController.NotifyGamePlayerJoined(gamecontroller, client);
                            sendMessage(new NotifyGamePlayerJoinedResponse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof AddNicknameMessage) {
                        try {
                            mainController.addNickname(((AddNicknameMessage) message).getNickname(), client);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof RemoveAvailableColorMessage) {
                        try {
                            gamecontroller.removeAvailableColor(((RemoveAvailableColorMessage) message).getColor());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof ExtractInitialCardMessage) {
                        InitialCard card = null;
                        try {
                            card = gamecontroller.extractInitialCard();
                            sendMessage(new ExtractInitialCardResponse(card));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetModelMessage) {
                        PlayGround response = gamecontroller.getModel();
                        try {
                            sendMessage(new GetModelResponse(response));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof ExtractPlayerHandCardsMessage) {
                        try {
                            ArrayList<PlayCard> cards = gamecontroller.extractPlayerHandCards();
                            sendMessage(new ExtractPlayerHandCardsResponse(cards));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof DisconnectMessage) {
                        try {
                            mainController.disconnectPlayer(client);
                            sendMessage(new DisconnectResponse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof DisplayAvailableColorsResponse) {
                        synchronized (diplayAvailableColorsResponseLockObject) {
                            displayAvailableColorsResponse = (DisplayAvailableColorsResponse) message;
                            diplayAvailableColorsResponseLockObject.notify();
                        }
                    } else if (message instanceof GetNickNameResponse) {
                        synchronized (getNicknameResponseLockObject) {
                            getNicknameResponse = (GetNickNameResponse) message;
                            getNicknameResponseLockObject.notify();
                        }
                    } else if (message instanceof ChooseNicknameResponse) {
                        synchronized (chooseNicknameResponseLockObject) {
                            chooseNicknameResponse = (ChooseNicknameResponse) message;
                            chooseNicknameResponseLockObject.notify();
                        }
                    } else if (message instanceof GetPawnColorResponse) {
                        synchronized (getPawnColorResponseLockObject) {
                            getPawnColorResponse = (GetPawnColorResponse) message;
                            getPawnColorResponseLockObject.notify();
                        }

                    } else if (message instanceof GetPlayersMessage) {
                        ArrayList<Player> players = null;
                        try {
                            players = gamecontroller.getPlayers();
                            sendMessage(new GetPlayersResponse(players));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetPersonalObjectiveCardsMessage) {
                        ArrayList<ObjectiveCard> personalObjectiveCards = null;
                        try {
                            personalObjectiveCards = gamecontroller.getPersonalObjective();
                            sendMessage(new GetPersonalObjectiveCardsResponse(personalObjectiveCards));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetListenerMessage) {
                        GameListener gameListener = null;
                        try {
                            gameListener = gamecontroller.getListener();
                            sendMessage(new GetListenerResponse(gameListener));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
//                    else if (message instanceof UpdatePlayersMessage) {
//                        try {
//                            UpdatePlayersMessage updatePlayersMessage = (UpdatePlayersMessage) message;
//                            if (updatePlayersMessage.getPlayGround() != null) {
//                                gamecontroller.getListener().updatePlayers(updatePlayersMessage.getPlayGround());
//                            } else if (updatePlayersMessage.getNickname() != null) {
//                                ClientControllerInterface currentPlayer = null;
//                                for (ClientControllerInterface player : gamecontroller.getListener().getPlayers()) {
//                                    if (player.getNickname().equals(updatePlayersMessage.getNickname())) {
//                                        currentPlayer = player;
//                                        break;
//                                    }
//                                }
//                                gamecontroller.getListener().updatePlayers(updatePlayersMessage.getMessage(), currentPlayer);
//                            } else {
//                                gamecontroller.getListener().updatePlayers(updatePlayersMessage.getMessage());
//                            }
//                            sendMessage(new UpdatePlayersResponse());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
                    else if (message instanceof IncrementPlayersWhoChoseObjectiveMessage) {
                        try {
                            gamecontroller.incrementPlayersWhoChoseObjective();
                            sendMessage(new IncrementPlayersWhoChoseObjectiveResponse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetPlayersWhoChoseObjectiveMessage) {
                        try {
                            int playersWhoChoseObjective = gamecontroller.getPlayersWhoChoseObjective();
                            sendMessage(new GetPlayersWhoChoseObjectiveResponse(playersWhoChoseObjective));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof IsValidMoveMessage) {
                        try {
                            IsValidMoveMessage isValidMoveMessage = (IsValidMoveMessage) message;
                            PlayArea playArea = new PlayArea(isValidMoveMessage.getCardsOnPlayArea(), isValidMoveMessage.getSymbols());
                            boolean isValidMove = gamecontroller.isValidMove(playArea, isValidMoveMessage.getRow(), isValidMoveMessage.getColumn(), isValidMoveMessage.getNewCard());
                            sendMessage(new IsValidMoveResponse(isValidMove));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetStatusMessage) {
                        try {
                            GameStatus gameStatus = gamecontroller.getStatus();
                            sendMessage(new GetStatusResponse(gameStatus));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof FinalRankingMessage) {
                        try {
                            String finalRanking = gamecontroller.finalRanking();
                            sendMessage(new FinalRankingResponse(finalRanking));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof GetPlayerResponse) {
                        synchronized (getPlayerResponseLockObject) {
                            getPlayerResponse = (GetPlayerResponse) message;
                            getPlayerResponseLockObject.notify();
                        }
                    } else if (message instanceof ChooseCardToPlayResponse) {
                        synchronized (chooseCardToPlayResponseLockObject) {
                            chooseCardToPlayResponse = (ChooseCardToPlayResponse) message;
                            chooseCardToPlayResponseLockObject.notify();
                        }
                    } else if (message instanceof GetScoreResponse) {
                        synchronized (getScoreResponseLockObject) {
                            getScoreResponse = (GetScoreResponse) message;
                            getScoreResponseLockObject.notify();
                        }
                    } else if (message instanceof GetRoundResponse) {
                        synchronized (getRoundResponseLockObject) {
                            getRoundResponse = (GetRoundResponse) message;
                            getRoundResponseLockObject.notify();
                        }
                    } else if (message instanceof GetPersonalObjectiveCardResponse) {
                        synchronized (getPersonalObjectiveCardResponseLockObject) {
                            getPersonalObjectiveCardResponse = (GetPersonalObjectiveCardResponse) message;
                            getPersonalObjectiveCardResponseLockObject.notify();
                        }
                    } else if (message instanceof WhatDoIDoNowResponse) {
                        synchronized (whatDoIDoNowResponseLockObject) {
                            whatDoIDoNowResponse = (WhatDoIDoNowResponse) message;
                            whatDoIDoNowResponseLockObject.notify();
                        }
                    } else if (message instanceof DisconnectResponse) {
                        synchronized (disconnectResponseLockObject) {
                            disconnectResponse = (DisconnectResponse) message;
                            disconnectResponseLockObject.notify();
                        }
                    } else if (message instanceof UpdateMessageResponse) {
                        synchronized (sendMessageResponseLockObject) {
                            sendMessageResponse = (UpdateMessageResponse) message;
                            sendMessageResponseLockObject.notify();
                        }
                    } else if (message instanceof ShowBoardAndPlayAreasResponse) {
                        synchronized (showBoardAndPlayAreasResponseLockObject) {
                            showBoardAndPlayAreasResponse = (ShowBoardAndPlayAreasResponse) message;
                            showBoardAndPlayAreasResponseLockObject.notify();
                        }
                    }
                });
                scheduler.schedule(thread, 100, TimeUnit.MILLISECONDS);
            }
        } catch (SocketException | EOFException e) {
            try {
                gamecontroller.clientDisconnected(client);
                mainController.disconnectPlayer(client);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the GetNickNameResponse object.
     * This method waits until the GetNickNameResponse object is available.
     * It is synchronized on the getNicknameResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetNickNameResponse object
     */
    public GetNickNameResponse getNicknameResponse() {
        synchronized (getNicknameResponseLockObject) {
            try {
                getNicknameResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getNicknameResponse;
    }

    /**
     * Retrieves the ChooseNicknameResponse object.
     * This method waits until the ChooseNicknameResponse object is available.
     * It is synchronized on the chooseNicknameResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the ChooseNicknameResponse object
     */
    public ChooseNicknameResponse chooseNicknameResponse() {
        synchronized (chooseNicknameResponseLockObject) {
            try {
                chooseNicknameResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return chooseNicknameResponse;
    }

    /**
     * Retrieves the GetPawnColorResponse object.
     * This method waits until the GetPawnColorResponse object is available.
     * It is synchronized on the getPawnColorResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetPawnColorResponse object
     */
    public GetPawnColorResponse getPawnColorResponse() {
        synchronized (getPawnColorResponseLockObject) {
            try {
                getPawnColorResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return getPawnColorResponse;
    }

    /**
     * Retrieves the GetPlayerResponse object.
     * This method waits until the GetPlayerResponse object is available.
     * It is synchronized on the getPlayerResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetPlayerResponse object
     */
    public GetPlayerResponse getPlayerResponse() {
        synchronized (getPlayerResponseLockObject) {
            try {
                getPlayerResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return getPlayerResponse;
    }

    /**
     * Retrieves the ChooseCardToPlayResponse object.
     * This method waits until the ChooseCardToPlayResponse object is available.
     * It is synchronized on the chooseCardToPlayResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the ChooseCardToPlayResponse object
     */
    public ChooseCardToPlayResponse chooseCardToPlayResponse() {
        synchronized (chooseCardToPlayResponseLockObject) {
            try {
                chooseCardToPlayResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return chooseCardToPlayResponse;
    }

    /**
     * Retrieves the GetScoreResponse object.
     * This method waits until the GetScoreResponse object is available.
     * It is synchronized on the getScoreResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetScoreResponse object
     */
    public GetScoreResponse gerScoreResponse() {
        synchronized (getScoreResponseLockObject) {
            try {
                getScoreResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return getScoreResponse;
    }

    /**
     * Retrieves the GetRoundResponse object.
     * This method waits until the GetRoundResponse object is available.
     * It is synchronized on the getRoundResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetRoundResponse object
     */
    public GetRoundResponse getRoundResponse() {
        synchronized (getRoundResponseLockObject) {
            try {
                getRoundResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return getRoundResponse;
    }

    /**
     * Retrieves the GetPersonalObjectiveCardResponse object.
     * This method waits until the GetPersonalObjectiveCardResponse object is available.
     * It is synchronized on the getPersonalObjectiveCardResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the GetPersonalObjectiveCardResponse object
     */
    public GetPersonalObjectiveCardResponse getPersonalObjectiveCardResponse() {
        synchronized (getPersonalObjectiveCardResponseLockObject) {
            try {
                getPersonalObjectiveCardResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return getPersonalObjectiveCardResponse;
    }

    /**
     * Retrieves the WhatDoIDoNowResponse object.
     * This method waits until the WhatDoIDoNowResponse object is available.
     * It is synchronized on the whatDoIDoNowResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the WhatDoIDoNowResponse object
     */
    public WhatDoIDoNowResponse whatDoIDoNowResponse() {
        synchronized (whatDoIDoNowResponseLockObject) {
            try {
                whatDoIDoNowResponseLockObject.wait();
            } catch (InterruptedException ex) {

            }
        }
        return whatDoIDoNowResponse;
    }

    /**
     * Retrieves the DisconnectResponse object.
     * This method waits until the DisconnectResponse object is available.
     * It is synchronized on the disconnectResponseLockObject to prevent multiple threads from accessing the response at the same time.
     * @return the DisconnectResponse object
     */
    public DisconnectResponse getDisconnectResponse() {
        synchronized (disconnectResponseLockObject) {
            try {
                disconnectResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return disconnectResponse;
    }

    public DisplayAvailableColorsResponse diplayAvailableColorsResponse() {
        synchronized (diplayAvailableColorsResponseLockObject) {
        try {
            diplayAvailableColorsResponseLockObject.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
        return displayAvailableColorsResponse;
    }

    public UpdateMessageResponse sendMessageResponse() {
        synchronized (sendMessageResponseLockObject) {
            try {
                sendMessageResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return sendMessageResponse;
    }

    public ShowBoardAndPlayAreasResponse showBoardAndPlayAreasResponse() {
        synchronized (showBoardAndPlayAreasResponseLockObject) {
            try {
                showBoardAndPlayAreasResponseLockObject.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return showBoardAndPlayAreasResponse;
    }
}

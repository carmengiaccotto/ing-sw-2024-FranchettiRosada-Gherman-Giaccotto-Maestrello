package it.polimi.ingsw.Connection.Socket.Client;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The ClientListener class extends Thread and is responsible for listening to messages from the server.
 * It contains methods to send and receive messages, and to handle different types of messages.
 * It also contains a reference to the client controller, and input and output streams for communication.
 *
 * Each type of response message from the server has a corresponding response object and a lock object in this class.
 * The lock objects are used to synchronize the threads when waiting for a response from the server.
 */
public class ClientListener extends Thread {

    /**
    *The client controller
     */
    private ClientController clientController;

    /**
     *The input stream for receiving messages from the server
     */
    private ObjectInputStream inputStream;

    /**
     *The output stream for sending messages to the server
     */
    private final ObjectOutputStream outputStream;

        /**
     * The response object for the number of required players.
     */
    private NumRequiredPlayersResponse numRequiredPlayersResponse;

    /**
     * The lock object for synchronizing threads when waiting for the number of required players response.
     */
    private final Object numRequiredPlayersResponseLockObject = new Object();

    /**
     * The response object for checking the uniqueness of the nickname.
     */
    private CheckUniqueNickNameResponse checkUniqueNickNameResponse;

    /**
     * The lock object for synchronizing threads when waiting for the uniqueness of the nickname response.
     */
    private final Object checkUniqueNickNameResponseLockObject = new Object();

    /**
     * The response object for displaying available games.
     */
    private DisplayAvailableGamesResponse displayAvailableGamesResponse;

    /**
     * The lock object for synchronizing threads when waiting for the available games response.
     */
    private final Object displayAvailableGamesResponseLockObject = new Object();

    /**
     * The response object for getting available colors.
     */
    public GetAvailableColorsResponse getAvailableColorsResponse;

    /**
     * The lock object for synchronizing threads when waiting for the available colors response.
     */
    private final Object getAvailableColorsResponseLockObject = new Object();

    /**
     * The response object for extracting the initial card.
     */
    public ExtractInitialCardResponse initialCardResponse;

    /**
     * The lock object for synchronizing threads when waiting for the initial card extraction response.
     */
    private final Object initialCardResponseLockObject = new Object();

    /**
     * The response object for getting the players in the game.
     */
    public GetPlayersResponse getPlayerResponse;

    /**
     * The lock object for synchronizing threads when waiting for the players in the game response.
     */
    private final Object getPlayerResponseLockObject = new Object();

    /**
     * The response object for getting the game model.
     */
    public GetModelResponse getModelResponse;

    /**
     * The lock object for synchronizing threads when waiting for the game model response.
     */
    private final Object getModelresponseLockObject = new Object();

    /**
     * The response object for creating a game.
     */
    private CreateGameResponse createGameResponse;

    /**
     * The lock object for synchronizing threads when waiting for the game creation response.
     */
        private final Object createGameResponseLockObject = new Object();

    /**
     * Lock object for synchronizing threads when waiting for the chat response.
     */
    private final Object getChatResponseLockObject = new Object();

    /**
     * Response object for joining a game.
     */
    private JoinGameResponse joinGameResponse;

    /**
     * Lock object for synchronizing threads when waiting for the join game response.
     */
    private final Object joinGameResponseLockObject = new Object();

    /**
     * Response object for notifying when a player joins the game.
     */
    private NotifyGamePlayerJoinedResponse notifyGamePlayerJoinedResponse;

    /**
     * Lock object for synchronizing threads when waiting for the notification of a player joining the game.
     */
    private final Object notifyGamePlayerJoinedResponseLockObject = new Object();

    /**
     * Response object for getting personal objective cards.
     */
    private GetPersonalObjectiveCardsResponse getPersonalObjectiveCardsResponse;

    /**
     * Lock object for synchronizing threads when waiting for the personal objective cards response.
     */
    private final Object getPersonalObjectiveCardsResponseLockObject = new Object();

    /**
     * Response object for getting the listener.
     */
    private GetListenerResponse getListenerResponse;

    /**
     * Lock object for synchronizing threads when waiting for the listener response.
     */
    private final Object getListenerResponseLockObject = new Object();

    /**
     * Response object for incrementing players who chose an objective.
     */
    private IncrementPlayersWhoChoseObjectiveResponse incrementPlayersWhoChoseObjectiveResponse;

    /**
     * Lock object for synchronizing threads when waiting for the increment of players who chose an objective response.
     */
    private final Object incrementPlayersWhoChoseObjectiveResponseLockObject = new Object();

    /**
     * Response object for getting the players who chose an objective.
     */
    private GetPlayersWhoChoseObjectiveResponse getPlayersWhoChoseObjectiveResponse;

    /**
     * Lock object for synchronizing threads when waiting for the players who chose an objective response.
     */
    private final Object getPlayersWhoChoseObjectiveResponseLockObject = new Object();

    /**
     * Response object for extracting player hand cards.
     */
    private ExtractPlayerHandCardsResponse extractPlayerHandCardsResponse;

    /**
     * Lock object for synchronizing threads when waiting for the extraction of player hand cards response.
     */
    private final Object extractPlayerHandCardsResponseLockObject = new Object();

    /**
     * Response object for checking if a move is valid.
     */
    private IsValidMoveResponse isValidMoveResponse;

    /**
     * Lock object for synchronizing threads when waiting for the validity of a move response.
     */
    private final Object isValidMoveResponseLockObject = new Object();

    /**
     * Response object for getting the game status.
     */
    private GetStatusResponse getStatusResponse;

    /**
     * Lock object for synchronizing threads when waiting for the game status response.
     */
    private final Object getStatusResponseLockObject = new Object();

    /**
     * Response object for updating players.
     */
    private UpdatePlayersResponse updatePlayersResponse;

    /**
     * Lock object for synchronizing threads when waiting for the update of players response.
     */
    private final Object updatePlayersResponseLockObject = new Object();

    /**
     * Response object for getting the final ranking of players.
     */
    private FinalRankingResponse finalRankingResponse;

    /**
     * Lock object for synchronizing threads when waiting for the final ranking of players response.
     */
    private final Object finalRankingResponseLockObject = new Object();

    /**
     * Response object for a disconnect event.
     */
    private DisconnectResponse disconnectResponse;

    /**
     * Lock object for synchronizing threads when waiting for a disconnect event response.
     */
    private final Object disconnectResponseLockObject = new Object();

    /**
     * The response object for displaying available colors.
     * This object is updated when a DisplayAvailableColorsResponse message is received from the server.
     */
    private DisplayAvailableColorsResponse displayAvailableColorsResponse;

    /**
     * The lock object for synchronizing threads when waiting for the available colors response.
     * Threads that need to access the displayAvailableColorsResponse object will wait on this lock object.
     * When the response is received, the waiting thread is notified.
     */
    private final Object displayAvailableColorsLockObject = new Object();

    /**
     * The response object for removing an available color.
     * This object is updated when a RemoveAvailableColorResponse message is received from the server.
     */
    private RemoveAvailableColorResponse removeAvailableColorResponse;

    /**
     * The lock object for synchronizing threads when waiting for the remove available color response.
     * Threads that need to access the removeAvailableColorResponse object will wait on this lock object.
     * When the response is received, the waiting thread is notified.
     */
    private final Object removeAvailableColorResponseLockObject = new Object();


    /**
     * Constructor for the ClientListener class.
     * Initializes the input and output streams and the client controller.
     *
     * @param oos The ObjectOutputStream used for sending messages to the server.
     * @param ois The ObjectInputStream used for receiving messages from the server.
     * @param clientController The ClientController used for handling client-side logic.
     */
    public ClientListener(ObjectOutputStream oos, ObjectInputStream ois, ClientController clientController) {
        this.outputStream = oos;
        this.inputStream = ois;
        this.clientController = clientController;
    }

    /**
     * Sends a message to the server.
     * This method is synchronized on the output stream to ensure that messages are sent one at a time.
     *
     * @param message The GenericMessage object to be sent to the server.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(message);
            outputStream.flush();
        }
    }

    /**
     * This method is the main execution point for the ClientListener thread.
     * It continuously listens for incoming messages from the server and handles them accordingly.
     * Each type of message is checked using instanceof and handled in a separate thread to avoid blocking the main thread.
     * The handling of each message type involves either calling a method on the client controller or updating a response object and notifying the waiting thread.
     * If an IOException or ClassNotFoundException occurs, the stack trace is printed.
     */
    public void run() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
            while (true) {
                try {
                    GenericMessage message = (GenericMessage) inputStream.readObject();
                    Thread thread = new Thread(() -> {
                        if (message instanceof ChoosePawnColorMessage) {
                            try {
                                clientController.ChoosePawnColor();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof NumRequiredPlayersResponse) {
                            synchronized (numRequiredPlayersResponseLockObject) {
                                numRequiredPlayersResponse = (NumRequiredPlayersResponse) message;
                                numRequiredPlayersResponseLockObject.notify();
                            }
                        } else if (message instanceof CheckUniqueNickNameResponse) {
                            synchronized (checkUniqueNickNameResponseLockObject) {
                                checkUniqueNickNameResponse = (CheckUniqueNickNameResponse) message;
                                checkUniqueNickNameResponseLockObject.notify();
                            }
                        } else if (message instanceof DisplayAvailableGamesResponse) {
                            synchronized (displayAvailableGamesResponseLockObject) {
                                displayAvailableGamesResponse = (DisplayAvailableGamesResponse) message;
                                displayAvailableGamesResponseLockObject.notify();
                            }
                        } else if (message instanceof GetAvailableColorsResponse) {
                            synchronized (getAvailableColorsResponseLockObject) {
                                getAvailableColorsResponse = (GetAvailableColorsResponse) message;
                                getAvailableColorsResponseLockObject.notify();
                            }
                        } else if (message instanceof UpdateMessage) {
                            try {
                                clientController.sendUpdateMessage(((UpdateMessage) message).getMessage());
                                sendMessage(new UpdateMessageResponse());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof GetNickNameMessage) {
                            try {
                                String nickname = clientController.getNickname();
                                sendMessage(new GetNickNameResponse(nickname));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof GetPlayerMessage) {
                            try {
                                Player player = clientController.getPlayer();
                                sendMessage(new GetPlayerResponse(player));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof WhatDoIDoNowMessage) {
                            try {
                                Object object = clientController.WhatDoIDoNow(((WhatDoIDoNowMessage) message).getDoThis());
                                sendMessage(new WhatDoIDoNowResponse(object));
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof GetRoundMessage) {
                            try {
                                int round = clientController.getRound();
                                sendMessage(new GetRoundResponse(round));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof GetPawnColorMessage) {
                            try {
                                PawnColor pawnColor = clientController.getPawnColor();
                                sendMessage(new GetPawnColorResponse(pawnColor));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof ShowBoardAndPlayAreasMessage) {
                            try {
                                clientController.showBoardAndPlayAreas(((ShowBoardAndPlayAreasMessage) message).getPlayGround());
                                sendMessage(new ShowBoardAndPlayAreasResponse());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }  else if (message instanceof DisconnectMessage) {
                            try {
                                clientController.disconnect();
                                sendMessage(new DisconnectResponse());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (message instanceof DisplayAvailableColorsMessage) {
                            synchronized (displayAvailableColorsLockObject) {
                                try {
                                    DisplayAvailableColorsMessage displayAvailableColorsMessage = (DisplayAvailableColorsMessage) message;
                                    clientController.displayAvailableColors(displayAvailableColorsMessage.getColors());
                                    sendMessage(new DisplayAvailableColorsResponse());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }else if (message instanceof GetPlayersResponse) {
                            synchronized (getPlayerResponseLockObject) {
                                getPlayerResponse = (GetPlayersResponse) message;
                                getPlayerResponseLockObject.notify();
                            }
                        } else if (message instanceof ExtractInitialCardResponse) {
                            synchronized (initialCardResponseLockObject) {
                                initialCardResponse = (ExtractInitialCardResponse) message;
                                initialCardResponseLockObject.notify();
                            }
                        } else if (message instanceof CreateGameResponse) {
                            synchronized (createGameResponseLockObject) {
                                createGameResponse = (CreateGameResponse) message;
                                createGameResponseLockObject.notify();
                            }
                        } else if (message instanceof GetModelResponse) {
                            synchronized (getModelresponseLockObject) {
                                getModelResponse = (GetModelResponse) message;
                                getModelresponseLockObject.notify();
                            }
                        } else if (message instanceof JoinGameResponse) {
                            synchronized (joinGameResponseLockObject) {
                                joinGameResponse = (JoinGameResponse) message;
                                joinGameResponseLockObject.notify();
                            }
                        } else if (message instanceof NotifyGamePlayerJoinedResponse) {
                            synchronized (notifyGamePlayerJoinedResponseLockObject) {
                                notifyGamePlayerJoinedResponse = (NotifyGamePlayerJoinedResponse) message;
                                notifyGamePlayerJoinedResponseLockObject.notify();
                            }
                        } else if (message instanceof GetPersonalObjectiveCardsResponse) {
                            synchronized (getPersonalObjectiveCardsResponseLockObject) {
                                getPersonalObjectiveCardsResponse = (GetPersonalObjectiveCardsResponse) message;
                                getPersonalObjectiveCardsResponseLockObject.notify();
                            }
                        } else if (message instanceof GetListenerResponse) {
                            synchronized (getListenerResponseLockObject) {
                                getListenerResponse = (GetListenerResponse) message;
                                getListenerResponseLockObject.notify();
                            }
                        } else if (message instanceof IncrementPlayersWhoChoseObjectiveResponse) {
                            synchronized (incrementPlayersWhoChoseObjectiveResponseLockObject) {
                                incrementPlayersWhoChoseObjectiveResponse = (IncrementPlayersWhoChoseObjectiveResponse) message;
                                incrementPlayersWhoChoseObjectiveResponseLockObject.notify();
                            }
                        } else if (message instanceof GetPlayersWhoChoseObjectiveResponse) {
                            synchronized (getPlayersWhoChoseObjectiveResponseLockObject) {
                                getPlayersWhoChoseObjectiveResponse = (GetPlayersWhoChoseObjectiveResponse) message;
                                getPlayersWhoChoseObjectiveResponseLockObject.notify();
                            }
                        } else if (message instanceof ExtractPlayerHandCardsResponse) {
                            synchronized (extractPlayerHandCardsResponseLockObject) {
                                extractPlayerHandCardsResponse = (ExtractPlayerHandCardsResponse) message;
                                extractPlayerHandCardsResponseLockObject.notify();
                            }
                        } else if (message instanceof IsValidMoveResponse) {
                            synchronized (isValidMoveResponseLockObject) {
                                isValidMoveResponse = (IsValidMoveResponse) message;
                                isValidMoveResponseLockObject.notify();
                            }
                        } else if (message instanceof GetStatusResponse) {
                            synchronized (getStatusResponseLockObject) {
                                getStatusResponse = (GetStatusResponse) message;
                                getStatusResponseLockObject.notify();
                            }
                        } else if (message instanceof UpdatePlayersResponse) {
                            synchronized (updatePlayersResponseLockObject) {
                                updatePlayersResponse = (UpdatePlayersResponse) message;
                                updatePlayersResponseLockObject.notify();
                            }
                        } else if (message instanceof FinalRankingResponse) {
                            synchronized (finalRankingResponseLockObject) {
                                finalRankingResponse = (FinalRankingResponse) message;
                                finalRankingResponseLockObject.notify();
                            }
                        } else if (message instanceof DisconnectResponse) {
                            synchronized (disconnectResponseLockObject) {
                                disconnectResponse = (DisconnectResponse) message;
                                disconnectResponseLockObject.notify();
                            }
                        } else if (message instanceof RemoveAvailableColorResponse) {
                            synchronized (removeAvailableColorResponseLockObject) {
                                removeAvailableColorResponse = (RemoveAvailableColorResponse) message;
                                removeAvailableColorResponseLockObject.notify();
                            }
                        }
                    });
                    scheduler.schedule(thread, 100, TimeUnit.MILLISECONDS);
                } catch (OptionalDataException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is used to retrieve the response for the number of required players.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it prints the stack trace.
     *
     * @return The NumRequiredPlayersResponse object containing the response for the number of required players.
     */
    public NumRequiredPlayersResponse getNumRequiredPlayersResponse() {
        synchronized (numRequiredPlayersResponseLockObject) {
            try {
                numRequiredPlayersResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return numRequiredPlayersResponse;
    }

    /**
     * This method is used to retrieve the response for the uniqueness of the nickname.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it prints the stack trace.
     *
     * @return The CheckUniqueNickNameResponse object containing the response for the uniqueness of the nickname.
     */
    public CheckUniqueNickNameResponse getCheckUniqueNickNameResponse() {
        synchronized (checkUniqueNickNameResponseLockObject) {
            try {
                checkUniqueNickNameResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return checkUniqueNickNameResponse;
    }

    /**
     * This method is used to retrieve the response for the available games.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it prints the stack trace.
     *
     * @return The DisplayAvailableGamesResponse object containing the response for the available games.
     */
    public DisplayAvailableGamesResponse getDisplayAvailableGamesResponse() {
        synchronized (displayAvailableGamesResponseLockObject) {
            try {
                displayAvailableGamesResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return displayAvailableGamesResponse;
    }

    /**
     * This method is used to retrieve the response for the available colors.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it prints the stack trace.
     *
     * @return The GetAvailableColorsResponse object containing the response for the available colors.
     */
    public GetAvailableColorsResponse getAvailableColorsResponse() {
        synchronized (getAvailableColorsResponseLockObject) {
            try {
                getAvailableColorsResponseLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getAvailableColorsResponse;
    }

    /**
     * This method is used to retrieve the response for the initial card extraction.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The ExtractInitialCardResponse object containing the response for the initial card extraction.
     */
    public ExtractInitialCardResponse getInitialCardResponse() {
        synchronized (initialCardResponseLockObject) {
            try {
                initialCardResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return initialCardResponse;
    }

    /**
     * This method is used to retrieve the response for the players in the game.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetPlayersResponse object containing the response for the players in the game.
     */
    public GetPlayersResponse getPlayersResponse() {
        synchronized (getPlayerResponseLockObject) {
            try {
                getPlayerResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getPlayerResponse;
    }

    /**
     * This method is used to retrieve the response for the game model.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetModelResponse object containing the response for the game model.
     */
    public GetModelResponse getModelResponse() {
        synchronized (getModelresponseLockObject) {
            try {
                getModelresponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getModelResponse;
    }

    /**
     * This method is used to retrieve the response for the game creation.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The CreateGameResponse object containing the response for the game creation.
     */
    public CreateGameResponse createGameResponse() {
        synchronized (createGameResponseLockObject) {
            try {
                createGameResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return createGameResponse;
    }

    /**
     * This method is used to retrieve the response for joining a game.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The JoinGameResponse object containing the response for joining a game.
     */
    public JoinGameResponse getJoinGameResponse() {
        synchronized (joinGameResponseLockObject) {
            try {
                joinGameResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return joinGameResponse;
    }

    /**
     * This method is used to retrieve the response for a player joining the game.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The NotifyGamePlayerJoinedResponse object containing the response for a player joining the game.
     */
    public NotifyGamePlayerJoinedResponse getNotifyGamePlayerJoinedResponse() {
        synchronized (notifyGamePlayerJoinedResponseLockObject) {
            try {
                notifyGamePlayerJoinedResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return notifyGamePlayerJoinedResponse;
    }

    /**
     * This method is used to retrieve the response for the personal objective cards.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetPersonalObjectiveCardsResponse object containing the response for the personal objective cards.
     */
    public GetPersonalObjectiveCardsResponse getPersonalObjectiveCardsResponse() {
        synchronized (getPersonalObjectiveCardsResponseLockObject) {
            synchronized (getPersonalObjectiveCardsResponseLockObject) {
                try {
                    getPersonalObjectiveCardsResponseLockObject.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return getPersonalObjectiveCardsResponse;
    }

    /**
     * This method is used to retrieve the response for the listener.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetListenerResponse object containing the response for the listener.
     */
    public GetListenerResponse getListenerResponse() {
        synchronized (getListenerResponseLockObject) {
            try {
                getListenerResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getListenerResponse;
    }

    /**
     * This method is used to retrieve the response for the increment of players who chose an objective.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The IncrementPlayersWhoChoseObjectiveResponse object containing the response for the increment of players who chose an objective.
     */
    public IncrementPlayersWhoChoseObjectiveResponse getIncrementPlayersWhoChoseObjectiveResponse() {
        synchronized (incrementPlayersWhoChoseObjectiveResponseLockObject) {
            try {
                incrementPlayersWhoChoseObjectiveResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return incrementPlayersWhoChoseObjectiveResponse;
    }

    /**
     * This method is used to retrieve the response for the players who chose an objective.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetPlayersWhoChoseObjectiveResponse object containing the response for the players who chose an objective.
     */
    public GetPlayersWhoChoseObjectiveResponse getPlayersWhoChoseObjectiveResponse() {
        synchronized (getPlayersWhoChoseObjectiveResponseLockObject) {
            try {
                getPlayersWhoChoseObjectiveResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getPlayersWhoChoseObjectiveResponse;
    }

    /**
     * This method is used to retrieve the response for the extraction of player hand cards.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The ExtractPlayerHandCardsResponse object containing the response for the extraction of player hand cards.
     */
    public ExtractPlayerHandCardsResponse extractPlayerHandCardsResponse() {
        synchronized (extractPlayerHandCardsResponseLockObject) {
            try {
                extractPlayerHandCardsResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return extractPlayerHandCardsResponse;
    }

    /**
     * This method is used to retrieve the response for the validity of a move.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The IsValidMoveResponse object containing the response for the validity of a move.
     */
    public IsValidMoveResponse isValidMoveResponse() {
        synchronized (isValidMoveResponseLockObject) {
            try {
                isValidMoveResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return isValidMoveResponse;
    }

    /**
     * This method is used to retrieve the response for the game status.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The GetStatusResponse object containing the response for the game status.
     */
    public GetStatusResponse getStatusResponse() {
        synchronized (getStatusResponseLockObject) {
            try {
                getStatusResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return getStatusResponse;
    }

    /**
     * This method is used to retrieve the response for the update of players.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The UpdatePlayersResponse object containing the response for the update of players.
     */
    public UpdatePlayersResponse getUpdatePlayersResponse() {
        synchronized (updatePlayersResponseLockObject) {
            try {
                updatePlayersResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return updatePlayersResponse;
    }

    /**
     * This method is used to retrieve the response for the final ranking of players.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The FinalRankingResponse object containing the response for the final ranking of players.
     */
    public FinalRankingResponse getFinalRankingResponse() {
        synchronized (finalRankingResponseLockObject) {
            try {
                finalRankingResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return finalRankingResponse;
        }
    }

    /**
     * This method is used to retrieve the response for a disconnect event.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The DisconnectResponse object containing the response for a disconnect event.
     */
    public DisconnectResponse getDisconnectResponse() {
        synchronized (disconnectResponseLockObject) {
            try {
                disconnectResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return disconnectResponse;
        }
    }

    /**
     * This method is used to retrieve the response for removing an available color.
     * It waits until the response is available by synchronizing on the lock object associated with the response.
     * If the thread is interrupted while waiting, it throws a RuntimeException.
     *
     * @return The RemoveAvailableColorResponse object containing the response for removing an available color.
     */
    public RemoveAvailableColorResponse removeAvailablecolorResponse() {
        synchronized (removeAvailableColorResponseLockObject) {
            try {
                removeAvailableColorResponseLockObject.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return removeAvailableColorResponse;
        }
    }
}

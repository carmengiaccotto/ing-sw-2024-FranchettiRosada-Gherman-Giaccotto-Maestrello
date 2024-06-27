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

public class ClientListener extends Thread {
    private ClientController clientController;
    private ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private NumRequiredPlayersResponse numRequiredPlayersResponse;
    private final Object numRequiredPlayersResponseLockObject = new Object();
    private CheckUniqueNickNameResponse checkUniqueNickNameResponse;
    private final Object checkUniqueNickNameResponseLockObject = new Object();
    private DisplayAvailableGamesResponse displayAvailableGamesResponse;
    private final Object displayAvailableGamesResponseLockObject = new Object();
    public GetAvailableColorsResponse getAvailableColorsResponse;
    private final Object getAvailableColorsResponseLockObject = new Object();
    public ExtractInitialCardResponse initialCardResponse;
    private final Object initialCardResponseLockObject = new Object();
    public GetPlayersResponse getPlayerResponse;
    private final Object getPlayerResponseLockObject = new Object();
    public GetModelResponse getModelResponse;
    private final Object getModelresponseLockObject = new Object();
    private CreateGameResponse createGameResponse;
    private final Object createGameResponseLockObject = new Object();
    private final Object getChatResponseLockObject = new Object();
    private JoinGameResponse joinGameResponse;
    private final Object joinGameResponseLockObject = new Object();
    private NotifyGamePlayerJoinedResponse notifyGamePlayerJoinedResponse;
    private final Object notifyGamePlayerJoinedResponseLockObject = new Object();
    private GetPersonalObjectiveCardsResponse getPersonalObjectiveCardsResponse;
    private final Object getPersonalObjectiveCardsResponseLockObject = new Object();
    private GetListenerResponse getListenerResponse;
    private final Object getListenerResponseLockObject = new Object();
    private IncrementPlayersWhoChoseObjectiveResponse incrementPlayersWhoChoseObjectiveResponse;
    private final Object incrementPlayersWhoChoseObjectiveResponseLockObject = new Object();
    private GetPlayersWhoChoseObjectiveResponse getPlayersWhoChoseObjectiveResponse;
    private final Object getPlayersWhoChoseObjectiveResponseLockObject = new Object();
    private ExtractPlayerHandCardsResponse extractPlayerHandCardsResponse;
    private final Object extractPlayerHandCardsResponseLockObject = new Object();
    private IsValidMoveResponse isValidMoveResponse;
    private final Object isValidMoveResponseLockObject = new Object();
    private GetStatusResponse getStatusResponse;
    private final Object getStatusResponseLockObject = new Object();
    private UpdatePlayersResponse updatePlayersResponse;
    private final Object updatePlayersResponseLockObject = new Object();
    private FinalRankingResponse finalRankingResponse;
    private final Object finalRankingResponseLockObject = new Object();
    private DisconnectResponse disconnectResponse;
    private final Object disconnectResponseLockObject = new Object();


    public ClientListener(ObjectOutputStream oos, ObjectInputStream ois, ClientController clientController) {
        this.outputStream = oos;
        this.inputStream = ois;
        this.clientController = clientController;
    }

    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(message);
            outputStream.flush();
        }
    }

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
                            } catch (RemoteException e) {
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
                                clientController.WhatDoIDoNow(((WhatDoIDoNowMessage) message).getDoThis());
                                sendMessage(new WhatDoIDoNowResponse());
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
                        } else if (message instanceof GetPlayersResponse) {
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
}

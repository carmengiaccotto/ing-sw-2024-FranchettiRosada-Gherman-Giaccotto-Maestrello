package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerListener extends Thread {
    private final ClientControllerInterface client;
    private GameController gamecontroller;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final MainControllerInterface mainController;
    private GetNickNameResponse getNicknameResponse;
    private final Object getNicknameResponseLockObject = new Object();
    private ChooseNicknameResponse chooseNicknameResponse;
    private final Object chooseNicknameResponseLockObject = new Object();
    private GetPawnColorResponse getPawnColorResponse;
    private final Object getPawnColorResponseLockObject = new Object();
    private ChooseCardToPlayResponse chooseCardToPlayResponse;
    private final Object chooseCardToPlayResponseLockObject = new Object();
    private GetPlayerResponse getPlayerResponse;
    private final Object getPlayerResponseLockObject = new Object();
    private GetScoreResponse getScoreResponse;
    private final Object getScoreResponseLockObject = new Object();
    private GetRoundResponse getRoundResponse;
    private final Object getRoundResponseLockObject = new Object();
    private GetPersonalObjectiveCardResponse getPersonalObjectiveCardResponse;
    private final Object getPersonalObjectiveCardResponseLockObject = new Object();
    private WhatDoIDoNowResponse whatDoIDoNowResponse;
    private final Object whatDoIDoNowResponseLockObject = new Object();


    public ServerListener(ObjectOutputStream oos, ObjectInputStream ois, ClientControllerInterface client, MainControllerInterface mainController) {
        this.client = client;
        this.mainController = mainController;
        this.outputStream = oos;
        this.inputStream = ois;
    }

    public void setGamecontroller(GameController game) {
        this.gamecontroller = game;
    }


    private void sendMessage(GenericMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public void run() {
        try {
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
                        } catch (RemoteException e) {
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
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (message instanceof AddNicknameMessage) {
                        try {
                            mainController.addNickname(((AddNicknameMessage) message).getNickname());
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
                    }
                });
                thread.start();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

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

    public WhatDoIDoNowResponse whatDoIDoNowResponse() {
        synchronized (whatDoIDoNowResponseLockObject) {
        try {
            whatDoIDoNowResponseLockObject.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
        return whatDoIDoNowResponse;
    }
}

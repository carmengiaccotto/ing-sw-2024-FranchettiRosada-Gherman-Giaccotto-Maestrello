package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerListener extends Thread {
    private final ClientControllerInterface client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final MainControllerInterface mainController;
    private GetNickNameResponse getNicknameResponse;
    private ChooseNicknameResponse chooseNicknameResponse;
    private GetPawnColorResponse getPawnColorResponse;
    private ChooseCardToPlayResponse chooseCardToPlayResponse;
    private GetPlayerResponse getPlayerResponse;
    private GetScoreResponse getScoreResponse;
    private GetRoundResponse getRoundResponse;
    private GetPersonalObjectiveCardResponse getPersonalObjectiveCardResponse;

    public ServerListener(Socket socket, ClientControllerInterface client, MainControllerInterface mainController) {
        this.client = client;
        this.mainController = mainController;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(GenericMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public void run() {
        try {
            while (true) {
                Object message = inputStream.readObject();
                if(message instanceof NumRequiredPlayersMessage) {
                    ArrayList<Pair<Integer, Integer>> numRequiredPlayers = mainController.numRequiredPlayers();
                    sendMessage(new NumRequiredPlayersResponse(numRequiredPlayers));
                } else if (message instanceof ConnectMessage) {
                    mainController.connect(client);
                } else if (message instanceof CheckUniqueNickNameMessage) {
                    boolean isUnique = mainController.checkUniqueNickName(((CheckUniqueNickNameMessage) message).getNickname());
                    sendMessage(new CheckUniqueNickNameResponse(isUnique));
                } else if(message instanceof JoinGameMessage) {
                    mainController.joinGame(client, ((JoinGameMessage)message).getGameId());
                } else if (message instanceof DisplayAvailableGamesMessage) {
                    Map availableGames = mainController.DisplayAvailableGames();
                    sendMessage(new DisplayAvailableGamesResponse(availableGames));
                } else if(message instanceof CreateGameMessage) {
                    mainController.createGame(client, ((CreateGameMessage)message).getMaxNumberOfPlayers());
                }  else if(message instanceof NotifyGamePlayerJoinedMessage) {
//                    mainController.NotifyGamePlayerJoined();
                }   else if(message instanceof AddNicknameMessage) {
                    mainController.addNickname(((AddNicknameMessage)message).getNickname());
                } else if (message instanceof GetNickNameResponse) {
                    synchronized (this) {
                        getNicknameResponse = (GetNickNameResponse) message;
                        getNicknameResponse.notify();
                    }
                } else if (message instanceof ChooseNicknameResponse) {
                    synchronized (this) {
                        chooseNicknameResponse = (ChooseNicknameResponse) message;
                        chooseNicknameResponse.notify();
                    }
                } else if (message instanceof GetPawnColorResponse) {
                    synchronized (this) {
                        getPawnColorResponse = (GetPawnColorResponse) message;
                        getPawnColorResponse.notify();
                    }
                } else if (message instanceof GetPlayerResponse) {
                    synchronized (this) {
                        getPlayerResponse = (GetPlayerResponse) message;
                        getPlayerResponse.notify();
                    }
                } else if (message instanceof ChooseCardToPlayResponse) {
                    synchronized (this) {
                        chooseCardToPlayResponse = (ChooseCardToPlayResponse) message;
                        chooseCardToPlayResponse.notify();
                    }
                } else if (message instanceof GetScoreResponse) {
                    synchronized (this) {
                        getScoreResponse = (GetScoreResponse) message;
                        getScoreResponse.notify();
                    }
                } else if (message instanceof GetRoundResponse) {
                    synchronized (this) {
                        getRoundResponse = (GetRoundResponse) message;
                        getRoundResponse.notify();
                    }
                } else if (message instanceof GetPersonalObjectiveCardResponse) {
                    synchronized (this) {
                        getPersonalObjectiveCardResponse = (GetPersonalObjectiveCardResponse) message;
                        getPersonalObjectiveCardResponse.notify();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public GetNickNameResponse getNicknameResponse() {
        try {
            getNicknameResponse.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getNicknameResponse;
    }

    public ChooseNicknameResponse chooseNicknameResponse() {
        try {
            chooseNicknameResponse.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return chooseNicknameResponse;
    }

    public GetPawnColorResponse getPawnColorResponse() {
        try {
            getPawnColorResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return getPawnColorResponse;
    }

    public GetPlayerResponse getPlayerResponse() {
        try {
            getPlayerResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return getPlayerResponse;
    }

    public ChooseCardToPlayResponse chooseCardToPlayResponse() {
        try {
            chooseCardToPlayResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return chooseCardToPlayResponse;
    }

    public GetScoreResponse gerScoreResponse() {
        try {
            getScoreResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return getScoreResponse;
    }

    public GetRoundResponse getRoundResponse() {
        try {
            getRoundResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return getRoundResponse;
    }

    public GetPersonalObjectiveCardResponse getPersonalObjectiveCardResponse() {
        try {
            getPersonalObjectiveCardResponse.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return getPersonalObjectiveCardResponse;
    }
}

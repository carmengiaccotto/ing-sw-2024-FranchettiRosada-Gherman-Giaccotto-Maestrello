package it.polimi.ingsw.Connection.Socket.Server;

import it.polimi.ingsw.Connection.Socket.Messages.*;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ServerCallsToClient implements ClientControllerInterface, Serializable {
    private final ObjectOutputStream oos;
    private ServerListener serverListener;

    private MainControllerInterface mainController;
    private GameControllerInterface gameController;

    private String nickname;


    public ServerCallsToClient(ObjectOutputStream oos, ObjectInputStream ois, MainControllerInterface mainController) throws IOException {
        this.oos = oos;
        this.mainController = mainController;


        serverListener = new ServerListener(oos, ois, this, mainController);
        serverListener.start();
    }

    private void sendMessage(GenericMessage message) throws IOException {
        synchronized (oos) {
            oos.writeObject(message);
            oos.flush();
        }
    }

    /**
     * @param server
     * @throws RemoteException
     */
    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        return;
    }

    /**
     * @param view
     * @throws RemoteException
     */
    @Override
    public void setView(UserInterface view) throws RemoteException {
        return;
    }

    /**
     * @throws RemoteException
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
     * @throws RemoteException
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
     * @throws RemoteException
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
     * @return
     * @throws RemoteException
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
     * @param nickname
     * @throws RemoteException
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
     * @throws RemoteException
     */
    @Override
    public void JoinOrCreateGame() throws RemoteException {
        try {
            sendMessage(new JoinOrCreateGameMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {

    }

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
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
     * @throws RemoteException
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
     * @throws RemoteException
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
     * @return
     * @throws RemoteException
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
     * @param objectiveCard
     * @throws RemoteException
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
     * @return
     * @throws RemoteException
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
     * @param playGround
     * @throws RemoteException
     */
    @Override
    public void showBoardAndPlayAreas(PlayGround playGround) throws RemoteException {
        try {
            sendMessage(new ShowBoardAndPlayAreasMessage(playGround));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param playGround
     * @return
     * @throws RemoteException
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
     * @param message
     * @throws RemoteException
     */
    @Override
    public void sendUpdateMessage(String message) throws RemoteException {
        try {
            sendMessage(new UpdateMessage(message));
        } catch (IOException ex) {
        }
    }

    /**
     * @throws RemoteException
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
     * @param card
     * @throws RemoteException
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
     * @return
     * @throws RemoteException
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
     * @return
     * @throws RemoteException
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

    @Override
    public void setRound(int round) throws RemoteException {

    }

    /**
     * @param game
     * @throws RemoteException
     */
    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        this.gameController = game;
        serverListener.setGamecontroller((GameController) game);
    }

    /**
     * @return
     * @throws RemoteException
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
     * @throws RemoteException
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
     * @param doThis
     * @throws RemoteException
     */
    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {
        try {
            sendMessage(new WhatDoIDoNowMessage(doThis));
            WhatDoIDoNowResponse response = serverListener.whatDoIDoNowResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

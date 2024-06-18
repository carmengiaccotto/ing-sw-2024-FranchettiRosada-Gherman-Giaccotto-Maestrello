package it.polimi.ingsw.Controller.Client;

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
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientControllerInterface extends Remote, Serializable {

    public void setServer(MainControllerInterface server) throws RemoteException;
    void setView(UserInterface view) throws RemoteException;

    void disconnect() throws RemoteException;

    void ChoosePawnColor() throws RemoteException;

    void JoinGame() throws RemoteException;

    String getNickname() throws RemoteException;

    void setNickname(String nickname) throws RemoteException;

    void JoinOrCreateGame() throws RemoteException;

    String ChooseNickname() throws IOException, ClassNotFoundException;

    void  newGameSetUp() throws RemoteException;

    void Wait() throws RemoteException;

    PawnColor getPawnColor() throws RemoteException;

    void updatePlayers(List<ClientControllerInterface> players)throws RemoteException;

    void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException;

    Player getPlayer() throws RemoteException;

    void showBoardAndPlayAreas(PlayGround m) throws RemoteException;

    void chooseCardToDraw(PlayGround m) throws RemoteException;

    SideOfCard chooseCardToPlay() throws RemoteException;

    void sendUpdateMessage(String message) throws RemoteException;

    void connect()  throws RemoteException;

    void addCardToHand(PlayCard card) throws RemoteException;

    int getScore() throws RemoteException;

    int getRound() throws RemoteException;

    void setGame(GameControllerInterface game) throws RemoteException;

    ObjectiveCard getPersonalObjectiveCard() throws RemoteException;

    void JoinLobby() throws RemoteException;

    void WhatDoIDoNow (String doThis) throws RemoteException;
}
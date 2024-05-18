package it.polimi.ingsw.Controller.Game;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameControllerInterface extends  Remote{
    //Player Configuration Methods
    public GameListener getListener();
    public int getNumPlayers();
    public void addPlayer(ClientControllerInterface client);
    public GameStatus getStatus();
    public void setStatus(GameStatus status);
    public void CheckMaxNumPlayerReached();
    public List<PawnColor> AvailableColors(List<ClientControllerInterface> clients);
    public void NotifyNewPlayerJoined(ClientControllerInterface newPlayer);
    public int getId();
    public void receiveMessage(Command c, ClientControllerInterface client) throws RemoteException;
    public PlayGround getModel();
    public void setModel(PlayGround model);


}

package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Connection.VirtualClient;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameController implements  Runnable, Serializable {

    private List<VirtualClient> players = new ArrayList<>();
    private GameStatus status;
    private final int numPlayers;
    private final int id;
    private final PlayGround model;

    public GameController(int id, int n) {
        this.status = GameStatus.WAITING;
        this.numPlayers=n;
        this.id=id;
        try {
            model = new PlayGround(this.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(!status.equals(GameStatus.ENDED)){
            performAction(GameStatus currentStatus);
        }

        while(status.equals(GameStatus.RUNNING)){
            receiveMessage(Command c);
        }



    }


    public synchronized List<VirtualClient> getPlayers() {
        return players;
    }

    public int getNumPlayers() {

        return numPlayers;
    }
    public synchronized void addPlayer(VirtualClient client){
        players.add(client);
    }


    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public void CheckMaxNumPlayerReached(){
        if(players.size()==numPlayers){
            setStatus(GameStatus.SETUP);
            run();
        }
        else{
            for(VirtualClient client: players){
                try {
                    client.Wait();
                } catch (RemoteException e) {
                    System.out.println("Problem connecting to view");
                }
            }

        }

    }


    public List<PawnColor> AvailableColors(List<VirtualClient> clients) {

        List<PawnColor> colors = clients.stream()
                .map(client -> {
                    try {
                        return client.getPlayer().getPawnColor();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        List<PawnColor> available = new ArrayList<>();

        for (PawnColor color : PawnColor.values()) {
            if (!colors.contains(color)) {
                available.add(color);
            }
        }
        return available;
    }

    public void setPlayers(List<VirtualClient> players){
        this.players=players;

    }

    public synchronized void NotifyNewPlayerJoined(VirtualClient newPlayer) {
        for (VirtualClient player : players) {
            try {
                player.updatePlayers(players);
            } catch (RemoteException e) {
                System.out.println("An error Occurred dutìring Players update: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            newPlayer.setPawnColor((ArrayList<PawnColor>) AvailableColors(players));
        } catch (RemoteException e) {
            System.out.println("An error occured");
        }
        //TODO
        System.out.println("reached this point");
        CheckMaxNumPlayerReached();
    }

    public int getId() {
        return id;
    }

    public void PlayGroundSetUp(){

    }

    void receiveMessage(Command c){
        switch (c){
            case CHAT ->{
                //broadcast chat
            }
            case MOVE ->{
                // check se è current player
                //aggiungi carta se si
                //digli fatti i fatti tuoi se no
            }
        }
    }

    void performAction(GameStatus status){

    }




}

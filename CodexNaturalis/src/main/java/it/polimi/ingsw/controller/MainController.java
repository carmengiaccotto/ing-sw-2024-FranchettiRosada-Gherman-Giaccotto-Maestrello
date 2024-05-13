package it.polimi.ingsw.controller;

import it.polimi.ingsw.Connection.Client;
import it.polimi.ingsw.model.PlayGround.Player;

import java.util.ArrayList;

public class MainController {

    private final ArrayList<GameController> games = new ArrayList<>();
    private final ArrayList<Client> clients = new ArrayList<>();

    public MainController() {
    }

    /** Add a player to Game's lobby.*/
    public void addClientToLobby(String nickName, int ID) {
        Client c = new Client();
        games.get(ID).getClients().add(c);
        Player p = new Player();
        c.setPlayer(p);
        p.setNickname(nickName);
        clients.add(c);
    }

    /** Check if the nickname is unique*/
    public boolean checkUniqueNickname(String name) {
        for (Client c : clients) {
            if (name.equals(c.getPlayer().getNickname())) {
                return false;
            }
        }
        return true;
    }

    /** Create a new Game and add the Player.*/
    public void createGame(String nickname, int numOfPlayers, GameController game, Client c){
        games.add(game);
        clients.add(c);
        game.getClients().add(c);
        Player p = new Player();
        p.setNickname(nickname);
        c.setPlayer(p);
        game.getModel().setNumOfPlayers(numOfPlayers);
        game.getModel().setGameId(games.size());
    }

    /** It checks if the new client that wants to connect
     should join an existing game or if he should create a new one.*/
    public String joinOrcreate(){
        if(games.isEmpty()){
            return "CREATE";
        }else{
            if(games.get(games.size() - 1).getClients().size() < games.get(games.size() - 1).getModel().getNumOfPlayers()){
                return "JOIN";
            }else{
                return "CREATE";
            }
       }
    }

    public ArrayList<GameController> getGames(){
        return this.games;
    }
}

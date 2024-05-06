package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Client;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;

import java.util.ArrayList;

public class MainController {

    private final ArrayList<GameController> games = null;
    private final ArrayList<Client> clients = null;

    /** Add a player to Game's lobby.*/
    public void addClientToLobby(String nickName, PawnColor color) {
        Client c = new Client();
        games.get(games.size() - 1).getClients().add(c);
        Player p = new Player();
        c.setPlayer(p);
        p.setNickname(nickName);
        p.setPawnColor(color);
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
    public void createGame(String nickname, int numOfPlayers, PawnColor color){
        GameController game = new GameController();
        games.add(game);
        Client c = new Client();
        clients.add(c);
        game.getClients().add(c);
        Player p = new Player();
        c.setPlayer(p);
        p.setNickname(nickname);
        p.setPawnColor(color);
        game.getModel().setNumOfPlayers(numOfPlayers);
        game.getModel().setGameId(games.size());
    }

    /** It checks if the new client that wants to connect
     should join an existing game or if he should create a new one.*/
    public String joinOrcreate(){
        int num = games.get(games.size() - 1).getModel().getNumOfPlayers();
        if(games.size() < num){
            return "JOIN";
        }else{
            return "CREATE";
        }
    }
}

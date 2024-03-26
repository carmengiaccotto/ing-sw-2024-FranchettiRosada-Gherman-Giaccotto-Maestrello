package CodexNaturalis.src.main.java.it.polimi.ingsw.model;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class PlayGround {

    private HashMap<Player, PlayArea> Players;
    private ArrayList<Deck> Decks;
    private ArrayList<Card>  CommonCards;
    private ArrayList<ObjectiveCard> CommonObjectives;
    private String CurrentPlayer;
    private GameStatus gameStatus;

    public PlayGround(HashMap<Player, PlayArea> players, ArrayList<Deck> decks, ArrayList<Card> commonCards, ArrayList<ObjectiveCard> commonObjectives, String currentPlayer, GameStatus gameStatus) {
        Players = players;
        Decks = decks;
        CommonCards = commonCards;
        CommonObjectives = commonObjectives;
        CurrentPlayer = currentPlayer;
        this.gameStatus = gameStatus;
    }

    public Players getPlayers(){
    Player Player;
    return Players.get(Player);
}
public HashMap<Player, PlayArea> AddPlayer(HashMap<Player, PlayArea> Players, Player player, PlayArea playarea){
        Players.put(player, playarea);
        return Players;
}
public boolean CheckUniqueNickNames(){
        for(Player chiave1: Players.keySet().getNickname){
            for (Player chiave2: players.keySet().getNickname){
                if(!chiave1.equals(chiav2)){
                    return TRUE;
                }
                else
                    return FALSE;
            }

    }
}

public ArrayList<Card> addCommonCard(Card){
        CommonCards.add(Card);
        return CommonCards;
}

public ArrayList<Card> getCommonCard(){
        return CommonCards;

}
public initializeGame(){

}

public void getGameStatus(){
        return gameStatus;
}
public void setGameStatus(gameStatus){
        this.gameStatus = gameStatus;
}

public AddObjectiveScore(){

}

public void getCommonOjective(){
        return CommonObjectives;
}

public string Winner(){
        if(CurrentPlayer)
        return CurrentPlayer
}

public void setCurrentPlayer(CurrentPlayer){
        this.CurrentPlayer = CurrentPlayer;
}

public string getCurrentPlayer(){
        return CurrentPlayer;
}

}

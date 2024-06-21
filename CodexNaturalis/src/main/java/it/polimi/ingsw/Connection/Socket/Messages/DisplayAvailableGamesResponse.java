package it.polimi.ingsw.Connection.Socket.Messages;

import java.util.ArrayList;
import java.util.Map;

public class DisplayAvailableGamesResponse extends GenericMessage{
    private Map<Integer, ArrayList<String>> availableGames;
    public DisplayAvailableGamesResponse(Map<Integer, ArrayList<String>> availableGames){
        this.availableGames = availableGames;
    }
    public Map<Integer, ArrayList<String>> getAvailableGames(){
        return availableGames;
    }
}

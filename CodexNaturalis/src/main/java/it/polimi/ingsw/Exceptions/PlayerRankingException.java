package it.polimi.ingsw.Exceptions;

public class PlayerRankingException extends RuntimeException {
    public PlayerRankingException(String message, Throwable cause) {
        super(message, cause);
    }
}

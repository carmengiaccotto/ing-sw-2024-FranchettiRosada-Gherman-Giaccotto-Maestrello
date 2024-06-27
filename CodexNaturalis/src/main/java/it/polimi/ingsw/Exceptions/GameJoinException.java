package it.polimi.ingsw.Exceptions;

public class GameJoinException extends RuntimeException {
    public GameJoinException(String message, Throwable cause) {
        super(message, cause);
        System.out.println("an error occurred while joining the game");
    }
}

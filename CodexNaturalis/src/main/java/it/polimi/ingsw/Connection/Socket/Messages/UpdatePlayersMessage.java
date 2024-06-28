package it.polimi.ingsw.Connection.Socket.Messages;

import it.polimi.ingsw.Model.PlayGround.PlayGround;

/**
 * This class extends the GenericMessage class and is used to represent an update players message.
 * It contains a String object representing the message, a PlayGround object representing the game's play area,
 * and a String object representing the nickname.
 */
public class UpdatePlayersMessage extends GenericMessage {
    // The message to be updated.
    private String message;
    // The game's play area.
    private PlayGround playGround;
    // The nickname to be updated.
    private String nickname;

    /**
     * Constructs a new UpdatePlayersMessage with the specified message.
     * @param message the message to be updated
     */
    public UpdatePlayersMessage(String message) {
        this.message = message;
    }

    /**
     * Constructs a new UpdatePlayersMessage with the specified message and nickname.
     * @param message the message to be updated
     * @param nickname the nickname to be updated
     */
    public UpdatePlayersMessage(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }

    /**
     * Constructs a new UpdatePlayersMessage with the specified play area.
     * @param model the game's play area
     */
    public UpdatePlayersMessage(PlayGround model) {
        this.playGround = model;
    }

    /**
     * Returns the nickname.
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the game's play area.
     * @return the game's play area
     */
    public PlayGround getPlayGround() {
        return playGround;
    }
}
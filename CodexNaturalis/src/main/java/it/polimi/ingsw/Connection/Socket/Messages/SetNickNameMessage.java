package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a set nickname message.
 * It contains a String object representing the nickname.
 */
public class SetNickNameMessage extends GenericMessage {
    /**
    * The nickname to be set.
     */
    private final String nickname;

    /**
     * Constructs a new SetNickNameMessage with the specified nickname.
     * @param nickname the nickname to be set
     */
    public SetNickNameMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the nickname.
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }
}

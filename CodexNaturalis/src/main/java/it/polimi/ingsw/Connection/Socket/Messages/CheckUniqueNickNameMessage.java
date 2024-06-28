package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a message that checks for a unique nickname.
 * It contains a String object that represents the nickname to be checked.
 */
public class CheckUniqueNickNameMessage extends GenericMessage {
    /**
     * The nickname of the user.
     * This is a private instance variable used to store the nickname of the user.
     */
    private String nickname;

    /**
     * This constructor creates a new CheckUniqueNickNameMessage with the given nickname.
     *
     * @param nickname The nickname to be checked.
     */
    public CheckUniqueNickNameMessage(String nickname) {
        super();
        this.nickname = nickname;
    }

    /**
     * This method is used to get the nickname.
     *
     * @return The nickname.
     */
    public String getNickname() {
        return nickname;
    }
}
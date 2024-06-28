package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a message that adds a nickname.
 * It contains a String object that represents the nickname to be added.
 */
public class AddNicknameMessage extends GenericMessage {
    private String nickname;

    /**
     * This constructor creates a new AddNicknameMessage with the given nickname.
     *
     * @param nickname The nickname to be added.
     */
    public AddNicknameMessage(String nickname) {
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
package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a get nickname response message.
 * It contains a String object representing the nickname.
 */
public class GetNickNameResponse extends GenericMessage{
    /**
    * The String object representing the nickname.
     */
    private final String nickName;

    /**
     * This constructor creates a new GetNickNameResponse with the given nickname.
     *
     * @param nickName The String object representing the nickname.
     */
    public GetNickNameResponse(String nickName) {
        this.nickName = nickName;
    }

    /**
     * This method is used to get the nickname.
     *
     * @return The String object representing the nickname.
     */
    public String getNickName() {
        return nickName;
    }
}
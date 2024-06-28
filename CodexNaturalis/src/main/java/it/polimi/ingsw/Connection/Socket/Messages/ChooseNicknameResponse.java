package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a response message that chooses a nickname.
 * It contains a String object that represents the chosen nickname.
 */
public class ChooseNicknameResponse extends GenericMessage{
    /**
     * The nickname of the user.
     * This is a private instance variable used to store the nickname of the user.
     */
    private String nickname;

    /**
     * This constructor creates a new ChooseNicknameResponse with the given String object.
     *
     * @param nickname The String object representing the chosen nickname.
     */
    public ChooseNicknameResponse(String nickname){
        this.nickname = nickname;
    }

    /**
     * This method is used to get the String object representing the chosen nickname.
     *
     * @return The String object representing the chosen nickname.
     */
    public String getNickname(){
        return nickname;
    }
}
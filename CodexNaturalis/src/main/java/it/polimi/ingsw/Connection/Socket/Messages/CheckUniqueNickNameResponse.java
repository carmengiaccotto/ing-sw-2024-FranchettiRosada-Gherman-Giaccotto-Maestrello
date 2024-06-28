package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a response message that checks if a nickname is unique.
 * It contains a boolean value that indicates whether the nickname is unique or not.
 */
public class CheckUniqueNickNameResponse extends GenericMessage {
    /**
     * This is a private member variable of the CheckUniqueNickNameResponse class.
     * It is a boolean that indicates whether a nickname is unique or not.
     * If it is true, the nickname is unique. If it is false, the nickname is not unique.
     * It is set in the constructor and can be accessed via the getIsUnique() method.
     */
    private boolean isUnique;

    /**
     * This constructor creates a new CheckUniqueNickNameResponse with the given boolean value.
     *
     * @param isUnique The boolean value indicating whether the nickname is unique or not.
     */
    public CheckUniqueNickNameResponse(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * This method is used to get the boolean value indicating whether the nickname is unique or not.
     *
     * @return The boolean value indicating whether the nickname is unique or not.
     */
    public boolean getIsUnique() {
        return isUnique;
    }
}
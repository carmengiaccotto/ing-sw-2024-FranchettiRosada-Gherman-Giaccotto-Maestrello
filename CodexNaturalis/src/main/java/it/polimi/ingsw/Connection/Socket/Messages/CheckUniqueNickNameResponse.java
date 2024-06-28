package it.polimi.ingsw.Connection.Socket.Messages;

/**
 * This class extends the GenericMessage class and is used to represent a response message that checks if a nickname is unique.
 * It contains a boolean value that indicates whether the nickname is unique or not.
 */
public class CheckUniqueNickNameResponse extends GenericMessage {
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
package it.polimi.ingsw.Connection.Socket.Messages;

public class CheckUniqueNickNameResponse extends GenericMessage {
    private boolean isUnique;

    public CheckUniqueNickNameResponse(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean getIsUnique() {
        return isUnique;
    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;

public class Card {
    private int idCard;

    /**Class Constructor*/
    protected Card(int idCard) {
        this.idCard = idCard;
    }

    public Card mapFromJson(JsonObject jsonObject){
        this.idCard = jsonObject.get("id").getAsInt();
        return new Card(idCard );
    }

    /**Getter method for Card's Id.
     * @return idCard*/
    public int getIdCard() {
        return idCard;
    }

    /**Setter method for Card's Id.
     * @param idCard assigned to every play Card*/
    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }
}

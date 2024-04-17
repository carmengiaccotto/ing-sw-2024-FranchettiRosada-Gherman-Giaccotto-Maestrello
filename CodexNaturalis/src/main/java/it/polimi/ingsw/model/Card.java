package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;

public class Card {
    private int idCard;

    protected Card(int idCard) {
        this.idCard = idCard;
    }

    public Card mapFromJson(JsonObject jsonObject){
        this.idCard = jsonObject.get("id").getAsInt();
        return new Card(idCard );
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }
}

package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;
/**Physical Card*/
public class Card {
    /**JSON Card ID*/
    private int idCard;

    /**Class Constructor*/
    public Card(int idCard) {
        this.idCard = idCard;
    }


    /**JSON  Card Constructor
     * @param jsonObject  JSON
     * @return Card with JSON card ID */
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

package it.polimi.ingsw.Model.Cards;

import java.io.Serializable;

/**Physical Card*/
public class Card implements Serializable {
    /**JSON Card ID*/
    private int idCard;

    /**Class Constructor*/
    public Card(int idCard) {
        if(idCard <0)
            throw new IllegalArgumentException("ID cannot be negative");
        else
            this.idCard = idCard;
    }



    /**Getter method for Card's ID.
     * @return idCard*/
    public int getIdCard() {
        return idCard;
    }

    /**Setter method for Card's ID.
     * @param idCard assigned to every play Card*/
    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }
}

package it.polimi.ingsw.Model.Cards;

import java.io.Serializable;

/**
 * This class represents a physical card in the game.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each card has an ID, which is a unique identifier for the card.
 */
public class Card implements Serializable {

    /**
     * The ID of the card. This is a unique identifier for the card.
     * It is final because once a card is created, its ID cannot be changed.
     */
    private final int idCard;

    /**
     * This is the constructor for the Card class.
     * It initializes the idCard field with the provided value.
     * If the provided ID is negative, it throws an IllegalArgumentException.
     *
     * @param idCard The ID of the card. It must be a non-negative integer.
     * @throws IllegalArgumentException If idCard is negative.
     */
    public Card(int idCard) {
        if(idCard <0)
            throw new IllegalArgumentException("ID cannot be negative");
        else
            this.idCard = idCard;
    }

    /**
     * This method returns the ID of the card.
     *
     * @return The ID of the card.
     */
    public int getIdCard() {
        return idCard;
    }
}

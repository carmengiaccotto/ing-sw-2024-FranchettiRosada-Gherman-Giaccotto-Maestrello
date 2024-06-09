package it.polimi.ingsw.Model.Cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getIdCard() {
        Card card= new Card(1);
        assertEquals(1,card.getIdCard());
    }
    @Test
    void getIdCardNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Card(-1));
    }
}
package it.polimi.ingsw.Model.Enumerations;

import it.polimi.ingsw.Model.CardColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardColorsTest {//
    @Test
    public void testDefinedValues() {
        assertNotNull(CardColors.RED);
        assertNotNull(CardColors.GREEN);
        assertNotNull(CardColors.PURPLE);
        assertNotNull(CardColors.BLUE);
    }
    @Test
    public void testEquality() {
        assertEquals(CardColors.RED, CardColors.RED);
        Assertions.assertNotEquals(CardColors.RED, CardColors.GREEN);
    }

    @Test
    public void testSwitchStatement() {
        CardColors color = CardColors.GREEN;
        switch(color) {
            case RED:
                break;
            case GREEN:
                assertEquals(CardColors.GREEN, color);
                break;
            case PURPLE:
                break;
            case BLUE:
                break;
            default:
                throw new AssertionError("Unexpected color");
        }
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("RED", CardColors.RED.toString());
        assertEquals("GREEN", CardColors.GREEN.toString());
        assertEquals("PURPLE", CardColors.PURPLE.toString());
        assertEquals("BLUE", CardColors.BLUE.toString());
    }

}
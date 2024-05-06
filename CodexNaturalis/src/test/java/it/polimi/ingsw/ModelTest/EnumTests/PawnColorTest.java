package CodexNaturalis.src.test.java.it.polimi.ingsw.ModelTest.EnumTests;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PawnColorTest {
    @Test
    public void testDefinedValues() {
        assertNotNull(PawnColor.RED);
        assertNotNull(PawnColor.GREEN);
        assertNotNull(PawnColor.YELLOW);
        assertNotNull(PawnColor.BLUE);
    }
    @Test
    public void testEquality() {
        assertEquals(PawnColor.RED, PawnColor.RED);
        Assertions.assertNotEquals(PawnColor.RED, PawnColor.GREEN);
    }

    @Test
    public void testSwitchStatement() {
        PawnColor color = PawnColor.GREEN;
        switch(color) {
            case RED:
                break;
            case GREEN:
                assertEquals(PawnColor.GREEN, color);
                break;
            case YELLOW:
                break;
            case BLUE:
                break;
            default:
                throw new AssertionError("Unexpected color");
        }
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("RED", PawnColor.RED.toString());
        assertEquals("GREEN", PawnColor.GREEN.toString());
        assertEquals("YELLOW", PawnColor.YELLOW.toString());
        assertEquals("BLUE", PawnColor.BLUE.toString());
    }

}
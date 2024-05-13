package it.polimi.ingsw.ModelTest.EnumTests;

import it.polimi.ingsw.Model.Enumerations.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SideTest {
    @Test
    public void testDefinedValues() {
        assertNotNull(Side.FRONT);
        assertNotNull(Side.BACK);
        assertNotEquals(Side.FRONT, Side.BACK);
    }

    @Test
    public void testEquality() {
        assertEquals(Side.FRONT, Side.FRONT);
        assertEquals(Side.BACK, Side.BACK);
        assertNotEquals(Side.FRONT, Side.BACK);
    }

    @Test
    public void testSwitchStatement() {
        Side side = Side.BACK;
        switch(side) {
            case FRONT:
                assertEquals(Side.FRONT, side);
                break;
            case BACK:
                assertEquals(Side.BACK, side);
                break;
            default:
                fail("Unexpected side");
        }
    }

    @Test
    public void testStringRepresentation() {
        assertEquals("FRONT", Side.FRONT.toString());
        assertEquals("BACK", Side.BACK.toString());
    }

}
package it.polimi.ingsw.Model.JsonHandler;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerFactoryTest { //Tests Run, all test passed

    @Test
    public void testCreateCornerFromValidSymbol() {
        // Arrange
        String validSymbol = "FUNGI";

        // Act
        Corner corner = CornerFactory.createCornerFromJson(validSymbol);

        // Assert
        assertNotNull(corner);
        assertEquals(Symbol.FUNGI, corner.getSymbol());
        assertFalse(corner.isHidden());
    }

    @Test
    public void testCreateHiddenCorner() {
        String hiddenCorner = "HIDDEN";
        Corner corner = CornerFactory.createCornerFromJson(hiddenCorner);
        assertNotNull(corner);
        assertNull(corner.getSymbol());
        assertTrue(corner.isHidden());
    }

    @Test
    public void testCreateEmptyCorner() {
        String emptyCorner = "EMPTY";
        Corner corner = CornerFactory.createCornerFromJson(emptyCorner);
        assertNotNull(corner);
        assertNull(corner.getSymbol());
        assertFalse(corner.isHidden());
    }

    @Test
    public void testCreateCornerFromInvalidValue() {
        String invalidValue = "FLOWER";
        assertThrows(IllegalArgumentException.class, () -> {
            CornerFactory.createCornerFromJson(invalidValue);
        });
    }

}
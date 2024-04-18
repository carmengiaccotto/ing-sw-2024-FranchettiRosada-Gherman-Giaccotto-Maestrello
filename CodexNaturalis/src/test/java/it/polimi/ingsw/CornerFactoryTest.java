package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.CornerFactory;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerFactoryTest {

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

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            CornerFactory.createCornerFromJson(invalidValue);
        });
    }

}
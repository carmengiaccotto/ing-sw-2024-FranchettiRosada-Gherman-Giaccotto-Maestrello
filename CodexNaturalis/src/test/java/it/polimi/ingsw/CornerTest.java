package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class CornerTest {
    @Test
    public void testCornerConstructorValidInput() {

        Corner corner = new Corner(Symbol.ANIMAL, false);
        Assertions.assertEquals(Symbol.ANIMAL, corner.getSymbol());
        Assertions.assertFalse(corner.isHidden());
        Assertions.assertFalse(corner.isCovered());
        assertNull(corner.getNextCorner());
    }

    @Test
    public void testCornerConstructorHiddenWithoutSymbol() {
        Corner corner = new Corner(null,true);

        assertNull(corner.getSymbol());
        Assertions.assertTrue(corner.isHidden());
        Assertions.assertFalse(corner.isCovered());
        assertNull(corner.getNextCorner());
    }

    @Test
    public void testCornerConstructorHiddenWithSymbol() {
        Symbol symbol = Symbol.ANIMAL;
        boolean hidden = true;
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Corner(symbol, hidden));
    }


    @Test
    public void testIsCoveredDefaultFalse(){
        Corner corner= new Corner(Symbol.MANUSCRIPT, false);
        Assertions. assertFalse(corner.isCovered());
    }
    @Test
    public void testIsCoveredSetCovered(){
        Corner corner= new Corner(Symbol.MANUSCRIPT, false);
        corner.setCovered();
        Assertions. assertTrue(corner.isCovered());
    }





}
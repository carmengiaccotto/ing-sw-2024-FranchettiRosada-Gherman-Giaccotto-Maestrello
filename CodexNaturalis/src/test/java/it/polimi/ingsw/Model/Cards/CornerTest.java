package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void isHidden() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        Assertions.assertFalse(corner.isHidden());
        Corner corner1 = new Corner(null, true);
        Assertions.assertTrue(corner1.isHidden());
    }

    @Test
    void getNextCorner() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        Corner nextCorner = new Corner(Symbol.MANUSCRIPT, false);
        corner.setNextCorner(nextCorner);
        assertEquals(nextCorner, corner.getNextCorner());
    }

    @Test
    void getSymbol() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        assertEquals(Symbol.ANIMAL, corner.getSymbol());
    }
    @Test
    void getSymbolNull() {
        Corner corner = new Corner(null, true);
        assertNull(corner.getSymbol());
    }

    @Test
    void setCovered() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        assertFalse(corner.isCovered());
        corner.setCovered();
        assertTrue(corner.isCovered());
    }


    @Test
    void getParentCard() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        Corner[][] corners =  {{null, corner}, {null, null}};
        SideOfCard sideOfCard = new SideOfCard(null,corners);
        assertEquals(sideOfCard, corner.getParentCard());
    }


    @Test
    void getPosition() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        Corner[][] corners =  {{null, corner}, {null, null}};
        SideOfCard sideOfCard = new SideOfCard(null,corners);
        assertEquals(CornerPosition.TOPRIGHT, corner.getPosition());
    }

    @Test
    void setPosition() {
        Corner corner = new Corner(Symbol.ANIMAL, false);
        corner.setPosition(CornerPosition.BOTTOMLEFT);
        assertEquals(CornerPosition.BOTTOMLEFT, corner.getPosition());
    }
}
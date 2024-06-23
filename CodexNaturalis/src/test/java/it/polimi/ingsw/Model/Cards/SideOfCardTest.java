package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SideOfCardTest {
    private static HashMap<Symbol, Integer> frontSymbols;
    private static Corner[][] frontCorners;
    private static List<List<SideOfCard>> cardsOnArea;
    @BeforeAll
    static void setUp() {
        Corner corner1= new Corner(Symbol.FUNGI, false);
        Corner corner2=new Corner(null, false);
        Corner corner3= new Corner(Symbol.INSECT, false);
        Corner corner4=new Corner(null, true);
        frontCorners= new Corner[][]{{corner1, corner2}, {corner3, corner4}};
        frontSymbols= new HashMap<>();
        frontSymbols.put(Symbol.FUNGI, 1);
        frontSymbols.put(Symbol.INSECT, 1);
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        //setup playArea
        cardsOnArea=new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        cardsOnArea.add(row1);
        List<SideOfCard> row2= new ArrayList<>();
        cardsOnArea.add(row2);
    }

    @Test
    void testConstructor(){
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        assertNotNull(sideOfCard);
        assertEquals(frontSymbols, sideOfCard.getSymbols());
        assertEquals(frontCorners, sideOfCard.getCorners());
        assertFalse(sideOfCard.isInConfiguration());
        assertNull(sideOfCard.getPositionOnArea());
        assertNull(sideOfCard.getParentCard());

        // Check the positions of the corners
        assertEquals(CornerPosition.TOPLEFT, frontCorners[0][0].getPosition());
        assertEquals(CornerPosition.TOPRIGHT, frontCorners[0][1].getPosition());
        assertEquals(CornerPosition.BOTTOMLEFT, frontCorners[1][0].getPosition());
        assertEquals(CornerPosition.BOTTOMRIGHT, frontCorners[1][1].getPosition());

        // Check the parent card of the corners
        for (Corner[] cornerRow : frontCorners) {
            for (Corner corner : cornerRow) {
                if (corner != null) {
                    assertEquals(sideOfCard, corner.getParentCard());
                }
            }
        }
    }

    @Test
    void testConstructor_nullCornersMatrix() {
        SideOfCard sideOfCard = new SideOfCard(frontSymbols, null);
        assertNotNull(sideOfCard);
        assertEquals(frontSymbols, sideOfCard.getSymbols());
        assertNull(sideOfCard.getCorners());
        assertFalse(sideOfCard.isInConfiguration());
        assertNull(sideOfCard.getPositionOnArea());
        assertNull(sideOfCard.getParentCard());

    }

    @Test
    void testConstructor_nullCorners() {
        Corner[][] nullCorners= new Corner[][]{{null, null}, {null, null}};
        SideOfCard sideOfCard = new SideOfCard(frontSymbols, nullCorners);
        assertNotNull(sideOfCard);
        assertEquals(frontSymbols, sideOfCard.getSymbols());
        assertEquals(nullCorners, sideOfCard.getCorners());
        assertFalse(sideOfCard.isInConfiguration());
        assertNull(sideOfCard.getPositionOnArea());
        assertNull(sideOfCard.getParentCard());
        assertNull(nullCorners[0][0]);
        assertNull(nullCorners[0][1]);
        assertNull(nullCorners[1][0]);
        assertNull(nullCorners[1][1]);
    }


    @Test
    void getSymbolsTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        assertEquals(frontSymbols, sideOfCard.getSymbols());

    }
    @Test
    void setColorTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setColor(CardColors.GREEN);
        assertEquals(CardColors.GREEN, sideOfCard.getColor());
    }

    @Test
    void getColorTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setColor(CardColors.GREEN);
        assertEquals(CardColors.GREEN, sideOfCard.getColor());
    }


    @Test
    void setInConfigurationTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setInConfiguration(true);
        assertTrue(sideOfCard.isInConfiguration());
    }

    @Test
    void isInConfigurationTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setInConfiguration(true);
        assertTrue(sideOfCard.isInConfiguration());
        sideOfCard.setInConfiguration(false);
        assertFalse(sideOfCard.isInConfiguration());

    }

    @Test
    void setPositionOnAreaTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setPositionOnArea(new Pair<>(0,0));
        assertEquals(new Pair<>(0,0), sideOfCard.getPositionOnArea());
    }

    @Test
    void getCornersTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        assertEquals(frontCorners, sideOfCard.getCorners());
    }

    @Test
    void getCornerInPositionTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        assertEquals(frontCorners[0][0], sideOfCard.getCornerInPosition(CornerPosition.TOPLEFT));
        assertEquals(frontCorners[0][1], sideOfCard.getCornerInPosition(CornerPosition.TOPRIGHT));
        assertEquals(frontCorners[1][0], sideOfCard.getCornerInPosition(CornerPosition.BOTTOMLEFT));
        assertEquals(frontCorners[1][1], sideOfCard.getCornerInPosition(CornerPosition.BOTTOMRIGHT));
    }

    @Test
    void getPositionOnAreaTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        sideOfCard.setPositionOnArea(new Pair<>(0,0));
        assertEquals(new Pair<>(0,0), sideOfCard.getPositionOnArea());
    }


    @Test
    void setParentCardTest() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        PlayCard newCard = new PlayCard(2, sideOfCard, sideOfCard, CardColors.GREEN);
        sideOfCard.setParentCard(newCard);
        assertEquals(newCard, sideOfCard.getParentCard());
    }
    @Test
   void testGetCornerInPosition_ThrowsException() {
        SideOfCard sideOfCard= new SideOfCard( frontSymbols, frontCorners);
        assertThrows(IllegalArgumentException.class, () -> {
            sideOfCard.getCornerInPosition(null);
        });
    }



}
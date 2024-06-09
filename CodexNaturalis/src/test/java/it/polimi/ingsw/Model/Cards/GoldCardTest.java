package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    private static GoldCard goldCard;
    private static SideOfCard front;
    private static SideOfCard back;
    private static CardColors color;
    private static HashMap<Symbol, Integer> requirement;
    private static int point;

    @BeforeAll
    public static void setUp() {
        front = new SideOfCard(null, null);
        back = new SideOfCard(null, null);
        color = CardColors.GREEN;
        requirement = new HashMap<>();
        requirement.put(Symbol.FUNGI, 3);
        requirement.put(Symbol.ANIMAL, 2);

        point = 10;
       goldCard = new GoldCard(1, front, back, color, requirement, point);

    }

    @Test
    public void testGoldCardConstructor() {
        Assertions.assertNotNull(goldCard);
        assertEquals(1, goldCard.getIdCard());
        assertEquals(front, goldCard.getFront());
        assertEquals(back, goldCard.getBack());
        assertEquals(color, goldCard.getColor());
        assertEquals(requirement, goldCard.getRequirement(Side.FRONT));
        assertEquals(point, goldCard.getPoints(Side.FRONT));
        assertEquals(0, goldCard.getPoints(Side.BACK));
    }

    @Test
    public void testCheckRequirement_FrontSide_SufficientSymbols() {
        HashMap<Symbol, Integer>playAreaSymbols=new HashMap<>();
        playAreaSymbols.put(Symbol.FUNGI, 5);
        playAreaSymbols.put(Symbol.ANIMAL, 3);
        playAreaSymbols.put(Symbol.PLANT, 2);
        Side chosenSide = Side.FRONT;
        assertTrue(goldCard.checkRequirement(playAreaSymbols, chosenSide));
    }
    @Test
    public void testCheckRequirement_FrontSide_InsufficientSymbols() {
        HashMap<Symbol, Integer>playAreaSymbols=new HashMap<>();
        playAreaSymbols.put(Symbol.FUNGI, 5);
        playAreaSymbols.put(Symbol.ANIMAL, 1);
        Side chosenSide = Side.FRONT;
        assertFalse(goldCard.checkRequirement(playAreaSymbols, chosenSide));
    }

    @Test
    public void testCheckRequirement_BackSide() {
        HashMap<Symbol, Integer>playAreaSymbols=new HashMap<>();
        playAreaSymbols.put(Symbol.FUNGI, 5);
        playAreaSymbols.put(Symbol.PLANT, 2);
        Side chosenSide = Side.BACK;
        assertTrue(goldCard.checkRequirement(playAreaSymbols, chosenSide));
    }

    @Test
    void testCheckRequirement_FrontSide_MissingSymbol() {
        HashMap<Symbol, Integer>playAreaSymbols=new HashMap<>();
        playAreaSymbols.put(Symbol.FUNGI, 5);
        // Do not put Symbol.ANIMAL in the map
        playAreaSymbols.put(Symbol.PLANT, 2);
        Side chosenSide = Side.FRONT;
        assertFalse(goldCard.checkRequirement(playAreaSymbols, chosenSide));
    }

    @Test
    void testIncreasePoints() {
        goldCard.increasePoints(5);
        assertEquals(10, goldCard.getPoints(Side.FRONT));
    }

    @Test
    void testGetRequirementsBackSide() {
        assertNull(goldCard.getRequirement(Side.BACK));
    }




}
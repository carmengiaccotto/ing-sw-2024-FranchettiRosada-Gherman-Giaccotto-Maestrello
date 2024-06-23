package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayCardTest {

    private static PlayCard card;
    private static SideOfCard front;
    private static SideOfCard back;

    @BeforeAll
    static void cardCreation(){
        Corner corner1= new Corner(Symbol.FUNGI, false);
        Corner corner2=new Corner(null, false);
        Corner corner3= new Corner(Symbol.INSECT, false);
        Corner corner4=new Corner(null, true);
        Corner[][] frontCorners={{corner1, corner2},{corner3, corner4}};
        Corner[][] backCorners={{corner2, corner2},{corner2, corner2}};
        HashMap<Symbol, Integer> frontSymbols= new HashMap<>();
        frontSymbols.put(Symbol.FUNGI, 1);
        frontSymbols.put(Symbol.INSECT, 1);
        front= new SideOfCard( frontSymbols, frontCorners);
        back= new SideOfCard( null, backCorners);
        card= new PlayCard(1, front, back, CardColors.BLUE);

    }

    @Test
    void chooseSide() {
        assertEquals(front, card.chooseSide(Side.FRONT));
        assertEquals(back, card.chooseSide(Side.BACK));
    }

    @Test
    void getColor() {
        assertEquals(CardColors.BLUE, card.getColor());

    }

    @Test
    void getFront() {
        assertEquals(front, card.getFront());
    }

    @Test
    void getBack() {
        assertEquals(back, card.getBack());
    }
    @Test
    void testCardNullSides(){
        PlayCard card= new PlayCard(1, null, null, CardColors.BLUE);
        assertNull(card.getFront());
        assertNull(card.getBack());
    }
}
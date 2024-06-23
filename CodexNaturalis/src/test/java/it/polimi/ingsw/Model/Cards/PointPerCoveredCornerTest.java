package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PointPerCoveredCornerTest {
    private static PointPerCoveredCorner card;
    private static SideOfCard front;
    private static SideOfCard back;

    @BeforeAll
    static void setUp(){
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
        card= new PointPerCoveredCorner(1, front, back, CardColors.BLUE, frontSymbols, 1);
        Corner coveredCorner1= new Corner(Symbol.FUNGI, false);
        Corner coveredCorner2=new Corner(null, false);
        corner1.setNextCorner(coveredCorner1);
        corner3.setNextCorner(coveredCorner2);

    }

    @Test
    void findCoveredCorners() {
        assertEquals(2, card.findCoveredCorners());
    }

    @Test
    void increasePoints() {
        assertEquals(2, card.increasePoints(1));

    }
}
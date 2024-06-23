package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class PointPerVisibleSymbolTest {
    private static PointPerVisibleSymbol card;
    private static PlayArea playArea;
    private static SideOfCard front;
    private static SideOfCard back;

    @BeforeAll
    static void setUp() {
        //PlayArea setUp. Creating just the map, because we check on that, and we do not need the cards
        playArea = new PlayArea();
        playArea.getSymbols().put(Symbol.ANIMAL, 3);
        playArea.getSymbols().put(Symbol.MANUSCRIPT, 3);
        playArea.getSymbols().put(Symbol.FUNGI, 2);

        //Creating the card
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
       card= new PointPerVisibleSymbol(1, front, back, CardColors.BLUE, frontSymbols, 1, Symbol.ANIMAL);
    }

    @Test
    void increasePoints() {
        Assertions.assertEquals(3, card.increasePoints(playArea));
    }

    @Test
    void getGoldGoal() {
        Assertions.assertEquals(Symbol.ANIMAL, card.getGoldGoal());
    }
}
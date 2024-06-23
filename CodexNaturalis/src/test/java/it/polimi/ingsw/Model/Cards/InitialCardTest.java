package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

class InitialCardTest {

    @Test
    void testInitialCard() {
        Corner corner1 = new Corner(Symbol.MANUSCRIPT, false);
        Corner corner2 = new Corner(null, false);
        Corner corner3 = new Corner(Symbol.ANIMAL, false);
        Corner corner4 = new Corner(null, true);
        Corner[][] FrontCorners= {{corner1, corner2}, {corner3, corner4}};
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        symbols.put(Symbol.ANIMAL, 1);
        symbols.put(Symbol.MANUSCRIPT, 1);
        SideOfCard sideOfCardFront = new SideOfCard(symbols, FrontCorners);
        Corner[][] backCorners = {{corner2, corner2}, {corner2, corner2}};
        SideOfCard sideOfCardBack = new SideOfCard(null, backCorners);
        InitialCard initialCard = new InitialCard(1, sideOfCardFront, sideOfCardBack, null);
        Assertions.assertEquals(1, initialCard.getIdCard());
    }

}
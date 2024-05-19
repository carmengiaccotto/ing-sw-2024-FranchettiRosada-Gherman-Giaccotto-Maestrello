package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

class EdgePositionsTest {
    private PlayArea playArea = new PlayArea();
    @BeforeEach
    public void setUp() {
        Corner coner1= new Corner(null, false); //corner1 empty
        Corner coner2= new Corner(Symbol.ANIMAL, false); //corner2
        Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
        Corner corner4= new Corner(null, true); //corner4 hidden
        Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        symbols.put(Symbol.ANIMAL, 1);
        symbols.put(Symbol.INKWELL, 1);

        SideOfCard card = new SideOfCard(symbols, corners);

        // Act
        playArea.addInitialCardOnArea(card);
    }




    @Test
    void testExpandArea_RowZero() {
        EdgePositions.EdgeCases.RowZero.ExpandArea(playArea.getCardsOnArea());
        Assertions.assertEquals(4, playArea.getCardsOnArea().size());
    }

    @Test
    void testExpandArea_ColumnZero() {
        EdgePositions.EdgeCases.ColumnZero.ExpandArea(playArea.getCardsOnArea());
        Assertions.assertEquals(4, playArea.getCardsOnArea().get(0).size());
    }


    @Test
    void testExpandArea_RowMax() {

        EdgePositions.EdgeCases.RowMax.ExpandArea(playArea.getCardsOnArea());


        Assertions.assertEquals(4, playArea.getCardsOnArea().size());
        Assertions.assertEquals(3, playArea.getCardsOnArea().get(3).size());
    }

    @Test
    void testExpandArea_ColumnMax() {

        EdgePositions.EdgeCases.ColumnMax.ExpandArea(playArea.getCardsOnArea());
        for (List<SideOfCard> row : playArea.getCardsOnArea()) {
            Assertions.assertNull(row.get(2));
        }
        Assertions.assertEquals(4, playArea.getCardsOnArea().get(2).size());
    }





    @Test
    void testIsEdgePosition_RowZero() {
        Assertions.assertTrue(EdgePositions.EdgeCases.RowZero.isEdgePosition(new Pair<>(0, 0), playArea.getCardsOnArea()));
    }

    @Test
    void testIsEdgePosition_ColumnZero() {
        Assertions.assertTrue(EdgePositions.EdgeCases.ColumnZero.isEdgePosition(new Pair<>(0, 0), playArea.getCardsOnArea()));
    }


    @Test
    void testIsEdgePosition_RowMax() {
        Assertions.assertTrue(EdgePositions.EdgeCases.RowMax.isEdgePosition(new Pair<>(2,2), playArea.getCardsOnArea()));
    }


    @Test
    void testIsEdgePosition_ColumnMax() {
        Assertions.assertTrue(EdgePositions.EdgeCases.ColumnMax.isEdgePosition(new Pair<>(2,2), playArea.getCardsOnArea()));

    }


}
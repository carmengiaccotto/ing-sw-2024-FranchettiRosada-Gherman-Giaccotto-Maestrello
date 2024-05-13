package it.polimi.ingsw.ModelTest.PlayGroundTests;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.PlayGround.EdgePositions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EdgePositionsTest {


    /**ExpandArea Tests*/


    @Test
    void testExpandArea_RowZero() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        EdgePositions.EdgeCases.RowZero.ExpandArea(cardsOnArea);
        Assertions.assertEquals(1, cardsOnArea.size());
    }

    @Test
    void testExpandArea_ColumnZero() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        EdgePositions.EdgeCases.ColumnZero.ExpandArea(cardsOnArea);
        Assertions.assertEquals(1, cardsOnArea.get(0).size());
    }


    @Test
    void testExpandArea_RowMax() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<SideOfCard> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Pair<Integer, Integer> positionOnArea= new Pair<>(i,j);
                SideOfCard testCard=new SideOfCard(null, new Corner[2][2]);
                testCard.setPositionOnArea(positionOnArea);
                row.add(testCard);
            }
            cardsOnArea.add(row);
        }

        EdgePositions.EdgeCases.RowMax.ExpandArea(cardsOnArea);


        Assertions.assertEquals(4, cardsOnArea.size());
        Assertions.assertEquals(3, cardsOnArea.get(3).size());
    }

    @Test
    void testExpandArea_ColumnMax() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<SideOfCard> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Pair<Integer, Integer> positionOnArea= new Pair<>(i,j);
                SideOfCard testCard=new SideOfCard(null, new Corner[2][2]);
                testCard.setPositionOnArea(positionOnArea);
                row.add(testCard);
            }
            cardsOnArea.add(row);
        }

        EdgePositions.EdgeCases.ColumnMax.ExpandArea(cardsOnArea);

        for (List<SideOfCard> row : cardsOnArea) {
            Assertions.assertNull(row.get(3));
        }
    }





    /**IsEdgePosition Tests*/

    @Test
    void testIsEdgePosition_RowZero() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        SideOfCard card = new SideOfCard(null, null);
        card.setPositionOnArea(new Pair<>(0, 0));
        Assertions.assertTrue(EdgePositions.EdgeCases.RowZero.isEdgePosition(card, cardsOnArea));
    }

    @Test
    void testIsEdgePosition_ColumnZero() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        SideOfCard card = new SideOfCard(null, new Corner[2][2]);
        card.setPositionOnArea(new Pair<>(0, 0));
        Assertions.assertTrue(EdgePositions.EdgeCases.ColumnZero.isEdgePosition(card, cardsOnArea));
    }


    @Test
    void testIsEdgePosition_RowMax() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<SideOfCard> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Pair<Integer, Integer> positionOnArea= new Pair<>(i,j);
                SideOfCard testCard=new SideOfCard(null, new Corner[2][2]);
                testCard.setPositionOnArea(positionOnArea);
                row.add(testCard);
            }
            cardsOnArea.add(row);
        }
        Pair<Integer, Integer> pos=new Pair<>((cardsOnArea.size() - 1), 1);
        SideOfCard card = new SideOfCard(null, new Corner[2][2]);
        card.setPositionOnArea(pos);

        Assertions.assertTrue(EdgePositions.EdgeCases.RowMax.isEdgePosition(card, cardsOnArea));

    }


    @Test
    void testIsEdgePosition_ColumnMax() {
        List<List<SideOfCard>> cardsOnArea = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<SideOfCard> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Pair<Integer, Integer> positionOnArea= new Pair<>(i,j);
                SideOfCard testCard=new SideOfCard(null, new Corner[2][2]);
                testCard.setPositionOnArea(positionOnArea);
                row.add(testCard);
            }
            cardsOnArea.add(row);
        }
        Pair<Integer, Integer> pos=new Pair<>(1, (cardsOnArea.getFirst().size() - 1));
        SideOfCard card = new SideOfCard(null, new Corner[2][2]);
        card.setPositionOnArea(pos);

        Assertions.assertTrue(EdgePositions.EdgeCases.ColumnMax.isEdgePosition(card, cardsOnArea));

    }







/**Class Constructor Test*/

    @Test
    void testConstructFullEdgeCases() {
        EdgePositions edgePositions = new EdgePositions();
        Assertions.assertNotNull(EdgePositions.cornersToCheck.get(EdgePositions.EdgeCases.RowZero));
        Assertions.assertNotNull(EdgePositions.cornersToCheck.get(EdgePositions.EdgeCases.RowMax));
        Assertions.assertNotNull(EdgePositions.cornersToCheck.get(EdgePositions.EdgeCases.ColumnZero));
        Assertions.assertNotNull(EdgePositions.cornersToCheck.get(EdgePositions.EdgeCases.ColumnMax));
    }



}
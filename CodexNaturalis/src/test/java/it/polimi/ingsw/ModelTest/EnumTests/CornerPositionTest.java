package it.polimi.ingsw.ModelTest.EnumTests;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CornerPositionTest {
    private static List<List<SideOfCard>> cardsOnArea;

    public static void setCardsOnArea(List<List<SideOfCard>> cardsOnArea) {
        CornerPositionTest.cardsOnArea = cardsOnArea;
    }

    private static Corner[][] generateCorners(int rows, int cols) {
        Corner[][] corners = new Corner[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                corners[i][j] = new Corner(Symbol.FUNGI, false);
            }
        }
        return corners;
    }


    @Test
    public void TestCoverCorner(){
        assertEquals(CornerPosition.BOTTOMRIGHT,CornerPosition.TOPLEFT.CoverCorners() );
        assertEquals(CornerPosition.TOPRIGHT,CornerPosition.BOTTOMLEFT.CoverCorners() );
        assertEquals(CornerPosition.TOPLEFT,CornerPosition.BOTTOMRIGHT.CoverCorners() );
        assertEquals(CornerPosition.BOTTOMLEFT,CornerPosition.TOPRIGHT.CoverCorners() );
    }

    @Test
    public void TestPositionNewCard(){
        SideOfCard testCard= new SideOfCard(null, null);
        testCard.setPositionOnArea(new Pair<>(1,3));
        assertEquals(new Pair<>(0,2), CornerPosition.TOPLEFT.PositionNewCard(testCard));
        assertEquals(new Pair<>(0,4), CornerPosition.TOPRIGHT.PositionNewCard(testCard));
        assertEquals(new Pair<>(2,2), CornerPosition.BOTTOMLEFT.PositionNewCard(testCard));
        assertEquals(new Pair<>(2,4), CornerPosition.BOTTOMRIGHT.PositionNewCard(testCard));

    }

    @BeforeAll
    public static void CreateTestPlayArea(){
        List<List<SideOfCard>> cardsOnArea= new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        SideOfCard card1= new SideOfCard(null, generateCorners(2,2));
        row1.add(card1);
        SideOfCard card2= new SideOfCard(null, generateCorners(2,2));
        row1.add(card2);
        SideOfCard card3= new SideOfCard(null, generateCorners(2,2));
        row1.add(card3);
        cardsOnArea.add(row1);
        List<SideOfCard> row2 = new ArrayList<>();
        SideOfCard card4= new SideOfCard(null, generateCorners(2,2));
        row2.add(card4);
        SideOfCard card5= new SideOfCard(null, generateCorners(2,2));
        row2.add(card5);
        SideOfCard card6= new SideOfCard(null, generateCorners(2,2));
        row2.add(card6);
        cardsOnArea.add(row2);
        List<SideOfCard> row3 = new ArrayList<>();
        SideOfCard card7= new SideOfCard(null, generateCorners(2,2));
        row3.add(card7);
        SideOfCard card8= new SideOfCard(null, generateCorners(2,2));
        row3.add(card8);
        SideOfCard card9= new SideOfCard(null, generateCorners(2,2));
        row3.add(card9);
        cardsOnArea.add(row3);
        card5.getCornerInPosition(CornerPosition.TOPLEFT).setNextCorner(card1.getCornerInPosition(CornerPosition.BOTTOMRIGHT));
        card5.getCornerInPosition(CornerPosition.TOPRIGHT).setNextCorner(card3.getCornerInPosition(CornerPosition.BOTTOMLEFT));
        card5.getCornerInPosition(CornerPosition.BOTTOMLEFT).setNextCorner(card7.getCornerInPosition(CornerPosition.TOPRIGHT));
        card5.getCornerInPosition(CornerPosition.BOTTOMRIGHT).setNextCorner(card9.getCornerInPosition(CornerPosition.TOPLEFT));
        setCardsOnArea(cardsOnArea);
    }

    @Test
    void testGetNeighbourCard() {
        PlayArea testPlayArea=new PlayArea();
        testPlayArea.setCardsOnArea(cardsOnArea);
        Assertions.assertEquals(cardsOnArea.get(2).get(2), CornerPosition.BOTTOMRIGHT.getNeighbourCard(cardsOnArea.get(1).get(1), testPlayArea));
        Assertions.assertEquals(cardsOnArea.get(2).get(0), CornerPosition.BOTTOMLEFT.getNeighbourCard(cardsOnArea.get(1).get(1), testPlayArea));
        Assertions.assertEquals(cardsOnArea.get(0).get(2), CornerPosition.TOPRIGHT.getNeighbourCard(cardsOnArea.get(1).get(1), testPlayArea));
        Assertions.assertEquals(cardsOnArea.get(0).get(0), CornerPosition.TOPLEFT.getNeighbourCard(cardsOnArea.get(1).get(1), testPlayArea));




    }

}
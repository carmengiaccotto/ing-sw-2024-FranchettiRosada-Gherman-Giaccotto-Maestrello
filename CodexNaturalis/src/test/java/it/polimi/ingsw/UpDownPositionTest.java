package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.UpDownPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayGround.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UpDownPositionTest {
    private static List<List<SideOfCard>> cardsOnArea;
    public static void setCardsOnArea(List<List<SideOfCard>> cardsOnArea) {
        UpDownPositionTest.cardsOnArea = cardsOnArea;
    }



    private static Corner[][] generateCorners() {
        Corner[][] corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners[i][j] = new Corner(Symbol.FUNGI, false);
            }
        }
        return corners;
    }
    @BeforeAll
    public static void CreateTestPlayArea(){
        List<List<SideOfCard>> cardsOnArea= new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        SideOfCard card1= new SideOfCard(null, generateCorners());
        card1.setPositionOnArea(new Pair<>(0, 0));
        row1.add(card1);
        cardsOnArea.add(row1);
        List<SideOfCard> row2 = new ArrayList<>();
        SideOfCard card2= new SideOfCard(null, generateCorners());
        card2.setPositionOnArea(new Pair<>(1, 0));
        row2.add(card2);
        cardsOnArea.add(row2);
        List<SideOfCard> row3 = new ArrayList<>();
        SideOfCard card3= new SideOfCard(null, generateCorners());
        card3.setPositionOnArea(new Pair<>(2, 0));
        row3.add(card3);
        cardsOnArea.add(row3);
        setCardsOnArea(cardsOnArea);
    }

    @Test
    public void TestGetNeighbourCard(){
        PlayArea testPlayArea=new PlayArea(cardsOnArea);

        Assertions.assertEquals(cardsOnArea.get(0).getFirst(), UpDownPosition.UP.getNeighbourCard(cardsOnArea.get(2).getFirst(), testPlayArea));
        Assertions.assertEquals(cardsOnArea.get(2).getFirst(), UpDownPosition.DOWN.getNeighbourCard(cardsOnArea.get(0).getFirst(), testPlayArea));


    }



}
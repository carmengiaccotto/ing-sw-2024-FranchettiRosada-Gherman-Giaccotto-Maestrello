package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DispositionObjectiveCardTest {
    private static List<List<SideOfCard>> cardsOnArea;

    @Test
    public void testConstructor() {
        int id = 1;
        ObjectivePoints points = ObjectivePoints.TWO;
        CardColors centralCardColor = CardColors.BLUE;
        Map<Position, CardColors> neighbors = new HashMap<>();
        neighbors.put(UpDownPosition.DOWN, CardColors.RED);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.GREEN);

        DispositionObjectiveCard card = new DispositionObjectiveCard(id, points, centralCardColor, neighbors);

        assertEquals(id, card.getIdCard());
        assertEquals(points, card.getPoints());
        assertEquals(centralCardColor, card.getCentralCardColor());
        assertEquals(neighbors, card.getNeighbors());
    }
    @Test
    public void testGetCentralCardColor() {
        CardColors centralCardColor = CardColors.BLUE;
        DispositionObjectiveCard card = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, null);

        assertEquals(centralCardColor, card.getCentralCardColor());
    }
    @Test
    public void testSetCentralCardColor() {
        DispositionObjectiveCard card = new DispositionObjectiveCard(1, ObjectivePoints.THREE, null, null);
        CardColors newColor = CardColors.RED;

        card.setCentralCardColor(newColor);

        assertEquals(newColor, card.getCentralCardColor());
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
        cardsOnArea=new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        SideOfCard card1= new SideOfCard(null, generateCorners());
        PlayCard fullcard1= new PlayCard(1, card1, card1, CardColors.GREEN);
        card1.setPositionOnArea(new Pair<>(0, 0));
        row1.add(card1);
        row1.add(null);
        SideOfCard card7= new SideOfCard(null, generateCorners());
        PlayCard fullcard7= new PlayCard(7, card7, card7, CardColors.RED);
        card7.setPositionOnArea(new Pair<>(0, 2));
        row1.add(card7);

        List<SideOfCard> row2 = new ArrayList<>();
        row2.add(null);
        SideOfCard card5= new SideOfCard(null, generateCorners());
        PlayCard fullcard5= new PlayCard(5, card5, card5, CardColors.RED);
        card5.setPositionOnArea(new Pair<>(1, 1));
        row2.add(card5);
        row2.add(null);
        card1.getCornerInPosition(CornerPosition.BOTTOMRIGHT).setNextCorner(card5.getCornerInPosition(CornerPosition.TOPLEFT));
        card5.getCornerInPosition(CornerPosition.TOPLEFT).setNextCorner(card1.getCornerInPosition(CornerPosition.BOTTOMRIGHT));
        card7.getCornerInPosition(CornerPosition.BOTTOMLEFT).setNextCorner(card5.getCornerInPosition(CornerPosition.TOPRIGHT));
        card5.getCornerInPosition(CornerPosition.TOPRIGHT).setNextCorner(card7.getCornerInPosition(CornerPosition.BOTTOMLEFT));



        List<SideOfCard> row3 = new ArrayList<>();
        SideOfCard card3= new SideOfCard(null, generateCorners());
        PlayCard fullcard3= new PlayCard(3, card3, card3, CardColors.RED);
        card3.setPositionOnArea(new Pair<>(2, 0));
        row3.add(card3);
        row3.add(null);
        SideOfCard card9= new SideOfCard(null, generateCorners());
        PlayCard fullcard9= new PlayCard(9, card9, card9, CardColors.BLUE);
        card9.setPositionOnArea(new Pair<>(2, 2));
        row3.add(card9);
        card3.getCornerInPosition(CornerPosition.TOPRIGHT).setNextCorner(card5.getCornerInPosition(CornerPosition.BOTTOMLEFT));
        card5.getCornerInPosition(CornerPosition.BOTTOMLEFT).setNextCorner(card3.getCornerInPosition(CornerPosition.TOPRIGHT));
        card9.getCornerInPosition(CornerPosition.TOPLEFT).setNextCorner(card5.getCornerInPosition(CornerPosition.BOTTOMRIGHT));
        card5.getCornerInPosition(CornerPosition.BOTTOMRIGHT).setNextCorner(card9.getCornerInPosition(CornerPosition.TOPLEFT));





        List<SideOfCard> row4 = new ArrayList<>();
        row4.add(null);
        SideOfCard card11= new SideOfCard(null, generateCorners());
        PlayCard fullcard11= new PlayCard(11, card11, card11, CardColors.BLUE);
        card11.setPositionOnArea(new Pair<>(3, 1));
        row4.add(card11);
        row4.add(null);
        card11.getCornerInPosition(CornerPosition.TOPLEFT).setNextCorner(card3.getCornerInPosition(CornerPosition.BOTTOMRIGHT));
        card3.getCornerInPosition(CornerPosition.BOTTOMRIGHT).setNextCorner(card11.getCornerInPosition(CornerPosition.TOPLEFT));
        card11.getCornerInPosition(CornerPosition.TOPRIGHT).setNextCorner(card9.getCornerInPosition(CornerPosition.BOTTOMLEFT));
        card9.getCornerInPosition(CornerPosition.BOTTOMLEFT).setNextCorner(card11.getCornerInPosition(CornerPosition.TOPRIGHT));







        cardsOnArea.add(row1);
        cardsOnArea.add(row2);
        cardsOnArea.add(row3);

    }

    @Test
    public void FoundDispositionCornerPosition(){
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.RED;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMLEFT, CardColors.RED);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.RED);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(1, objectiveCard.CheckGoals(playArea));

    }
    @Test
    public void FoundDispositionUpDownPosition(){
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.BLUE;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMLEFT, CardColors.BLUE);
        neighbors.put(UpDownPosition.UP, CardColors.RED);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(1, objectiveCard.CheckGoals(playArea));

    }

    @Test
    public void NoDispositionFound(){
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.BLUE;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMLEFT, CardColors.BLUE);
        neighbors.put(UpDownPosition.UP, CardColors.PURPLE);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(0, objectiveCard.CheckGoals(playArea));

    }

    @Test
    public void FoundTwoDispositionButOneCardInCommonSoOneDisposition(){
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.RED;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMRIGHT, CardColors.BLUE);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.RED);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(1, objectiveCard.CheckGoals(playArea));

    }

    @Test
    public void ActuallyFoundTwoDispositions(){
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.RED;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMRIGHT, CardColors.BLUE);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(2, objectiveCard.CheckGoals(playArea));

    }
    @Test
    public void testGetNeighbors() {
        Map<Position, CardColors> neighbors = new HashMap<>();
        neighbors.put(UpDownPosition.DOWN, CardColors.RED);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.GREEN);

        DispositionObjectiveCard card = new DispositionObjectiveCard(1, ObjectivePoints.TWO, CardColors.BLUE, neighbors);

        assertEquals(neighbors, card.getNeighbors());
    }

    @Test
    public void testCheckGoalsWithNullNeighborColor() {
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.RED;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMRIGHT, null); // Set a neighbor color to null
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        assertEquals(0, objectiveCard.CheckGoals(playArea)); // Expect no valid configurations because of the null neighbor color
    }

    @Test
    public void testCheckGoalsWithCardInConfiguration() {
        PlayArea playArea = new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);
        CardColors centralCardColor= CardColors.RED;
        HashMap<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMRIGHT, CardColors.BLUE);
        DispositionObjectiveCard objectiveCard = new DispositionObjectiveCard(1, ObjectivePoints.TWO, centralCardColor, neighbors);
        playArea.getCardInPosition(0, 0).setInConfiguration(true);
        assertEquals(2, objectiveCard.CheckGoals(playArea));
    }


}
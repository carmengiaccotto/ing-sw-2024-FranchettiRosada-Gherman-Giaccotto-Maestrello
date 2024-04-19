package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    public void testPlayerConstructor() {
        String expectedNickname = "TestPlayer";
        PawnColor expectedPawnColor = PawnColor.RED;
        Player player = new Player(expectedNickname, expectedPawnColor, 0, 0);
        assertNotNull(player);
        assertEquals(expectedNickname, player.getNickname());
        assertEquals(PawnColor.RED, player.getPawnColor());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getCardsInHand().size());
        assertEquals(0,
                player.getRound());
        assertNotNull(player.getPlayArea());
    }


    @Test
    public void testGetPlayAreaSetPlayArea() {
        Player player = new Player("TestPlayer", PawnColor.RED, 0, 0);
        PlayArea expectedPlayArea = new PlayArea(null);
        player.setPlayArea(expectedPlayArea);
        PlayArea actualPlayArea = player.getPlayArea();
        assertNotNull(actualPlayArea);
        assertEquals(expectedPlayArea, actualPlayArea);
    }


    @Test
    public void testGetNickname() {
        String expectedNickname = "TestPlayer";
        Player player = new Player(expectedNickname, PawnColor.RED, 0, 0);
        String actualNickname = player.getNickname();
        assertEquals(expectedNickname, actualNickname);
    }

    @Test
    public void testSetNickname() {
        String nickname = "OldNickname";
        Player TestPlayer = new Player(nickname, PawnColor.YELLOW, 0, 0);
        String newNickName = "NewNickName";
        TestPlayer.setNickname(newNickName);
        assertEquals(newNickName, TestPlayer.getNickname());
    }







    @Test
    public void testGetPawnColor() {
        String testNickname = "Nickname";
        PawnColor testColor = PawnColor.RED;
        Player testPlayer = new Player(testNickname, testColor, 0, 0);
        assertEquals(testColor, testPlayer.getPawnColor());
    }

    @Test
    public void testSetPawnColor() {
        Player testPlayer = new Player(null, null, 0, 0);
        PawnColor testColor = PawnColor.RED;
        testPlayer.setPawnColor(testColor);
        assertEquals(testColor, testPlayer.getPawnColor());
    }

    @Test
    public void TestGetScore(){
        Player testPlayer=new Player("user1", PawnColor.RED, 2, 0);
        assertEquals(2,testPlayer.getScore());
    }


    @Test
    public void TestSetScore(){
        Player testPlayer=new Player("user1", PawnColor.RED, 0, 0);
        int scoreToSet=5;
        testPlayer.setScore(scoreToSet);
        assertEquals(scoreToSet,testPlayer.getScore());
    }

    @Test
    public void TestIncreaseScoreFirst(){
        Player testPlayer=new Player("user1", PawnColor.RED, 0, 0);
        int points=2;
        testPlayer.increaseScore(points);
        assertEquals(2,testPlayer.getScore());
    }

    @Test
    public void TestIncreaseScoreSecond(){
        Player testPlayer=new Player("user1", PawnColor.RED, 5, 0);
        int points=7;
        testPlayer.increaseScore(points);
        assertEquals(12,testPlayer.getScore());
    }

    @Test

    public void TestGetRound(){
        Player testPlayer=new Player("user1", PawnColor.RED, 2, 4);
        assertEquals(4,testPlayer.getRound());

    }
    @Test
    public void TestIncreaseRound(){
        Player testPlayer=new Player("user1", PawnColor.RED, 0, 4);
        testPlayer.IncreaseRound();
        assertEquals(5,testPlayer.getRound());
    }


    @Test
    public void testGetCardsInHandAddCardsToHand() {
        Player testPlayer = new Player("user1", PawnColor.RED, 0, 4);
        PlayCard card1 = new PlayCard(3, new SideOfCard(null, CardColors.BLUE, null),
                new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE);
        PlayCard card2 = new PlayCard(4, new SideOfCard(null, CardColors.PURPLE, null),
                new SideOfCard(null, CardColors.PURPLE, new Corner[2][2]), CardColors.PURPLE);
        testPlayer.addCardToHand(card1);
        testPlayer.addCardToHand(card2);
        ArrayList<PlayCard> cardsInHand = testPlayer.getCardsInHand();
        assertEquals(2, cardsInHand.size());
        assertTrue(cardsInHand.contains(card1));
        assertTrue(cardsInHand.contains(card2));

    }

    @Test
    public void testChooseCardToPlay_RemovesCardFromHand() {
        Player testPlayer = new Player("user1", PawnColor.RED, 0, 4);
        PlayCard card = new PlayCard(3, new SideOfCard(null, CardColors.BLUE, null),
                new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE);
        testPlayer.addCardToHand(card);
        int initialHandSize = testPlayer.getCardsInHand().size();
        testPlayer.ChooseCardToPlay(card, Side.FRONT);

        assertEquals(initialHandSize - 1, testPlayer.getCardsInHand().size());
        Assertions.assertFalse(testPlayer.getCardsInHand().contains(card));
    }

    @Test
    public void testDrawCardFrom_DrawsCardFromDeck() {
        Player testPlayer = new Player("user1", PawnColor.RED, 0, 4);
        ArrayList<PlayCard>testDeck = new ArrayList<>();
        testDeck.add(new PlayCard(3, new SideOfCard(null, CardColors.BLUE, null),
                new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE));
        int initialDeckSize = testDeck.size();
        PlayCard drawnCard = testDeck.getFirst();

        testPlayer.DrawCardFrom(testDeck, drawnCard);

        assertEquals(initialDeckSize - 1, testDeck.size());
        assertTrue(testPlayer.getCardsInHand().contains(drawnCard));
    }


    @Test
    public void testDrawCardFrom_ThrowsIllegalStateException() {
        Player testPlayer = new Player("user1", PawnColor.RED, 0, 4);
        ArrayList<PlayCard> emptyDeck = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> {
            testPlayer.DrawCardFrom(emptyDeck, new PlayCard(3, new SideOfCard(null, CardColors.BLUE, null),
                    new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE));;
        });
    }

    @Test
    public void testDrawCardFrom_ThrowsNoSuchElementException() {
        // Setup
        Player testPlayer = new Player("user1", PawnColor.RED, 0, 4);
        ArrayList<PlayCard> testDeck = new ArrayList<>();
        testDeck.add(new PlayCard(7, new SideOfCard(null, CardColors.BLUE, null),
                new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE));
        PlayCard Card= new PlayCard(3, new SideOfCard(null, CardColors.BLUE, null),
                new SideOfCard(null, CardColors.BLUE, new Corner[2][2]), CardColors.BLUE);
        assertThrows(NoSuchElementException.class, () -> {
            testPlayer.DrawCardFrom(testDeck, Card);
        });
    }






}


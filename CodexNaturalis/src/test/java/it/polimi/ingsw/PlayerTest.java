package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {
    @Test
    public void testPlayerConstructor() {
        // Arrange
        String expectedNickname = "TestPlayer";
        PawnColor expectedPawnColor = PawnColor.RED;
        // Act
        Player player = new Player(expectedNickname, expectedPawnColor, 0, 0);

        // Assert
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
    public void testGetPlayArea() {
        // Arrange
        Player player = new Player("TestPlayer", PawnColor.RED, 0, 0);
        PlayArea expectedPlayArea = new PlayArea(null);

        // Act
        PlayArea actualPlayArea = player.getPlayArea();

        // Assert
        assertNotNull(actualPlayArea);
        assertEquals(expectedPlayArea, actualPlayArea);
    }


    @Test
    public void testGetNickname() {
        // Arrange
        String expectedNickname = "TestPlayer";
        Player player = new Player(expectedNickname, PawnColor.RED, 0, 0);

        // Act
        String actualNickname = player.getNickname();

        // Assert
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

















}


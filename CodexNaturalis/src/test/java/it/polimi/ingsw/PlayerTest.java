package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Colors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testPlayerConstructor() {
        // Arrange
        String expectedNickname = "TestPlayer";
        Colors expectedPawnColor = Colors.RED;
        // Act
        Player player = new Player(expectedNickname, expectedPawnColor, 0, 0);

        // Assert
        assertNotNull(player);
        assertEquals(expectedNickname, player.getNickname());
        assertEquals(Colors.RED, player.getPawnColor());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getCardsInHand().size());
        assertEquals(0,
                player.getRound());
        assertNotNull(player.getPlayArea());
    }


    @Test
    public void testGetPlayArea() {
        // Arrange
        Player player = new Player("TestPlayer", Colors.RED, 0, 0);
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
        Player player = new Player(expectedNickname, Colors.RED, 0, 0);

        // Act
        String actualNickname = player.getNickname();

        // Assert
        assertEquals(expectedNickname, actualNickname);
    }

    @Test
    public void testSetNickname() {
        String nickname = "OldNickname";
        Player TestPlayer = new Player(nickname, Colors.PURPLE, 0, 0);

        String newNickName = "NewNickName";
        TestPlayer.setNickname(newNickName);
        assertEquals(newNickName, TestPlayer.getNickname());
    }


    @Test
    public void testChooseNickNameAvailable() {
        String chosenNickname = "TestNickName";
        Player testPlayer = new Player(null, null, 0, 0);

        assertEquals(chosenNickname, testPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameUnavailable() {
        Player TestPlayer = new Player(null, null, 0, 0);
        TestPlayer.chooseNickName("usedNickName");
        assertNull(TestPlayer.chooseNickName("usedNickName"));

    }

    @Test
    public void testChooseNickNameFirstPlayer() {
        Player TestPlayer = new Player(null, null, 0, 0);
        assertEquals("chosenNickName", TestPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameWithOtherPlayers() {
        PlayGround.getListOfPlayers().add(new Player("Nickname1", null, 0, 0));
        PlayGround.getListOfPlayers().add(new Player("Nickname2", null, 0, 0));
        PlayGround.getListOfPlayers().add(new Player("Nickname3", null, 0, 0));
        Player TestPlayer = new Player(null, null, 0, 0);
        String ChosenNickname = "chosenNickName";
        assertEquals(ChosenNickname, TestPlayer.getNickname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChooseNickNameNull() {
        Player player = new Player(null, null, 0, 0);
        player.chooseNickName(null);

    }


    @Test
    public void testGetPawnColor() {
        String testNickname = "Nickname";
        Colors testColor = Colors.RED;
        Player testPlayer = new Player(testNickname, testColor, 0, 0);
        assertEquals(testColor, testPlayer.getPawnColor());
    }

    @Test
    public void testSetPawnColor() {
        Player testPlayer = new Player(null, null, 0, 0);
        Colors testColor = Colors.RED;
        testPlayer.setPawnColor(testColor);
        assertEquals(testColor, testPlayer.getPawnColor());
    }




    @Test
    public void TestDisplayAvailablePawnColorsFirstPlayerChoosing() {
        Player testPlayer=new Player(null, null, 0, 0);
        ArrayList<Player> testPlayerList= PlayGround.getListOfPlayers();
        testPlayerList.clear();
        ArrayList<Colors> expected= new ArrayList<>();
        Collections.addAll(expected, Colors.values());
        assertEquals(expected,testPlayer.DisplayAvailablePawnColors() );

    }

    @Test
    public void TestDisplayAvailablePawnColorsMoreThanOneColorAvailable(){
        Player testPlayer=new Player(null, null, 0, 0);
        Player player1= new Player ("user1", Colors.RED, 0, 0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN, 0, 0);
        PlayGround.getListOfPlayers().add(player2);
        ArrayList<Colors> expected= new ArrayList<>();
        expected.add(Colors.PURPLE);
        expected.add(Colors.BLUE);
        assertEquals(expected, testPlayer.DisplayAvailablePawnColors());

    }
    @Test
    public void TestDisplayAvailablePawnColorsOneColorAvailable(){
        Player testPlayer=new Player(null, null,0, 0);
        Player player1= new Player ("user1", Colors.RED,0,0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN,0,0);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", Colors.BLUE,0,0);
        PlayGround.getListOfPlayers().add(player3);
        assertEquals(Colors.PURPLE, testPlayer.DisplayAvailablePawnColors());

    }
    @Test
    public void TestDisplayAvailablePawnColorsNoColorsAvailable(){
        Player testPlayer=new Player(null, null,0,0);
        Player player1= new Player ("user1", Colors.RED,0,0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN,0,0);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", Colors.BLUE,0,0);
        PlayGround.getListOfPlayers().add(player3);
        Player player4= new Player ("user3", Colors.PURPLE,0,0);
        PlayGround.getListOfPlayers().add(player4);
        assertNull(testPlayer.DisplayAvailablePawnColors());

    }

    @Test

    public void TestDisplayAvailablePawnColorsNoPlayerMadeAChoice(){
        Player testPlayer=new Player(null, null,0,0);
        Player player1= new Player ("user1", null,0,0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", null,0,0);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", null,0,0);
        PlayGround.getListOfPlayers().add(player3);
        ArrayList<Colors> expected= new ArrayList<>();
        Collections.addAll(expected, Colors.values());
        assertEquals(expected,testPlayer.DisplayAvailablePawnColors());

    }

    @Test
    public void TestChoosePawnColorValidColor(){
        Player player1= new Player ("user1", Colors.PURPLE,0,0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.RED,0,0);
        PlayGround.getListOfPlayers().add(player2);
        Colors testChosenColor=Colors.BLUE;
        Colors expected=Colors.BLUE;
        Player testPlayer= new Player("testPlayer", null,0,0);
        assertEquals(expected, testPlayer.ChoosePawnColor(testChosenColor));
        assertEquals(expected,
                testPlayer.getPawnColor());

    }

    @Test
    public void TestChoosePawnColorNotAvailableColor(){
        Player player1= new Player ("user1", Colors.PURPLE,0,0);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.RED,0,0);
        PlayGround.getListOfPlayers().add(player2);
        Player testPlayer= new Player("testPlayer", null,0,0);
        assertThrows(RuntimeException.class, () -> testPlayer.ChoosePawnColor(Colors.RED));

    }

    @Test
    public void TestChoosePawnColorNoChosenColor(){
        PlayGround.getListOfPlayers().clear();
        Player testPlayer= new Player ("user1", null,0,0);
        assertThrows(RuntimeException.class, () -> testPlayer.ChoosePawnColor(null));
    }

    @Test
    public void TestGetScore(){
        Player testPlayer=new Player("user1", Colors.RED, 2, 0);
        assertEquals(2,testPlayer.getScore());
    }


    @Test
    public void TestSetScore(){
        Player testPlayer=new Player("user1", Colors.RED, 0, 0);
        int scoreToSet=5;
        testPlayer.setScore(scoreToSet);
        assertEquals(scoreToSet,testPlayer.getScore());
    }

    @Test
    public void TestIncreaseScoreFirst(){
        Player testPlayer=new Player("user1", Colors.RED, 0, 0);
        int points=2;
        testPlayer.increaseScore(points);
        assertEquals(2,testPlayer.getScore());
    }

    @Test
    public void TestIncreaseScoreSecond(){
        Player testPlayer=new Player("user1", Colors.RED, 5, 0);
        int points=7;
        testPlayer.increaseScore(points);
        assertEquals(12,testPlayer.getScore());
    }

    @Test

    public void TestGetRound(){
        Player testPlayer=new Player("user1", Colors.RED, 2, 4);
        assertEquals(4,testPlayer.getRound());

    }
    @Test
    public void TestIncreaseRound(){
        Player testPlayer=new Player("user1", Colors.RED, 0, 4);
        testPlayer.IncreaseRound();
        assertEquals(5,testPlayer.getRound());
    }

















}


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
        Player player = new Player(expectedNickname, expectedPawnColor);

        // Assert
        assertNotNull(player);
        assertEquals(expectedNickname, player.getNickname());
        assertEquals(expectedPawnColor, player.getPawnColor());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getCardsInHand().size());
        assertEquals(0, player.getRound());
        assertNotNull(player.getPlayArea());
    }


    @Test
    public void testGetPlayArea() {
        // Arrange
        Player player = new Player("TestPlayer", Colors.RED);
        PlayArea expectedPlayArea = new PlayArea();

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
        Player player = new Player(expectedNickname, Colors.RED);

        // Act
        String actualNickname = player.getNickname();

        // Assert
        assertEquals(expectedNickname, actualNickname); // Verifica che il nickname restituito sia uguale a quello di aspettativa
    }

    @Test
    public void testSetNickname() {
        String nickname = "OldNickname";
        Player TestPlayer = new Player(nickname, Colors.PURPLE);

        String newNickName = "NewNickName";
        TestPlayer.setNickname(newNickName);
        assertEquals(newNickName, TestPlayer.getNickname());
    }


    @Test
    public void testChooseNickNameAvailable() {
        String chosenNickname = "TestNickName";
        Player testPlayer = new Player(null, null);

        assertEquals(chosenNickname, testPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameUnavailable() {
        Player TestPlayer = new Player(null, null);
        TestPlayer.chooseNickName("usedNickName");
        assertNull(TestPlayer.chooseNickName("usedNickName"));

    }

    @Test
    public void testChooseNickNameFirstPlayer() {
        Player TestPlayer = new Player(null, null);
        assertEquals("chosenNickName", TestPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameWithOtherPlayers() {
        ArrayList<Player> otherPlayers = new ArrayList<>();
        otherPlayers.add(new Player("Nickname1", null));
        otherPlayers.add(new Player("Nickname2", null));
        otherPlayers.add(new Player("Nickname3", null));
        Player TestPlayer = new Player(null, null);
        String ChosenNickname = "chosenNickName";
        assertEquals("chosenNickName", TestPlayer.getNickname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChooseNickNameNull() {
        Player player = new Player(null, null);
        player.chooseNickName(null);

    }


    @Test
    public void testGetPawnColor() {
        String testNickname = "Nickname";
        Colors testColor = Colors.RED;
        Player testPlayer = new Player(testNickname, testColor);
        assertEquals(testColor, testPlayer.getPawnColor());
    }

    @Test
    public void testSetPawnColor() {
        Player testPlayer = new Player(null, null);
        Colors testColor = Colors.RED;
        testPlayer.setPawnColor(testColor);
        assertEquals(testColor, testPlayer.getPawnColor());
    }




    @Test
    public void TestDisplayAvailablePawnColorsFirstPlayerChoosing() {
        Player testPlayer=new Player(null, null);
        ArrayList<Player> testPlayerList= PlayGround.getListOfPlayers();
        testPlayerList.clear();
        ArrayList<Colors> expected= new ArrayList<Colors>();
        Collections.addAll(expected, Colors.values());
        assertEquals(expected,testPlayer.DisplayAvailablePawnColors() );

    }

    @Test
    public void TestDisplayAvailablePawnColorsMoreThanOneColorAvailable(){
        Player testPlayer=new Player(null, null);
        Player player1= new Player ("user1", Colors.RED);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN);
        PlayGround.getListOfPlayers().add(player2);
        ArrayList<Colors> expected= new ArrayList<Colors>();
        expected.add(Colors.PURPLE);
        expected.add(Colors.BLUE);
        assertEquals(expected, testPlayer.DisplayAvailablePawnColors());

    }
    @Test
    public void TestDisplayAvailablePawnColorsOneColorAvailable(){
        Player testPlayer=new Player(null, null);
        Player player1= new Player ("user1", Colors.RED);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", Colors.BLUE);
        PlayGround.getListOfPlayers().add(player3);
        assertEquals(Colors.PURPLE, testPlayer.DisplayAvailablePawnColors());

    }
    @Test
    public void TestDisplayAvailablePawnColorsNoColorsAvailable(){
        Player testPlayer=new Player(null, null);
        Player player1= new Player ("user1", Colors.RED);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", Colors.GREEN);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", Colors.BLUE);
        PlayGround.getListOfPlayers().add(player3);
        Player player4= new Player ("user3", Colors.PURPLE);
        PlayGround.getListOfPlayers().add(player4);
        assertNull(testPlayer.DisplayAvailablePawnColors());

    }

    @Test

    public void TestDisplayAvailablePawnColorsNoPlayerMadeAChoice(){
        Player testPlayer=new Player(null, null);
        Player player1= new Player ("user1", null);
        PlayGround.getListOfPlayers().add(player1);
        Player player2= new Player ("user2", null);
        PlayGround.getListOfPlayers().add(player2);
        Player player3= new Player ("user3", null);
        PlayGround.getListOfPlayers().add(player3);
        ArrayList<Colors> expected= new ArrayList<Colors>();
        Collections.addAll(expected, Colors.values());
        assertEquals(expected,testPlayer.DisplayAvailablePawnColors());

    }










}


package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Colors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.util.ArrayList;

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
    public void testSetNickname(){
        String nickname= "OldNickname";
        Player TestPlayer= new Player(nickname, Colors.PURPLE);

        String newNickName= "NewNickName";
        TestPlayer.setNickname(newNickName);
        assertEquals(newNickName, TestPlayer.getNickname());
    }


    @Test
    public void testChooseNickNameAvailable() {
        String chosenNickname= "TestNickName";
        Player testPlayer=new Player(null, null);

        assertEquals(chosenNickname, testPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameUnavailable(){
        Player TestPlayer=new Player(null,null);
        TestPlayer.chooseNickName("usedNickName");
        assertNull(TestPlayer.chooseNickName("usedNickName"));

    }

    @Test
    public void testChooseNickNameFirstPlayer(){
        Player TestPlayer=new Player(null,null);
        assertEquals("chosenNickName", TestPlayer.chooseNickName("chosenNickName"));
    }

    @Test
    public void testChooseNickNameWithOtherPlayers(){
        ArrayList<Player> otherPlayers = new ArrayList<>();
        otherPlayers.add(new Player ("Nickname1", null));
        otherPlayers.add(new Player ("Nickname2", null));
        otherPlayers.add(new Player ("Nickname3", null));
        Player TestPlayer=new Player(null, null);
        String ChosenNickname="chosenNickName";
        assertEquals("chosenNickName", TestPlayer.getNickname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChooseNickNameNull(){
        Player player = new Player(null, null);
        player.chooseNickName(null);

    }

}


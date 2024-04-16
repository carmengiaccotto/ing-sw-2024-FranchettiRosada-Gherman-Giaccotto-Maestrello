package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.UpDownPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpDownPositionTest {
    @Test
    void testPositionToCheck_UP() {
        SideOfCard currentCard = new SideOfCard(null, new Pair<>(2, 2), null);

        Pair<Integer, Integer> positionToCheck = UpDownPosition.UP.PositionToCheck(currentCard);

        assertEquals(1, positionToCheck.getFirst());
        assertEquals(2, positionToCheck.getSecond());
    }

    @Test
    void testPositionToCheck_UP_ZEROROW() {
        SideOfCard currentCard = new SideOfCard(null, new Pair<>(0, 2), null);

        Pair<Integer, Integer> positionToCheck = UpDownPosition.UP.PositionToCheck(currentCard);

        assertEquals(-1, positionToCheck.getFirst());
        assertEquals(2, positionToCheck.getSecond());
    }

    @Test
    void testPositionToCheck_DOWN() {
        SideOfCard currentCard = new SideOfCard(null, new Pair<>(2, 2), null);

        Pair<Integer, Integer> positionToCheck = UpDownPosition.DOWN.PositionToCheck(currentCard);

        assertEquals(3, positionToCheck.getFirst());
        assertEquals(2, positionToCheck.getSecond());
    }

    @Test
    void testPositionToCheck_Default() {
        SideOfCard currentCard = new SideOfCard(null, new Pair<>(2, 2), null);

        Pair<Integer, Integer> positionToCheck = UpDownPosition.valueOf("DEFAULT").PositionToCheck(currentCard);

        assertEquals(2, positionToCheck.getFirst());
        assertEquals(2, positionToCheck.getSecond());
    }

}
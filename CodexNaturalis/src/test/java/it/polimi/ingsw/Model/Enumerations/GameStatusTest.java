package it.polimi.ingsw.Model.Enumerations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStatusTest {

    @Test
    void values() {
        GameStatus[] statuses = GameStatus.values();
        assertEquals(6, statuses.length);
        assertEquals(GameStatus.WAITING, statuses[0]);
        assertEquals(GameStatus.RUNNING, statuses[1]);
        assertEquals(GameStatus.SETUP, statuses[2]);
        assertEquals(GameStatus.INITIAL_CIRCLE, statuses[3]);
        assertEquals(GameStatus.LAST_CIRCLE, statuses[4]);
        assertEquals(GameStatus.ENDED, statuses[5]);
    }

    @Test
    void valueOf() {
        assertEquals(GameStatus.WAITING, GameStatus.valueOf("WAITING"));
        assertEquals(GameStatus.RUNNING, GameStatus.valueOf("RUNNING"));
        assertEquals(GameStatus.SETUP, GameStatus.valueOf("SETUP"));
        assertEquals(GameStatus.INITIAL_CIRCLE, GameStatus.valueOf("INITIAL_CIRCLE"));
        assertEquals(GameStatus.LAST_CIRCLE, GameStatus.valueOf("LAST_CIRCLE"));
        assertEquals(GameStatus.ENDED, GameStatus.valueOf("ENDED"));
    }
}
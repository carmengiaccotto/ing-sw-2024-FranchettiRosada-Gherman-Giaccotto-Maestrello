package it.polimi.ingsw.Model.Enumerations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void values() {
        Command[] commands = Command.values();
        assertEquals(2, commands.length);
        assertEquals(Command.MOVE, commands[0]);
        assertEquals(Command.CHAT, commands[1]);
    }

    @Test
    void valueOf() {
        assertEquals(Command.MOVE, Command.valueOf("MOVE"));
        assertEquals(Command.CHAT, Command.valueOf("CHAT"));
    }
}
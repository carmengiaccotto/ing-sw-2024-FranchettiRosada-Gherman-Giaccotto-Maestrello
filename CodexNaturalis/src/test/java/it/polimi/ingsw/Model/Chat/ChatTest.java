package it.polimi.ingsw.Model.Chat;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {


    @Test
    void testConstructor() {
        Chat chat = new Chat();
        assertNotNull(chat.getMessage());
        assertTrue(chat.getMessage().isEmpty());
    }

    @Test
    void testGetMessage() {
        Chat chat = new Chat();
        Message message = new Message("Hello");
        chat.addMessage(message);
        assertEquals(1, chat.getMessage().size());
        assertEquals(message, chat.getMessage().get(0));
    }

    @Test
    void testAddMessage() {
        Chat chat = new Chat();
        Message message = new Message("Hello");
        chat.addMessage(message);
        assertEquals(1, chat.getMessage().size());
        assertEquals(message, chat.getMessage().get(0));
    }

    @Test
    void testSetMessage() {
        Chat chat = new Chat();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello"));
        messages.add(new Message("World"));
        chat.setMessage(messages);
        assertEquals(2, chat.getMessage().size());
        assertEquals(messages, chat.getMessage());
    }

    @Test
    void testAddMessageWithLimit() {
        Chat chat = new Chat();
        for (int i = 0; i < 1001; i++) {
            chat.addMessage(new Message("Message " + i));
        }
        assertEquals(1000, chat.getMessage().size());
        assertEquals("Message 1", chat.getMessage().get(0).getText());
    }
}
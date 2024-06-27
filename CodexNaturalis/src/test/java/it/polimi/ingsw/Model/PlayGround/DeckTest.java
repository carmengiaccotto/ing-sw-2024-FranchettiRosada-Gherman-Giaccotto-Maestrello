package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.ResourceCard;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class
DeckTest {

    @Test
    void getCards() throws IOException {
        Deck deck = new Deck(ResourceCard.class);
        assertNotNull(deck.getCards());
    }


    @Test
    void getSize() throws IOException {
        Deck deck = new Deck(ResourceCard.class);
        assertEquals(40, deck.getSize());
    }

    @Test
    void testDrawCard() throws IOException {
        Deck deck = new Deck(ResourceCard.class);
        int size = deck.getSize();
        ResourceCard card= (ResourceCard) deck.drawCard();
        assertEquals(size - 1, deck.getSize());
        assertNotNull(card);

    }

    @Test
    void testDrawCardEmptyDeck() throws IOException {
        Deck deck = new Deck(ResourceCard.class);
        for (int i = 0; i < 40; i++) {
            deck.drawCard();
        }
        assertThrows(RuntimeException.class, deck::drawCard);
    }
}
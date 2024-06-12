package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.ResourceCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DeckTest {

    @Test
    void getCards() {
        Deck deck = new Deck(ResourceCard.class, "src/main/resources/");
        assertNotNull(deck.getCards());
    }


    @Test
    void getSize() {
        Deck deck = new Deck(ResourceCard.class, "src/main/resources/");
        assertEquals(40, deck.getSize());
    }

    @Test
    void testConstructorExceptionHandling() {
        assertThrows(RuntimeException.class, () -> {
            new Deck(ResourceCard.class, "invalid/file/path/");
        });
    }

    @Test
    void testDrawCard(){
        Deck deck = new Deck(ResourceCard.class, "src/main/resources/");
        int size = deck.getSize();
        ResourceCard card= (ResourceCard) deck.drawCard();
        assertEquals(size - 1, deck.getSize());
        assertNotNull(card);

    }

    @Test
    void testDrawCardEmptyDeck(){
        Deck deck = new Deck(ResourceCard.class, "src/main/resources/");
        for (int i = 0; i < 40; i++) {
            deck.drawCard();
        }
        assertThrows(RuntimeException.class, deck::drawCard);
    }
}
package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Card;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void testConstructorWithPositiveId() {
        int idCard = 5;
        Card card = new Card(idCard);
        Assertions.assertEquals(idCard, card.getIdCard());
    }

    @Test
    void testConstructorWithZeroId() {
        int idCard = 0;
        Card card = new Card(idCard);
        Assertions.assertEquals(idCard, card.getIdCard());
    }
    @Test
    void testConstructor_NegativeID() {
        int invalidID = -1;
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Card(invalidID);
        });
        Assertions.assertEquals("ID cannot be negative", exception.getMessage());
    }

    @Test
    void testGetIdCard() {
        int id = 42;
        Card card = new Card(id);
        Assertions.assertEquals(id, card.getIdCard());
    }

    @Test
    void testSetIdCard() {
        int initialId = 42;
        int newId = 99;
        Card card = new Card(initialId);

        card.setIdCard(newId);

        Assertions.assertEquals(newId, card.getIdCard());
    }
    @Test
    public void testMapFromJson() {
        JsonObject jsonObject = new JsonObject();
        int expectedId = 123;
        jsonObject.addProperty("id", expectedId);

        Card card= new Card(0);
        card.mapFromJson(jsonObject);
        Assertions.assertEquals(expectedId, card.getIdCard());
    }
}



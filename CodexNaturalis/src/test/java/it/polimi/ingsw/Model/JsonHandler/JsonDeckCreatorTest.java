package it.polimi.ingsw.Model.JsonHandler;

import it.polimi.ingsw.Model.Cards.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonDeckCreatorTest {


    @Test
    void TestDeckCreatorGoldCards() throws IOException {
        String filePath="GoldCard.json";
        ArrayList<GoldCard> goldDeck = (ArrayList<GoldCard>) JsonDeckCreator.createDeckFromJson(GoldCard.class, filePath);
        Assertions.assertNotNull(goldDeck);

    }

    @Test
    void TestDeckCreatorInitialCard() throws IOException {
        String filePath="InitialCard.json";
        ArrayList<InitialCard> initialCards = (ArrayList<InitialCard>) JsonDeckCreator.createDeckFromJson(InitialCard.class,filePath);
        Assertions.assertNotNull(initialCards);

    }

    @Test
    void TestDeckCreatorResourceCard() throws IOException {
        String filePath="ResourceCard.json";
        ArrayList<ResourceCard> resourceDeck = (ArrayList<ResourceCard>) JsonDeckCreator.createDeckFromJson(ResourceCard.class, filePath);
        Assertions.assertNotNull(resourceDeck);

    }

    @Test
    void TestDeckObjectiveCard() throws IOException {
        String filePath="ObjectiveCard.json";
        ArrayList<ObjectiveCard> deck = (ArrayList<ObjectiveCard>) JsonDeckCreator.createDeckFromJson(ObjectiveCard.class, filePath);
        Assertions.assertNotNull(deck);
    }


}

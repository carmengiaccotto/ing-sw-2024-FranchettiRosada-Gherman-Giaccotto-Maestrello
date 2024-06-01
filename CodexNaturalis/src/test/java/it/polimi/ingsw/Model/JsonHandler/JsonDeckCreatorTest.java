package it.polimi.ingsw.Model.JsonHandler;

import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class JsonDeckCreatorTest {
    private String filePath="src/main/resources/";

    @Test
    public void TestDeckCreatorGoldCards() throws IOException {
        filePath=filePath.concat("GoldCard.json");
        ArrayList<GoldCard> goldDeck = (ArrayList<GoldCard>) JsonDeckCreator.createDeckFromJson(GoldCard.class, filePath);
        Assertions.assertNotNull(goldDeck);

    }

    @Test
    public void TestDeckCreatorInitialCard() throws IOException {
        filePath=filePath.concat("InitialCard.json");
        ArrayList<InitialCard> initialCards = (ArrayList<InitialCard>) JsonDeckCreator.createDeckFromJson(InitialCard.class,filePath);
        Assertions.assertNotNull(initialCards);

    }

    @Test
    public void TestDeckCreatorResourceCard() throws IOException {
        filePath=filePath.concat("ResourceCard.json");
        ArrayList<ResourceCard> resourceDeck = (ArrayList<ResourceCard>) JsonDeckCreator.createDeckFromJson(ResourceCard.class, filePath);
        Assertions.assertNotNull(resourceDeck);

    }

    @Test
    public void TestDeckObjectiveCard() throws IOException {
        filePath=filePath.concat("ObjectiveCard.json");
        ArrayList<ObjectiveCard> deck = (ArrayList<ObjectiveCard>) JsonDeckCreator.createDeckFromJson(ObjectiveCard.class, filePath);
        Assertions.assertNotNull(deck);
    }
}

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

    @Test
    public void TestDeckCreatorGoldCards() throws IOException {
        ArrayList<GoldCard> goldDeck = (ArrayList<GoldCard>) JsonDeckCreator.createDeckFromJson(GoldCard.class);
        Assertions.assertNotNull(goldDeck);

    }

    @Test
    public void TestDeckCreatorInitialCard() throws IOException {
        ArrayList<InitialCard> initialCards = (ArrayList<InitialCard>) JsonDeckCreator.createDeckFromJson(InitialCard.class);
        Assertions.assertNotNull(initialCards);

    }

    @Test
    public void TestDeckCreatorResourceCard() throws IOException {
        ArrayList<ResourceCard> resourceDeck = (ArrayList<ResourceCard>) JsonDeckCreator.createDeckFromJson(ResourceCard.class);
        Assertions.assertNotNull(resourceDeck);

    }

    @Test
    public void TestDeckObjectiveCard() throws IOException {
        ArrayList<ObjectiveCard> deck = (ArrayList<ObjectiveCard>) JsonDeckCreator.createDeckFromJson(ObjectiveCard.class);
        Assertions.assertNotNull(deck);
    }
}

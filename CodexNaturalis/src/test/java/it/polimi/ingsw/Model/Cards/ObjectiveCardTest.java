package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {

    @Test
    void getPoints() {
        ObjectiveCard objectiveCard = new ObjectiveCard(1, ObjectivePoints.THREE);
        assertEquals(ObjectivePoints.THREE, objectiveCard.getPoints());
    }

    @Test
    void checkGoals() {
        ObjectiveCard objectiveCard = new ObjectiveCard(1, ObjectivePoints.THREE);
        assertEquals(0, objectiveCard.CheckGoals());
    }

    @Test
    void calculatePoints() {
        ObjectiveCard objectiveCard = new ObjectiveCard(1, ObjectivePoints.THREE);
        assertEquals(6, objectiveCard.calculatePoints(2));
    }
}
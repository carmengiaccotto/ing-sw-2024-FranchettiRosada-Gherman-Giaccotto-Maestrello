package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ObjectiveCardTest {
    @Test
    public void testObjectiveCardConstructor() {
        ObjectivePoints points = ObjectivePoints.TWO;
        ObjectiveCard objectiveCard = new ObjectiveCard(3, points);
        assertNotNull(objectiveCard);
        Assertions.assertEquals(3, objectiveCard.getIdCard());
        Assertions.assertEquals(points, objectiveCard.getPoints());
    }


    @Test
    public void testGetPoints() {
        ObjectivePoints points = ObjectivePoints.TWO;
        ObjectiveCard objectiveCard = new ObjectiveCard(2, points);
        Assertions.assertEquals(points, objectiveCard.getPoints());
    }

    @Test
    public void testCalculatePoints() {
        ObjectivePoints points = ObjectivePoints.TWO;
        ObjectiveCard objectiveCard = new ObjectiveCard(1, points);
        int numberOfGoals = 3;
        int calculatedPoints = objectiveCard.CalculatePoints(numberOfGoals);
        Assertions.assertEquals(numberOfGoals * points.getValue(), calculatedPoints);
    }

    @Test
    public void testMapFromJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", 1);
        jsonObject.addProperty("points", 2);
        ObjectiveCard objectiveCard = new ObjectiveCard(1, ObjectivePoints.TWO);
        ObjectiveCard mappedCard = objectiveCard.mapFromJson(jsonObject);
        Assertions.assertEquals(1, mappedCard.getIdCard());
        Assertions.assertEquals(ObjectivePoints.TWO, mappedCard.getPoints());
    }



}
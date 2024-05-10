package it.polimi.ingsw.ModelTest.EnumTests;

import it.polimi.ingsw.model.Enumerations.ObjectivePoints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePointsTest {
    @Test
    public void testDefinedValues() {
        assertNotNull(ObjectivePoints.TWO);
        assertNotNull(ObjectivePoints.THREE);
        assertEquals(2, ObjectivePoints.TWO.getValue());
        assertEquals(3, ObjectivePoints.THREE.getValue());
    }

    @Test
    public void testEquality() {
        assertEquals(ObjectivePoints.TWO, ObjectivePoints.TWO);
        assertEquals(ObjectivePoints.THREE, ObjectivePoints.THREE);
        assertNotEquals(ObjectivePoints.TWO, ObjectivePoints.THREE);
    }

    @Test
    public void testConversions() {
        assertEquals(2, ObjectivePoints.TWO.getValue());
        assertEquals(ObjectivePoints.TWO, ObjectivePoints.valueOf("TWO"));
    }

    @Test
    public void testSwitchStatement() {
        ObjectivePoints points = ObjectivePoints.THREE;
        switch(points) {
            case TWO:
                assertEquals(2, points.getValue());
                break;
            case THREE:
                assertEquals(3, points.getValue());
                break;
            default:
                fail("Unexpected points");
        }
    }

}
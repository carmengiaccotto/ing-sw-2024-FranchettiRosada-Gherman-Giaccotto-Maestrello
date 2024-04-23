package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.ObjectivePoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectivePointsTest {
    @Test
    public void testEnumValues() {
        Assertions.assertEquals(2, ObjectivePoints.TWO.getValue());
        Assertions.assertEquals(3, ObjectivePoints.THREE.getValue());
    }

}
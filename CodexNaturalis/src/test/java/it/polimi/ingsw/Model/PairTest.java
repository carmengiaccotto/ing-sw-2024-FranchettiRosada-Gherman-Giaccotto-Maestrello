package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void setFirst() {
        Pair<Integer, Integer> pair= new Pair<>(1,2);
        pair.setFirst(3);
        assertEquals(3,pair.getFirst());
    }

    @Test
    void setSecond() {
        Pair<Integer, Integer> pair= new Pair<>(1,2);
        pair.setSecond(3);
        assertEquals(3,pair.getSecond());
    }

    @Test
    void getFirst() {
        Pair<Integer, Integer> pair= new Pair<>(1,2);
        assertEquals(1,pair.getFirst());
    }

    @Test
    void getSecond() {
        Pair<Integer, Integer> pair= new Pair<>(1,2);
        assertEquals(2,pair.getSecond());
    }

    @Test
    void testToString() {
        Pair<Integer, Integer> pair = new Pair<>(1, 2);
        assertEquals("(1, 2)", pair.toString());
    }


    @Test
    void testEquals() {
        Pair<Integer, Integer> pair1 = new Pair<>(1, 2);
        Pair<Integer, Integer> pair2 = new Pair<>(1, 2);
        Pair<Integer, Integer> pair3 = new Pair<>(3, 4);
        Pair<Integer, Integer> pair4 = null;
        String differentClass = "Test";

        // Test when the same object is compared with itself
        assertTrue(pair1.equals(pair1));

        // Test when the object is compared with null
        assertFalse(pair1.equals(pair4));

        // Test when the object is compared with an object of a different class
        assertFalse(pair1.equals(differentClass));

        // Other tests
        assertTrue(pair1.equals(pair2));
        assertFalse(pair1.equals(pair3));
    }
}
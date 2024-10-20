package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PairTest {

    @Test
    void testPairCreation() {
        Pair<String, Integer> pair = new Pair<>("key", 1);
        assertEquals("key", pair.key);
        assertEquals(1, pair.value);
    }

    @Test
    void testPairEquality() {
        Pair<String, Integer> pair1 = new Pair<>("key", 1);
        Pair<String, Integer> pair2 = new Pair<>("key", 1);
        assertEquals(pair1.key, pair2.key);
        assertEquals(pair1.value, pair2.value);
    }

    @Test
    void testPairNotEqual() {
        Pair<String, Integer> pair1 = new Pair<>("key", 1);
        Pair<String, Integer> pair2 = new Pair<>("differentKey", 2);
        assertNotEquals(pair1.key, pair2.key);
        assertNotEquals(pair1.value, pair2.value);
    }
}

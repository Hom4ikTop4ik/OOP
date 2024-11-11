package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void constructorTest() {
        Pair pair = new Pair(1, 2);
        assertNotNull(pair);
        assertEquals(1, pair.fst);
        assertEquals(2, pair.snd);
    }

    @Test
    void toStringTest() {
        Pair pair = new Pair(3, 4);
        assertEquals("(3, 4)", pair.toString());

        Pair pair2 = new Pair(-3, -4);
        assertEquals("(-3, -4)", pair2.toString());
    }

    @Test
    void equalsSameObjectTest() {
        Pair pair = new Pair(5, 6);
        assertTrue(pair.equals(pair)); // Сравнение объекта с самим собой
        assertEquals(pair, pair);
    }

    @Test
    void equalsEqualObjectsTest() {
        Pair pair1 = new Pair(7, 8);
        Pair pair2 = new Pair(7, 8);
        assertTrue(pair1.equals(pair2)); // Сравнение одинаковых объектов
        assertEquals(pair1, pair2);
    }

    @Test
    void equalsDifferentObjectsTest() {
        Pair pair1 = new Pair(9, 10);
        Pair pair2 = new Pair(10, 9);
        assertFalse(pair1.equals(pair2)); // Сравнение разных объектов
        assertNotEquals(pair1, pair2);

        Pair pair3 = new Pair(9, -10);
        assertFalse(pair1.equals(pair3));
        assertNotEquals(pair1, pair3);
    }

    @Test
    void equalsNullTest() {
        Pair pair = new Pair(11, 12);
        assertFalse(pair.equals(null)); // Сравнение с null
        assertNotEquals(pair, null);
    }

    @Test
    void equalsDifferentClassTest() {
        Pair pair = new Pair(13, 14);
        String str = "(13, 14)";
        assertFalse(pair.equals(str)); // Сравнение с объектом другого класса
        assertNotEquals(pair, str);
    }
}
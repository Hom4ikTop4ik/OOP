package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelperTest {
    @Test
    void checkIndexesValidTest() {
        int[][] matrix = new int[3][3];
        assertDoesNotThrow(() -> Helper.checkIndexes(0, 2, matrix));
        assertDoesNotThrow(() -> Helper.checkIndexes(1, 1, matrix));
        assertDoesNotThrow(() -> Helper.checkIndexes(2, 0, matrix));
    }

    @Test
    void checkIndexesFromNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexes(-1, 2, matrix)
        );
        assertEquals("From index меньше нуля", exception.getMessage());
    }

    @Test
    void checkIndexesToNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexes(1, -1, matrix)
        );
        assertEquals("To index меньше нуля", exception.getMessage());
    }

    @Test
    void checkIndexesFromTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexes(3, 1, matrix)
        );
        assertEquals("From index слишком большой для текущего графа", exception.getMessage());
    }

    @Test
    void checkIndexesToTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexes(1, 3, matrix)
        );
        assertEquals("To index слишком большой для текущего графа", exception.getMessage());
    }

    @Test
    void checkIndexesNegValidTest() {
        assertDoesNotThrow(() -> Helper.checkIndexesNeg(0, 1));
        assertDoesNotThrow(() -> Helper.checkIndexesNeg(2, 0));
    }

    @Test
    void checkIndexesNegFromNegativeTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexesNeg(-1, 1)
        );
        assertEquals("From index меньше нуля", exception.getMessage());
    }

    @Test
    void checkIndexesNegToNegativeTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndexesNeg(1, -1)
        );
        assertEquals("To index меньше нуля", exception.getMessage());
    }

    @Test
    void checkIndexValidTest() {
        int[][] matrix = new int[3][3];
        assertDoesNotThrow(() -> Helper.checkIndex(0, matrix));
        assertDoesNotThrow(() -> Helper.checkIndex(2, matrix));
    }

    @Test
    void checkIndexNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndex(-1, matrix)
        );
        assertEquals("Index меньше нуля", exception.getMessage());
    }

    @Test
    void checkIndexTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Helper.checkIndex(3, matrix)
        );
        assertEquals("Index слишком большой для текущего графа", exception.getMessage());
    }
}
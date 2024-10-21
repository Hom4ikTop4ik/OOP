package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HelperTest {

    /**
     * Tests the Helper.checkIndexes() method for valid index pairs within the matrix bounds.
     * This method should not throw any exceptions for valid index inputs.
     */
    @Test
    void checkIndexesValidTest() {
        int[][] matrix = new int[3][3];
        assertDoesNotThrow(() -> Helper.checkIndexes(0, 2, matrix));
        assertDoesNotThrow(() -> Helper.checkIndexes(1, 1, matrix));
        assertDoesNotThrow(() -> Helper.checkIndexes(2, 0, matrix));
    }

    /**
     * Tests the Helper.checkIndexes() method when the "from" index is negative.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesFromNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexes(-1, 2, matrix)
                );
        assertEquals("From index меньше нуля", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndexes() method when the "to" index is negative.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesToNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexes(1, -1, matrix)
                );
        assertEquals("To index меньше нуля", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndexes() method when the "from" index exceeds the matrix bounds.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesFromTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexes(3, 1, matrix)
                );
        assertEquals("From index слишком большой для текущего графа", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndexes() method when the "to" index exceeds the matrix bounds.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesToTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexes(1, 3, matrix)
                );
        assertEquals("To index слишком большой для текущего графа", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndexesNeg() method for valid index pairs.
     * This method should not throw any exceptions for valid inputs.
     */
    @Test
    void checkIndexesNegValidTest() {
        assertDoesNotThrow(() -> Helper.checkIndexesNeg(0, 1));
        assertDoesNotThrow(() -> Helper.checkIndexesNeg(2, 0));
    }

    /**
     * Tests the Helper.checkIndexesNeg() method when the "from" index is negative.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesNegFromNegativeTest() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexesNeg(-1, 1)
                );
        assertEquals("From index меньше нуля", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndexesNeg() method when the "to" index is negative.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexesNegToNegativeTest() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndexesNeg(1, -1)
                );
        assertEquals("To index меньше нуля", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndex() method for valid single indexes within the matrix bounds.
     * This method should not throw any exceptions for valid index inputs.
     */
    @Test
    void checkIndexValidTest() {
        int[][] matrix = new int[3][3];
        assertDoesNotThrow(() -> Helper.checkIndex(0, matrix));
        assertDoesNotThrow(() -> Helper.checkIndex(2, matrix));
    }

    /**
     * Tests the Helper.checkIndex() method when the index is negative.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexNegativeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndex(-1, matrix)
                );
        assertEquals("Index меньше нуля", exception.getMessage());
    }

    /**
     * Tests the Helper.checkIndex() method when the index exceeds the matrix bounds.
     * The method should throw an IllegalArgumentException with the correct error message.
     */
    @Test
    void checkIndexTooLargeTest() {
        int[][] matrix = new int[3][3];
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        Helper.checkIndex(3, matrix)
                );
        assertEquals("Index слишком большой для текущего графа", exception.getMessage());
    }
}

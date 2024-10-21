package ru.nsu.martynov;

/**
 * The Helper class provides utility methods for validating indices in graph operations
 * and defines some constant prime numbers for general use.
 */
public class Helper {
    /**
     * Validates that both 'from' and 'to' indices are within bounds of the matrix.
     *
     * @param from   the source vertex index
     * @param to     the destination vertex index
     * @param matrix the graph's adjacency or incidence matrix
     * @throws IllegalArgumentException if the indices are out of bounds
     */
    public static void checkIndexes(int from, int to, int[][] matrix) {
        if (from < 0) {
            throw new IllegalArgumentException("From index меньше нуля");
        }
        if (from >= matrix.length) {
            throw new IllegalArgumentException("From index слишком большой для текущего графа");
        }
        if (to < 0) {
            throw new IllegalArgumentException("To index меньше нуля");
        }
        if (to >= matrix.length) {
            throw new IllegalArgumentException("To index слишком большой для текущего графа");
        }
    }

    /**
     * Validates that both 'from' and 'to' indices are non-negative.
     * This method is used in scenarios where matrix bounds are not considered.
     *
     * @param from the source vertex index
     * @param to   the destination vertex index
     * @throws IllegalArgumentException if the indices are negative
     */
    public static void checkIndexesNeg(int from, int to) {
        if (from < 0) {
            throw new IllegalArgumentException("From index меньше нуля");
        }
        if (to < 0) {
            throw new IllegalArgumentException("To index меньше нуля");
        }
    }

    /**
     * Validates that a single index is within the bounds of the matrix.
     *
     * @param index  the vertex index to check
     * @param matrix the graph's adjacency or incidence matrix
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public static void checkIndex(int index, int[][] matrix) {
        if (index < 0) {
            throw new IllegalArgumentException("Index меньше нуля");
        }
        if (index >= matrix.length) {
            throw new IllegalArgumentException("Index слишком большой для текущего графа");
        }
    }
}

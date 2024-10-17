package ru.nsu.martynov;

// MagicNumbersAndStrings
public class Helper {
    public static final int FIRST_PRIME = 83;
    public static final int SECOND_PRIME = 89;
    public static final int THIRD_PRIME = 97;

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

    public static void checkIndexesNeg(int from, int to) {
        if (from < 0) {
            throw new IllegalArgumentException("From index меньше нуля");
        }
        if (to < 0) {
            throw new IllegalArgumentException("To index меньше нуля");
        }
    }

    public static void checkIndex(int index, int[][] matrix) {
        if (index < 0) {
            throw new IllegalArgumentException("Index меньше нуля");
        }
        if (index >= matrix.length) {
            throw new IllegalArgumentException("Index слишком большой для текущего графа");
        }
    }
}

package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class MainTest {
    // Generate random array
    public static int[] randomArray(int size, int from, int to) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            // Random number in [from, to)
            array[i] = rand.nextInt(to - from) + from;
        }
        return array;
    }

    private static boolean arrayIsSort(int[] arr, boolean reverse) {
        boolean sorted = true;

        if (reverse) {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] < arr[i + 1]) {
                    sorted = false;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    sorted = false;
                    break;
                }
            }
        }

        return sorted;
    }

    @Test
    void HeapSortTest() {
        // Create 10 test arrays.
        int[][] testArrays = new int[10][];

        // Fill arrays different values.
        testArrays[0] = new int[]{5, 3, 8, 6, 1, 9, 2, 7, 4}; // Unsorted array
        testArrays[1] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}; // Sorted array
        testArrays[2] = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}; // Sorted-reversed array
        testArrays[3] = new int[]{1}; // 1 elem
        testArrays[4] = new int[0]; // Empty array
        testArrays[5] = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5}; // All elems are same
        testArrays[6] = new int[]{100, 20, -5, 200, 0, 50}; // Negatives and positives values.
        testArrays[7] = randomArray(10, -1000, 1000); // Random array of length 10
        testArrays[8] = randomArray(1000, -1000, 1000); // Random array of length 1000
        testArrays[9] = randomArray(1000*1000, -1000, 1000); // Random array of length 1kk

        for (int i = 0; i < testArrays.length; i++) {
            int[] tmp = Main.heapSort(testArrays[i], false);
            assertTrue(arrayIsSort(tmp, false));

            tmp = Main.heapSort(testArrays[i], true);
            assertTrue(arrayIsSort(tmp, true));
        }
    }
}
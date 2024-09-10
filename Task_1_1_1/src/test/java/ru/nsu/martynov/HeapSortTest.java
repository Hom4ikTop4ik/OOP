package ru.nsu.martynov;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {
    private static boolean arrayIsSort(int[] arr, boolean reverse) {
        boolean sorted = true;

        if (reverse) {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] < arr[i + 1]) {
                    sorted = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    sorted = false;
                    break;
                }
            }
        }

        return sorted;
    }

    private static void printArray(int[] arr) {
        if (arr.length <= 0) {
            return;
        }

        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1]);
        System.out.println("}.");
    }

    @Test
    void heapSortTest() {
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
        testArrays[7] = Heapsort.randomArray(10, -1000, 1000); // Random array of length 10
        testArrays[8] = Heapsort.randomArray(1000, -1000, 1000); // Random array of length 1000
        testArrays[9] = Heapsort.randomArray(1000*1000, -1000, 1000); // Random array of length 1kk

        for (int i = 0; i < testArrays.length; i++) {
            int[] tmp = Heapsort.heapSort(testArrays[i], false);
            assertTrue(arrayIsSort(tmp, false));

            tmp = Heapsort.heapSort(testArrays[i], true);
            assertTrue(arrayIsSort(tmp, true));
        }
    }

    @Test
    void randomArrayTest() {
        assertNotNull(Heapsort.randomArray(10, 0, 1));
        assertNotNull(Heapsort.randomArray(256, -128, 127));

        assertNull(Heapsort.randomArray(0, -10, 10));
        assertNull(Heapsort.randomArray(-10, -10, 10));
        assertNull(Heapsort.randomArray(-10, 10, -10));
        assertNull(Heapsort.randomArray(10, 5, 5));
        assertNull(Heapsort.randomArray(5, 0, 0));
        assertNull(Heapsort.randomArray(0, 0, 0));
    }

    @Test
    void swapTest() {
        int[] arr = new int[]{0, 1, 2, 3};
        int[] brr = new int[]{0, 1, 2, 3};

        Heapsort.swap(brr, -1, 0, brr.length);
        assertArrayEquals(arr, brr);
        Heapsort.swap(brr, 0, -1, arr.length);
        assertArrayEquals(arr, brr);

        Heapsort.swap(brr, 0, brr.length, brr.length);
        assertArrayEquals(arr, brr);
        Heapsort.swap(brr,153218, 1, brr.length);
        assertArrayEquals(arr, brr);

        Heapsort.swap(brr, 0, 0, brr.length);
        assertArrayEquals(arr, brr);
        Heapsort.swap(brr, 2, 2, brr.length);
        assertArrayEquals(arr, brr);

        Heapsort.swap(brr, 0, 1, brr.length);
        arr[0] = 1;
        arr[1] = 0;
        assertArrayEquals(arr, brr);
        Heapsort.swap(brr, 3, 2, brr.length);
        arr[3] = 2;
        arr[2] = 3;
        assertArrayEquals(arr, brr);
    }
}
package ru.nsu.martynov;

import java.util.Random;

public class Heapsort {

    /**
     * Function for sorting int array. O(n * log n)
     *
     * @param arr — int array.
     * @param reverse — boolean flag, true — need get reversed sorted array.
     * @return sorted copy of input array.
     */
    public static int[] heapSort(int[] arr, boolean reverse) {
        int n = arr.length;
        int[] brr = new int[n];

        // Copy array
        for (int i = 0; i < n; i++) {
            brr[i] = arr[i];
        }

        // Sort heap.
        for (int i = n-1; i >= 0; i--) {
            siftDown(brr, i, n, reverse);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(brr, 0, i, n);
            siftDown(brr, 0, i, reverse);
        }

        return brr;
    }

    /**
     * Function aka "heapify". Swap to elems in binary tree if parent more/less than child.
     *
     * @param arr — heap's array;
     * @param i — current index in array.
     * @param n — len of array.
     * @param reverse — boolean flag, true — need get reversed sorted array.
     */
    private static void siftDown(int[] arr, int i, int n, boolean reverse) {
        if (i < 0 || i >= n) {
            return;
        }

        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (reverse) {
            int smallest = i;

            // Only if left in binary tree
            if (left < n && arr[left] < arr[smallest]) {
                smallest = left;
            }
            // Only if right in binary tree
            if (right < n && arr[right] < arr[smallest]) {
                smallest = right;
            }

            // Min in UP
            if (arr[smallest] < arr[i]) {
                swap(arr, i, smallest, n);
                siftDown(arr, smallest, n, reverse);
            }
        } else {
            int biggest = i;

            // Only if left in binary tree
            if (left < n && arr[left] > arr[biggest]) {
                biggest = left;
            }
            // Only if right in binary tree
            if (right < n && arr[right] > arr[biggest]) {
                biggest = right;
            }

            // Max in UP
            if (arr[biggest] > arr[i]) {
                swap(arr, i, biggest, n);
                siftDown(arr, biggest, n, reverse);
            }
        }
    }

    /**
     * Function for swapping values in array by indexes i and j.
     *
     * @param arr — array;
     * @param i — first index;
     * @param j — second index;
     * @param n — len of array (just in case if out of range).
     */
    private static void swap(int[] arr, int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Generate random array.
     *
     * @param length — length of array.
     * @param from — minimal number. [from, to)
     * @param to — maximal number. [from, to)
     * @return array — length, numbers between from and to.
     */
    private static int[] randomArray(int length, int from, int to) {
        Random rand = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            // Random number in [from, to)
            array[i] = rand.nextInt(to - from) + from;
        }
        return array;
    }

    /**
     * Print tables with some spent-time info.
     *
     * @param args — are not using.
     */
    public static void main(String[] args) {
        int koef = 1000;
        int maxIters = 100 * koef;
        int testKoef = 5;

        int from = -1_000_000;
        int to = 1_000_000;

        for (int i = 10; i <= maxIters; i+=koef) {
            long timeSort = 0;
            long timeRev = 0;

            int iters = testKoef * maxIters / i;

            System.out.printf("Len is %d. Iterations: %d.%n", i, iters);

            for (int j = iters; j > 0; j--) {
                int[] arr = randomArray(i, from, to);

                long startTime = System.currentTimeMillis();
                heapSort(arr, false);
                long endTime = System.currentTimeMillis();


                long startTimeRev = System.currentTimeMillis();
                heapSort(arr, true);
                long endTimeRev = System.currentTimeMillis();

                timeSort += (endTime - startTime);
                timeRev += (endTimeRev - startTimeRev);
            }

            // For eyes:
            System.out.println("Name | Total (ms) | ms per array");
            System.out.printf( "Sort | %10d | %.3f%n", timeSort, (double)timeSort/iters);
            System.out.printf( "Rev  | %10d | %.3f%n%n", timeRev, (double)timeRev/iters);

            // For graphics in Excel:
            // System.out.printf( "%d / %d / %d / %d%n", i, timeSort, timeRev, iters);
        }
    }
}
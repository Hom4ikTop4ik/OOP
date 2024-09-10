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
    // Generate random array
    private static int[] randomArray(int length, int from, int to) {
        Random rand = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            // Random number in [from, to)
            array[i] = rand.nextInt(to - from) + from;
        }
        return array;
    }

    public static void main(String[] args) {

    }
}
package ru.nsu.martynov;

public class Main {

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

    private static void swap(int[] arr, int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
    }
}
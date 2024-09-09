package ru.nsu.martynov;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static int[] heapSort(int[] arr, boolean reverse) {
        int n = arr.length;
        printArray(arr);
        int[] brr = new int[n];

        // copy array
        for (int i = 0; i < n; i++) {
            brr[i] = arr[i];
        }

        // Do sorted heap.
        // I can explain (n/2-1) in life (maybe draw binary tree).
        for (int i = n-1; i >= 0; i--) {
            siftDown(brr, i, n, reverse);
        }
        printArray(brr);

        for (int i = n - 1; i >= 0; i--) {
            swap(brr, 0, i, n);
            siftDown(brr, 0, i, reverse); // do sift with brr[0], brr[i] is last elem in heap.
        }

        printArray(brr);
        return brr;
    }

    private static void siftUp(int[] arr, int i, int n, boolean reverse) {
        int parent = (i-1)/2;

        if (parent < 0 || i >= n)
            return;

        if (reverse) {
            // min in UP (in last steps min in END)
            if (arr[i] < arr[parent]) {
                swap(arr, parent, i, n);
            }
        }
        else {
            // max in UP
            if (arr[i] > arr[parent]) {
                swap(arr, parent, i, n);
            }
        }
    }

    private static void siftDown(int[] arr, int i, int n, boolean reverse) {
        if (i < 0 || i >= n) {
            return;
        }

        int left = 2*i+1;
        int right = 2*i+2;

        if (reverse) {
            int smallest = i;

            // only if left in binary tree
            if (left < n && arr[left] < arr[smallest]) {
                smallest = left;
            }
            // only if right in binary tree
            if (right < n && arr[right] < arr[smallest]) {
                smallest = right;
            }

            // min in UP
            if (arr[smallest] < arr[i]) {
                swap(arr, i, smallest, n);
            }
        }
        else {
            int biggest = i;

            // only if left in binary tree
            if (left < n && arr[left] > arr[biggest]) {
                biggest = left;
            }
            // only if right in binary tree
            if (right < n && arr[right] > arr[biggest]) {
                biggest = right;
            }

            // max in UP
            if (arr[biggest] > arr[i]) {
                swap(arr, i, biggest, n);
            }
        }
    }

    public static void printArray(int[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        if (arr.length > 0) {
            System.out.print(arr[arr.length - 1]);
        }
        System.out.print("}.\n");
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
package ru.nsu.martynov;

public class Main {
    public class Bogdans {}
    public Bogdans house;

    public static void main(String[] args) {
        try {
            UsualPrime usualPrime = new UsualPrime();
            int[] brr = {1,2,3,5,7,11,13,17,19,23};
            int len = 1000 * 1000 * 1000;
            int[] arr = new int[len];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = brr[i % 10];
            }
            System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            for (int i = 0; i < len; i++) {
                arr[i] = brr[i % 10];
            }
            System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + usualPrime.hasCompositeNumber(arr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
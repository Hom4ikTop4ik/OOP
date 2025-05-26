package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPrimes {

    @Test
    void hasCompositeNumber_1_10() {
        int[] list = new int[10];
        for (int i = 0; i < 10; i++) {
            list[i] = i;
        }

        OptimusPrime optimusPrime = new OptimusPrime();
        assertTrue(optimusPrime.hasCompositeNumber(list));

        AlphaTron alphaTron = new AlphaTron(8);
        assertTrue(alphaTron.hasCompositeNumber(list));

        UsualPrime usualPrime = new UsualPrime();
        assertTrue(usualPrime.hasCompositeNumber(list));
    }

    @Test
    void hasCompositeNumber_onlyPrimes() {
        int[] brr = {1, 2, 3, 5, 7, 11, 13, 17, 19, 23};
        int[] list = new int[brr.length];
        for (int i = 0; i < brr.length; i++) {
            list[i] = brr[i];
        }

        OptimusPrime optimusPrime = new OptimusPrime();
        assertFalse(optimusPrime.hasCompositeNumber(list));

        AlphaTron alphaTron = new AlphaTron(8);
        assertFalse(alphaTron.hasCompositeNumber(list));

        UsualPrime usualPrime = new UsualPrime();
        assertFalse(usualPrime.hasCompositeNumber(list));
    }

    @Test
    void hasCompositeNumber_bigArray() {

        int kk = 1000 * 1000;
        double cnt = 0.1;
        int len = (int)(cnt * kk);

        System.out.println("Array's length: " + cnt + "kk");

        int[] brr = {
//                1, 2, 3, 5, 7, 11, 13, 17, 19, 23
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053
        };

        int[] list = new int[len];
        for (int i = 0; i < len; i++) {
            list[i] = brr[i % brr.length];
        }

        System.out.println("Lets calc!");
        boolean cutePrint = true;

        UsualPrime usualPrime = new UsualPrime();
        long startTime = System.nanoTime();
        final boolean usual = usualPrime.hasCompositeNumber(list);
        long estimatedTime = System.nanoTime() - startTime;
        if (!cutePrint) {
            System.out.println("Usual: ");
        }
        System.out.print("   ");
        System.out.println(estimatedTime);
        assertFalse(usual);

        OptimusPrime optimusPrime = new OptimusPrime();
        startTime = System.nanoTime();
        final boolean optimus = optimusPrime.hasCompositeNumber(list);
        estimatedTime = System.nanoTime() - startTime;
        if (!cutePrint) {
            System.out.println("Optimus: ");
        }
        System.out.println(estimatedTime);
        assertFalse(optimus);

        for (int i = 1; i < 32; i++) {
            AlphaTron alphaTron = new AlphaTron(i);
            startTime = System.nanoTime();
            final boolean alpha = alphaTron.hasCompositeNumber(list);
            estimatedTime = System.nanoTime() - startTime;
            if (!cutePrint) {
                System.out.printf("Alpha %d: ", i);
            }
            System.out.printf("%d %n", estimatedTime);
            assertFalse(alpha);
        }
    }
}
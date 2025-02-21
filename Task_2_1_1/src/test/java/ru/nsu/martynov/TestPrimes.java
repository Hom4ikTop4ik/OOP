package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class TestPrimes {

    @Test
    void hasCompositeNumber_1_10() {
        ArrayList<Integer> list = new ArrayList<Integer>(10);
        for (int i = 0; i < 10; i++) {
            list.add(i);
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
        ArrayList<Integer> list = new ArrayList<Integer>(brr.length);
        for (int i = 0; i < brr.length; i++) {
            list.add(brr[i]);
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
        int cnt = 10;
        int len = cnt * kk;

        System.out.println("Array's length: " + cnt + "kk");

        int[] brr = {1, 2, 3, 5, 7, 11, 13, 17, 19, 23};

        ArrayList<Integer> list = new ArrayList<Integer>(len);
        for (int i = 0; i < len; i++) {
            list.add(brr[i % brr.length]);
        }

        System.out.println("Lets calc!");
        boolean cutePrint = true;

        UsualPrime usualPrime = new UsualPrime();
        long startTime = System.nanoTime();
        Boolean usual = usualPrime.hasCompositeNumber(list);
        long estimatedTime = System.nanoTime() - startTime;
        if (!cutePrint) {
            System.out.println("Usual: ");
        }
        System.out.println(estimatedTime);
        assertFalse(usual);

        OptimusPrime optimusPrime = new OptimusPrime();
        startTime = System.nanoTime();
        Boolean optimus = optimusPrime.hasCompositeNumber(list);
        estimatedTime = System.nanoTime() - startTime;
        if (!cutePrint) {
            System.out.println("Optimus: ");
        }
        System.out.println(estimatedTime);
        assertFalse(optimus);

        for (int i = 1; i < 32; i++) {
            AlphaTron alphaTron = new AlphaTron(i);
            startTime = System.nanoTime();
            Boolean alpha = alphaTron.hasCompositeNumber(list);
            estimatedTime = System.nanoTime() - startTime;
            if (!cutePrint) {
                System.out.printf("Alpha %d: ", i);
            }
            System.out.printf("%d %n", estimatedTime);
            assertFalse(alpha);
        }
    }
}
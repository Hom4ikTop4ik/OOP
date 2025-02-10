package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        int[] list = {1,2,3,5,7,11,13,17,19,23};

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
        int cnt = 200;
        int len = cnt * kk;

        int[] brr = {1,2,3,5,7,11,13,17,19,23};

        int[] list = new int[len];
        for (int i = 0; i < len; i++) {
            list[i] = brr[i % brr.length];
        }


        UsualPrime usualPrime = new UsualPrime();
        long startTime = System.nanoTime();
        Boolean usual = usualPrime.hasCompositeNumber(list);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Usual: " + estimatedTime);
        assertFalse(usual);

        OptimusPrime optimusPrime = new OptimusPrime();
        startTime = System.nanoTime();
        Boolean optimus = optimusPrime.hasCompositeNumber(list);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Optimus: " + estimatedTime);
        assertFalse(optimus);

        for (int i = 1; i < 32; i++) {
            AlphaTron alphaTron = new AlphaTron(i);
            startTime = System.nanoTime();
            Boolean alpha = alphaTron.hasCompositeNumber(list);
            estimatedTime = System.nanoTime() - startTime;
            System.out.printf("Alpha %d: %d" + System.lineSeparator(), i, estimatedTime);
            assertFalse(alpha);
        }
    }
}
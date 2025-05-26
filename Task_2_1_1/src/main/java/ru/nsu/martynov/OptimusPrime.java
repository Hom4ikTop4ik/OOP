package ru.nsu.martynov;

import java.util.Arrays;

/**
 * Stream prime checker.
 */
public class OptimusPrime implements Prime {

    /**
     * Function.
     *
     * @param primes — array of numbers
     * @return boolean value: true if array has composite number
     */
    public Boolean hasCompositeNumber(int[] primes) {
        return Arrays.stream(primes).parallel().anyMatch(x -> !isPrime(x));
    }
}

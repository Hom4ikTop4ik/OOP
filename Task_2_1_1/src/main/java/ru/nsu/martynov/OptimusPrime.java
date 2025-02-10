package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

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
        return Arrays.stream(primes).anyMatch(x -> !isPrime(x));
    }
}

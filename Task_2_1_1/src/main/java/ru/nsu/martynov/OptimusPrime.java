package ru.nsu.martynov;

import java.util.ArrayList;

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
    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
        return primes.parallelStream().anyMatch(x -> !isPrime(x));
    }
}

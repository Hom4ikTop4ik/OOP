package ru.nsu.martynov;

import java.util.ArrayList;

/**
 * Simple prime checker.
 */
public class UsualPrime implements Prime {

    /**
     * Function.
     *
     * @param primes — array of numbers
     * @return boolean value: true if it has composite number
     */
    public Boolean hasCompositeNumber(int[] primes) {
        for (int i = 0; i < primes.length - 1; i++) {
            if (!isPrime(primes[i])) {
                return true;
            }
        }
        return false;
    }
}

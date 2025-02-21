package ru.nsu.martynov;

import java.util.ArrayList;

/**
 * Interface with default method.
 */
public interface Prime {

    default Boolean isPrime(Integer num) {
        for (int i = 2; i*i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method.
     *
     * @param primes — array of numbers
     * @return boolean value: true if array has composite number
     */
    Boolean hasCompositeNumber(ArrayList<Integer> primes);
}

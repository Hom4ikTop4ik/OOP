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
    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
        for (int i = 0; i < primes.size() - 1; i++) {
            if (!isPrime(primes.get(i))) {
                return true;
            }
        }
        return false;
    }

    /*
    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
        boolean answer = false;
        int repeat = 10;
        for (int r = 0; r < repeat; r++) {
            for (int i = 0; i < primes.size() - 1; i++) {
                if (!isPrime(primes.get(i))) {
                    answer = true;
                }
            }
        }
        return answer;
    }
    */
}

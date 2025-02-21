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
        // return Arrays.stream(primes).anyMatch(x -> !isPrime(x));
        return primes.parallelStream().anyMatch(x -> !isPrime(x));
    }

    /*
    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
        int repeat = 10;
        boolean answer = false;
        for (int r = 0; r < repeat; r++)
            answer = primes.parallelStream().anyMatch(x -> !isPrime(x));
        return answer;
    }
    */
}

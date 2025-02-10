package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

// Stream
public class OptimusPrime implements Prime {

//    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
//        Stream<Integer> stream = primes.stream();
//        return stream.anyMatch(x -> !isPrime(x));
//    }

    public Boolean hasCompositeNumber(int[] primes) {
        return Arrays.stream(primes).anyMatch(x -> !isPrime(x));
        // Stream<Integer> stream = primes.stream();
        // return stream.anyMatch(x -> !isPrime(x));
    }
}

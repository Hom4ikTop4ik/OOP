package ru.nsu.martynov;

import java.util.ArrayList;

public interface Prime {
    default Boolean isPrime(Integer num) {
        for (int i = 2; i*i <= num; i++) {
//        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public Boolean hasCompositeNumber(/*ArrayList<Integer> primes*/ int[] primes);

}

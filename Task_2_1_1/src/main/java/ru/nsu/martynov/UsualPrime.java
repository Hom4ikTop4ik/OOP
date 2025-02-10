package ru.nsu.martynov;

import java.util.ArrayList;

public class UsualPrime implements Prime {

//    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
//        for (int i = 0; i < primes.size() - 1; i++) {
//            if (!isPrime(primes.get(i))) {
//                return true;
//            }
//        }
//        return false;
//    }

    public Boolean hasCompositeNumber(int[] primes) {
        for (int i = 0; i < primes.length - 1; i++) {
            if (!isPrime(primes[i])) {
                return true;
            }
        }
        return false;
    }
}

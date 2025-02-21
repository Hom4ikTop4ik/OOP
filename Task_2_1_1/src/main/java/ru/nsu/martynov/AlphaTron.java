package ru.nsu.martynov;

import java.util.ArrayList;

/**
 * Thread prime checker.
 */
public class AlphaTron implements Prime {
    int countOfThread;

    /**
     * Constructor.
     *
     * @param cnt — count of threads.
     */
    public AlphaTron(int cnt) {
        countOfThread = cnt;
    }

    volatile boolean found = false;

    /**
     * Helper function.
     *
     * @param primes — array with numbers
     * @param start — start index
     * @param end — end index (last index will be end-1)
     * @return true if subarray primes[start; end) has composite number
     */
    private void check/*стук-стук*/(ArrayList<Integer> primes, int start, int end) {
        for (int i = start; !found && i < end; i++) {
            if (!isPrime(primes.get(i))) {
                found = true;
            }
        }
    }

//    private void check/*стук-стук*/(ArrayList<Integer> primes, int start, int end) {
//        int repeat = 10;
//        for (int r = 0; r < repeat; r++) {
//            for (int i = start; !found && i < end; i++) {
//                if (!isPrime(primes.get(i))) {
//                    found = true;
//                }
//            }
//        }
//    }

    /**
     * Function.
     *
     * @param primes — array if numbers.
     * @return true if array has composite number.
     */
    public Boolean hasCompositeNumber(ArrayList<Integer> primes) {
        Thread[] threads = new Thread[countOfThread];
        found = false;

        int cnt = primes.size() / countOfThread + (primes.size() % countOfThread == 0 ? 0 : 1);
        int i = 0;
        for (; !found && i < countOfThread; i++) {
            final int start = i * cnt;
            final int end = Math.min(start + cnt, primes.size());
            threads[i] = new Thread(() -> check(primes, start, end));
            threads[i].start();
        }

        for (int j = 0; j < i; j++) {
            Thread thread = threads[j];
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        return found;
    }
}

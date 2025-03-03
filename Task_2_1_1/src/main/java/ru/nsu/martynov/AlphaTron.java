package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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

    volatile AtomicBoolean found = new AtomicBoolean(false);

    /**
     * Helper function.
     *
     * @param primes — array with numbers
     * @param start — start index
     * @param end — end index (last index will be end-1)
     * @return true if subarray primes[start; end) has composite number
     */
    private void check/*стук-стук*/(int[] primes, int start, int end, int coef) {
        for (int i = start; i < end; i++) {
            if (!isPrime(primes[i])) {
                found.set(true);
                return;
            }
            if (i % (100*coef) == 0 && found.get()) {
                return;
            }
        }
    }

    /**
     * Function.
     *
     * @param primes — array if numbers.
     * @return true if array has composite number.
     */
    public Boolean hasCompositeNumber(int[] primes) {
        Thread[] threads = new Thread[countOfThread];
        found.set(false);

        int cnt = primes.length / countOfThread + (primes.length % countOfThread == 0 ? 0 : 1);
        int i = 0;
        for (; !found.get() && i < countOfThread; i++) {
            final int start = i * cnt;
            final int end = Math.min(start + cnt, primes.length);

            threads[i] = new Thread(() -> check(primes, start, end, countOfThread));
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

        return found.get();
    }
}

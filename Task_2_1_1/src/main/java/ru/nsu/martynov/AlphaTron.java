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

    /**
     * Helper function.
     *
     * @param primes — array with numbers
     * @param start — start index
     * @param end — end index (last index will be end-1)
     * @return true if subarray primes[start; end) has composite number
     */
    private Boolean check/*стук-стук*/(int[] primes, int start, int end) {
        for (int i = start; i < end; i++) {
            if (found) {
                break;
            }
            if (!isPrime(primes[i])) {
                found = true;
                return true;
            }
        }
        return found;
    }

    Boolean found = false;

    /**
     * Function.
     *
     * @param primes — array if numbers.
     * @return true if array has composite number.
     */
    public Boolean hasCompositeNumber(int[] primes) {
        Thread[] threads = new Thread[countOfThread];

        int cnt = primes.length / countOfThread + (primes.length % countOfThread == 0 ? 0 : 1);
        for (int i = 0; i < countOfThread; i++) {
            int start = i * cnt;
            int end = Math.min(start + cnt, primes.length);
            threads[i] = new Thread(() -> {
                if (!found) check(primes, start, end);
            });
            if (found) {
                continue;
            }
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        Boolean hihihaha = found;
        found = false;
        return hihihaha;
    }
}

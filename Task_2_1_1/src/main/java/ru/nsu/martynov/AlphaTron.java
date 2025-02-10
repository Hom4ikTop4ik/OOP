package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

// Thread
public class AlphaTron implements Prime {
    int countOfThread;

    public AlphaTron(int cnt) {
        countOfThread = cnt;
    }

    public Boolean check/*стук-стук*/(int[] primes, int start, int end) {
        for (int i = start; i < end; i++) {
            if (found.get()) break;
            if (!isPrime(primes[i])) {
                found = new AtomicBoolean(true);
                return true;
            }
        }
        return found.get();
    }

    AtomicBoolean found = new AtomicBoolean(false);

    public Boolean hasCompositeNumber(int[] primes) {
        Thread[] threads = new Thread[countOfThread];

        int cnt = primes.length / countOfThread + (primes.length % countOfThread == 0 ? 0 : 1);
        for (int i = 0; i < countOfThread; i++) {
            int start = i * cnt;
            int end = Math.min(start + cnt, primes.length);
            threads[i] = new Thread(() -> {
                if (!found.get()) check(primes, start, end);
            });
            if (found.get()) continue;
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        Boolean hihihaha = found.get();
        found = new AtomicBoolean(false);
        return hihihaha;
    }


}

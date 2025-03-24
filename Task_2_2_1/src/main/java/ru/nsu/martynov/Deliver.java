package ru.nsu.martynov;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Deliver class.
 */
public class Deliver {
    private int time;
    private int count;
    private int capacity;
    private AtomicBoolean ready;

    Deliver(int time, int capacity) {
        this.time = time;
        this.capacity = capacity;
        this.ready = new AtomicBoolean(true);
    }

    int getTime() {
        return time;
    }

    int getCapacity() {
        return capacity;
    }

    void setCount(int count) {
        this.count = count;
    }

    int getCount() {
        return count;
    }

    void setReady(boolean ready) {
        this.ready.set(ready);
    }

    boolean isReady() {
        return ready.get();
    }
}

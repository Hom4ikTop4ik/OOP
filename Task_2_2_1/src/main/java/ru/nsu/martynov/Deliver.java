package ru.nsu.martynov;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Deliver class.
 */
public class Deliver {
    private final int time;
    private int count;
    private final int capacity;
    private volatile boolean ready;

    Deliver(int time, int capacity) {
        this.time = time;
        this.capacity = capacity;
        this.ready = true;
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
        this.ready = ready;
    }

    boolean isReady() {
        return ready;
    }
}

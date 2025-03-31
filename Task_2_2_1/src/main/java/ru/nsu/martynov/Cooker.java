package ru.nsu.martynov;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cooker class.
 */
public class Cooker {
    private final int time;
    private volatile boolean ready;

    Cooker(int time) {
        this.time = time;
        ready = true;
    }

    int getTime() {
        return time;
    }

    int getLevel() {
        return 1;
    }

    void setReady(boolean ready) {
        this.ready= ready;
    }

    boolean isReady() {
        return ready;
    }
}

package ru.nsu.martynov;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cooker class.
 */
public class Cooker {
    private int time;
    private int level = 1; // for future updates, now is constant
    private AtomicBoolean ready;

    Cooker(int time) {
        this.time = time;
        this.level = 1;
        ready = new AtomicBoolean(true);
    }

    void setTime(int time) {
        this.time = time;
    }

    int getTime() {
        return time;
    }

    int getLevel() {
        return level;
    }

    void setReady(boolean ready) {
        this.ready.set(ready);
    }

    boolean isReady() {
        return ready.get();
    }
}

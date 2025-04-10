package ru.nsu.martynov;

/**
 * Cooker class.
 */
public class Cooker {
    private int time;
    private int level = 1; // for future updates, now is constant
    private volatile boolean ready;

    Cooker(int time) {
        this.time = time;
        this.level = 1;
        ready = true;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready= ready;
    }
}

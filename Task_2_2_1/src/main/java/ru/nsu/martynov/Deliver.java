package ru.nsu.martynov;

/**
 * Deliver class.
 */
public class Deliver {
    private int time;
    private int count;
    private int capacity;
    private volatile boolean ready;

    Deliver(int time, int capacity) {
        this.time = time;
        this.capacity = capacity;
        this.ready = true;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}

package ru.nsu.martynov;

/**
 * Storage class.
 */
public class Storage {
    private int count;
    private final int capacity;

    Storage(int capacity) {
        this.count = 0;
        this.capacity = capacity;
    }

    int getCapacity() {
        return this.capacity;
    }

    void setCount(int count) {
        this.count = count;
    }

    int getCount() {
        return this.count;
    }

    int push(int count) {
        if (this.count + count <= this.capacity) {
            this.count += count;
            return count;
        } else {
            int tmp = this.capacity - this.count;
            this.count = this.capacity;
            return tmp;
        }
    }

    int pop(int count) {
        if (count <= this.count) {
            this.count -= count;
            return count;
        } else {
            int tmp = this.count;
            this.count = 0;
            return tmp;
        }
    }
}

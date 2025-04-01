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

    int getCount() {
        return this.count;
    }

    void setCount(int count) {
        this.count = count;
    }

    /**
     * Пытаемся положить на склад.
     *
     * @param count — сколько нужно положить на склад.
     * @return сколько смогли положить на склад.
     */
    public int push(int count) {
        if (this.count + count <= this.capacity) {
            this.count += count;
            return count;
        } else {
            int tmp = this.capacity - this.count;
            this.count = this.capacity;
            return tmp;
        }
    }

    /**
     * Пытаемся взять со склада.
     *
     * @param count — сколько хотим забрать со склада.
     * @return сколько смогли забрать со склада.
     */
    public int pop(int count) {
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

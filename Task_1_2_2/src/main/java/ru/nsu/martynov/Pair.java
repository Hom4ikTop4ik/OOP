package ru.nsu.martynov;

/**
 * Class Pair for table.
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {
    K key;
    V value;

    /**
     * Pair constructor.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

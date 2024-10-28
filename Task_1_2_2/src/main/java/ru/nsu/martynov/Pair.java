package ru.nsu.martynov;

/**
 * Class Pair for table.
 *
 * @param <K> key type.
 * @param <V> value type.
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

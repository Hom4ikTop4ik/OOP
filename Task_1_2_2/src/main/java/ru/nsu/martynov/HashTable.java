package ru.nsu.martynov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static ru.nsu.martynov.HashMapConstants.HASH_COEF;
import static ru.nsu.martynov.HashMapConstants.HASH_START_VALUE;
import static ru.nsu.martynov.HashMapConstants.HASH_TABLE_LOAD_FACTOR;

/**
 * Class HashTable.
 *
 * @param <K> — key
 * @param <V> — value
 */
public class HashTable<K, V> implements Iterable<Pair<K, V>> {
    private LinkedList<Pair<K, V>>[] table;
    private int capacity;
    private int cnt;
    private int modificationCount;

    /**
     * HashTable constructor.
     */
    public HashTable() {
        this.table = new LinkedList[0];
        this.capacity = 0;
        this.cnt = 0;
        this.modificationCount = 0;
    }

    /**
     * Put Pair(Key, Value) in hashTable.
     * If pair exist update this.
     *
     * @param key — key.
     * @param value — value.
     */
    public void put(K key, V value) {
        if (capacity < 0) {
            capacity = 0;
        }

        if (cnt >= capacity * HASH_TABLE_LOAD_FACTOR) {
            resize();
        }

        // There is capacity > 0.

        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        } else {
            for (Pair<K, V> pair : table[index]) {
                if (pair.key.equals(key)) {
                    pair.value = value; // Update value, if key exists.
                    modificationCount++;
                    return;
                }
            }
        }

        table[index].add(new Pair<>(key, value));
        modificationCount++;
        cnt++;
    }

    /**
     * Remove the pair if it exists.
     *
     * @param key — key.
     */
    public void remove(K key) {
        int index = hash(key);
        if (table[index] != null) {
            for (Pair<K, V> pair : table[index]) {
                if (pair.key.equals(key)) {
                    table[index].remove(pair);
                    cnt--;
                    return;
                }
            }
        }
    }

    /**
     * Return pair if existed.
     * Otherwise, return null.
     *
     * @param key — key.
     * @return pair or null.
     */
    public V get(K key) {
        int index = hash(key);
        if (table[index] != null) {
            for (Pair<K, V> pair : table[index]) {
                if (pair.key.equals(key)) {
                    return pair.value;
                }
            }
        }
        return null; // if key isn't exist.
    }

    /**
     * Update or add pair into hashTable.
     *
     * @param key — key.
     * @param value — value.
     */
    public void update(K key, V value) {
        put(key, value); // Add or update pair(key, value)
    }

    /**
     * Check if the pair (key, value) exists.
     *
     * @param key — key.
     * @return true if exists, otherwise — false.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Hash code for the hash table to compare them.
     * It collects the hash codes of all keys and values,
     * and adds the size of the table.
     *
     * @return the calculated hash code.
     */
    @Override
    public int hashCode() {
        int hash = HASH_START_VALUE;
        for (LinkedList<Pair<K, V>> list : table) {
            if (list != null) {
                for (Pair<K, V> pair : list) {
                    int keyHash = (pair.key != null) ? pair.key.hashCode() : 0;
                    int valueHash = (pair.value != null) ? pair.value.hashCode() : 0;
                    hash = HASH_COEF * hash + keyHash; // Add hash(key)
                    hash = HASH_COEF * hash + valueHash; // Add hash(value)
                }
            }
        }
        hash = HASH_COEF * hash + cnt; // Add count of elems in table
        return hash;
    }

    /**
     * Compare this hash table with another.
     *
     * @param obj — other object to compare with.
     * @return true if equal, otherwise — false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.cnt != other.cnt) {
            return false;
        }

        for (LinkedList<Pair<K, V>> list : table) {
            if (list != null) {
                for (Pair<K, V> pair : list) {
                    V otherValue = (V) other.get(pair.key); // Сравниваем значение по ключу
                    if (otherValue == null || !pair.value.equals(otherValue)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Creates a string based on the hash table data.
     *
     * @return table contents in the form of key-value pairs (unordered).
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (LinkedList<Pair<K, V>> list : this.table) {
            if (list != null) {
                for (Pair<K, V> pair : list) {
                    stringBuilder.append(pair.key.toString());
                    stringBuilder.append(": ");
                    stringBuilder.append(pair.value.toString());
                    stringBuilder.append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Реализация итератора для HashTable.
     */
    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Hash func — index in table.
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    /**
     * Resize the hash table when load factor is exceeded.
     */
    private void resize() {
        // if start from 0, we will get 0 every because 2 * 0 = 0, so add 1.
        this.capacity = 1 + 2 * this.capacity;
        LinkedList<Pair<K, V>>[] newTable = new LinkedList[this.capacity];

        for (LinkedList<Pair<K, V>> list : table) {
            if (list != null) {
                for (Pair<K, V> pair : list) {
                    int newIndex = hash(pair.key);
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new LinkedList<>();
                    }
                    newTable[newIndex].add(pair);
                }
            }
        }

        table = newTable;
    }

    /**
     * Iterator for the hash table.
     */
    private class HashTableIterator implements Iterator<Pair<K, V>> {
        private int curIndexI;  // Current index in table
        private int curIndexJ;  // Current index in list
        private int iteratedCount;  // How many elements have already been iterated
        private final int expectedModificationCount;  // For modification checking

        /**
         * Constructor for the iterator.
         * Initializes indices and sets the expected modification count.
         */
        public HashTableIterator() {
            this.curIndexI = 0;
            this.curIndexJ = -1;
            this.iteratedCount = 0;
            this.expectedModificationCount = modificationCount;
        }

        /**
         * Checks for modifications in the hash table.
         *
         * @throws ConcurrentModificationException if the hash table was modified during iteration.
         */
        private void checkForModifications() {
            if (modificationCount != expectedModificationCount) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * Finds the next key-value pair in the hash table.
         *
         * @return the next key-value pair, or null if there are no more elements.
         */
        private Pair<K, V> findNext() {
            while (curIndexI < capacity) {
                if (table[curIndexI] != null && curIndexJ + 1 < table[curIndexI].size()) {
                    return table[curIndexI].get(++curIndexJ);
                }
                curIndexJ = -1;  // Переходим к следующему bucket
                curIndexI++;
            }
            return null;
        }

        /**
         * Checks if there are more elements to iterate over.
         *
         * @return true if there are more elements; false otherwise.
         */
        @Override
        public boolean hasNext() {
            checkForModifications();
            return iteratedCount < cnt;
        }

        /**
         * Returns the next key-value pair in the iteration.
         *
         * @return the next key-value pair.
         * @throws NoSuchElementException if there are no more elements to return.
         */
        @Override
        public Pair<K, V> next() {
            checkForModifications();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Pair<K, V> nextPair = findNext();
            iteratedCount++;
            return nextPair;
        }

        /**
         * Removes the last element returned by this iterator.
         *
         * @throws UnsupportedOperationException as remove is not supported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported.");
        }
    }
}

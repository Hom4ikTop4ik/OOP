package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("key1", 1);
        assertEquals(1, hashTable.get("key1"));
    }

    @Test
    void testUpdate() {
        hashTable.put("key1", 1);
        hashTable.put("key1", 2);
        assertEquals(2, hashTable.get("key1"));
    }

    @Test
    void testRemove() {
        hashTable.put("key1", 1);
        hashTable.remove("key1");
        assertNull(hashTable.get("key1"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("key1", 1);
        assertTrue(hashTable.containsKey("key1"));
        assertFalse(hashTable.containsKey("nonexistentKey"));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> otherHashTable = new HashTable<>();
        hashTable.put("key1", 1);
        otherHashTable.put("key1", 1);
        assertTrue(hashTable.equals(otherHashTable));

        otherHashTable.put("key2", 2);
        assertFalse(hashTable.equals(otherHashTable));
    }

    @Test
    void testIterator() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        int count = 0;

        for (Pair<String, Integer> pair : hashTable) {
            assertNotNull(pair);
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    void testIteratorConcurrentModification() {
        hashTable.put("key1", 1);
        Iterator<Pair<String, Integer>> iterator = hashTable.iterator();
        hashTable.put("key2", 2);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testToString() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        String result = hashTable.toString();
        assertTrue(result.contains("key1: 1"));
        assertTrue(result.contains("key2: 2"));
    }

    @Test
    void testHashCode() {
        HashTable<String, Integer> hashTable1 = new HashTable<>();
        HashTable<String, Integer> hashTable2 = new HashTable<>();

        // Добавим пары в первую таблицу
        hashTable1.put("key1", 1);
        hashTable1.put("key2", 2);

        // Добавим те же пары во вторую таблицу
        hashTable2.put("key1", 1);
        hashTable2.put("key2", 2);

        // Проверяем, что обе таблицы имеют одинаковый хэш-код
        assertEquals(hashTable1.hashCode(), hashTable2.hashCode());

        // Изменим одну из таблиц
        hashTable2.put("key3", 3);

        // Проверяем, что хэш-коды теперь различаются
        assertNotEquals(hashTable1.hashCode(), hashTable2.hashCode());

        // Проверим, что хэш-код для пустой таблицы равен 1 (или любому другому ожидаемому значению)
        HashTable<String, Integer> emptyHashTable = new HashTable<>();
        hashTable1.remove("key1");
        hashTable1.remove("key2");
        assertEquals(hashTable1.hashCode(), emptyHashTable.hashCode()); // Предполагая, что ваш hashCode возвращает 1 для пустой таблицы
    }

}

package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void SampleTest() {
        assertEquals(Main.sum(1, 2), 3);
        assertEquals(Main.sum(2, 3), 5);
        assertEquals(Main.sum(3, 3), 6);
    }
}
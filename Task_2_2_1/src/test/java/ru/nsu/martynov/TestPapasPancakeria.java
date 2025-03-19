package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPapasPancakeria {

    @Test
    void porTest() {
        assertTrue(PapasPancakeria.por(-5) == 1);
        assertTrue(PapasPancakeria.por(0) == 1);
        assertTrue(PapasPancakeria.por(1) == 1);
        assertTrue(PapasPancakeria.por(9) == 1);
        assertTrue(PapasPancakeria.por(10) == 2);
        assertTrue(PapasPancakeria.por(23) == 2);
        assertTrue(PapasPancakeria.por(123) == 3);
        assertTrue(PapasPancakeria.por(4567) == 4);
        assertTrue(PapasPancakeria.por(97531) == 5);
        assertTrue(PapasPancakeria.por(123546) == 6);
        assertTrue(PapasPancakeria.por(1_000_000_000) == 10);
    }
}
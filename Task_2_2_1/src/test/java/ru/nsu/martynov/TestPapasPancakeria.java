package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPapasPancakeria {

    @Test
    void porTest() {
        assertTrue(Printer.por(-5) == 1);
        assertTrue(Printer.por(0) == 1);
        assertTrue(Printer.por(1) == 1);
        assertTrue(Printer.por(9) == 1);
        assertTrue(Printer.por(10) == 2);
        assertTrue(Printer.por(23) == 2);
        assertTrue(Printer.por(123) == 3);
        assertTrue(Printer.por(4567) == 4);
        assertTrue(Printer.por(97531) == 5);
        assertTrue(Printer.por(123546) == 6);
        assertTrue(Printer.por(1_000_000_000) == 10);
    }

    @Test
    void ppTest() {
        PapasPancakeria pp = new PapasPancakeria("config.txt");
        pp.start();
        assertEquals(0, pp.getStorage().getCount());
    }
}

package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPapasPancakeria {

    @Test
    void ppTest() {
        PapasPancakeria pp = new PapasPancakeria("config.txt");
        pp.start();
        assertEquals(0, pp.getStorage().getCount());
    }
}

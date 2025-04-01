package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPapasPancakeria {

    @Test
    void ppTest() {
        PapasPancakeria pp = new PapasPancakeria("config.txt");
        pp.start();

        pp.setCookers(pp.getCookers());
        pp.setDelivers(pp.getDelivers());
        pp.setStorage(pp.getStorage());
        pp.setTimeDay(pp.getTimeDay());

        assertEquals(0, pp.getStorage().getCount());
    }

    @Test
    void storageTest() {
        Storage st = new Storage(20);
        assertEquals(20, st.getCapacity());
        st.setCount(5); // now 5/20
        assertEquals(5, st.getCount());

        assertEquals(5, st.push(5)); // now 10/20
        assertEquals(10, st.push(20)); // now 20/20

        assertEquals(20, st.pop(30)); // now 0/20
    }

    @Test
    void cookerTest() {
        Cooker c = new Cooker(5);

        c.setTime(4);
        assertEquals(4, c.getTime());
        c.setLevel(3);
        assertEquals(3, c.getLevel());
    }

    @Test
    void deliverTest() {
        Deliver d = new Deliver(5, 15);

        d.setTime(10);
        assertEquals(10, d.getTime());
        d.setCapacity(20);
        assertEquals(20, d.getCapacity());
    }
}

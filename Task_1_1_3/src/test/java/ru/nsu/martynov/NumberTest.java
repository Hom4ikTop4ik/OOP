package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void print1() {
        Number num = new Number(-5);
        assertEquals("-5.0", num.toString());
    }

    @Test
    void print2() {
        Number num = new Number(0);
        assertEquals("0.0", num.toString());
    }

    @Test
    void print3() {
        Number num = new Number(5);
        assertEquals("5.0", num.toString());
    }

    @Test
    void derivative1() {
        Number num = new Number(-5);
        assertEquals("0.0", num.derivative("x").toString());
    }

    @Test
    void derivative2() {
        Number num = new Number(0);
        assertEquals("0.0", num.derivative("abc").toString());
    }

    @Test
    void derivative3() {
        Number num = new Number(5);
        assertEquals("0.0", num.derivative("123").toString());
    }

    @Test
    void eval1() {
        Number num = new Number(-5);
        assertEquals(-5, num.eval("xyz"));
    }

    @Test
    void eval2() {
        Number num = new Number(0);
        assertEquals(0, num.eval("123456"));
    }

    @Test
    void eval3() {
        Number num = new Number(5);
        assertEquals(5, num.eval("x="));
    }

    @Test
    void eval4() {
        Number num = new Number(5);
        assertEquals(5, num.eval("y"));
    }
}

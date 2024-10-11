package ru.nsu.martynov;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        num.print();
        assertEquals("-5.0", outputStream.toString());
    }

    @Test
    void print2() {
        Number num = new Number(0);
        num.print();
        assertEquals("0.0", outputStream.toString());
    }

    @Test
    void print3() {
        Number num = new Number(5);
        num.print();
        assertEquals("5.0", outputStream.toString());
    }

    @Test
    void derivative1() {
        Number num = new Number(-5);
        num.derivative("x").print();
        assertEquals("0.0", outputStream.toString());
    }

    @Test
    void derivative2() {
        Number num = new Number(0);
        num.derivative("abc").print();
        assertEquals("0.0", outputStream.toString());
    }

    @Test
    void derivative3() {
        Number num = new Number(5);
        num.derivative("123").print();
        assertEquals("0.0", outputStream.toString());
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
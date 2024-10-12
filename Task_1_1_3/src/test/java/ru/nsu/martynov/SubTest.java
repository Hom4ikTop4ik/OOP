package ru.nsu.martynov;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printNums1() {
        Sub sub = new Sub(new Number(0), new Number(0));
        sub.print();

        String ans = "(0.0 - 0.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums2() {
        Sub sub = new Sub(new Number(1), new Number(2));
        sub.print();

        String ans = "(1.0 - 2.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums3() {
        Sub sub = new Sub(new Number(-3), new Number(4));
        sub.print();

        String ans = "(-3.0 - 4.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums4() {
        Sub sub = new Sub(new Number(5), new Number(-6));
        sub.print();

        String ans = "(5.0 - -6.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums5() {
        Sub sub = new Sub(new Number(-7), new Number(-8));
        sub.print();

        String ans = "(-7.0 - -8.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNumsVars1() {
        Sub sub = new Sub(new Number(0), new Variable("x"));
        sub.print();

        String ans = "(0.0 - x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar1() {
        Sub sub = new Sub(new Variable("x"), new Variable("x"));
        sub.print();

        String ans = "(x - x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar2() {
        Sub sub = new Sub(new Variable("xyz"), new Variable("abc"));
        sub.print();

        String ans = "(xyz - abc)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar3() {
        Sub sub = new Sub(new Variable("hihi"), new Variable("a"));
        sub.print();

        String ans = "(hihi - a)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar4() {
        Sub sub = new Sub(new Sub(new Variable("a"), new Variable("b")), new Number(123));
        sub.print();

        String ans = "((a - b) - 123.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void derivative1() {
        Sub sub = new Sub(new Number(2), new Number(3));
        sub.derivative("x").print();
        assertEquals("(0.0 - 0.0)", outputStream.toString());
    }

    @Test
    void derivative2() {
        Sub sub = new Sub(new Variable("x"), new Number(3));
        sub.derivative("x").print();
        assertEquals("(1.0 - 0.0)", outputStream.toString());
    }

    @Test
    void derivative3() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        sub.derivative("x").print();
        assertEquals("(1.0 - 0.0)", outputStream.toString());
    }

    @Test
    void evalGood1() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(1.0, sub.eval("x = 5; y= 4"));
    }

    @Test
    void evalGood2() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        double res = sub.eval("x = .24; y=3.49");
        double rounded = Math.round(res * 1000);
        rounded /= 1000;
        assertEquals(-3.25, rounded);
    }

    @Test
    void evalBad1() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            sub.eval("x = 1 a= 4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
        assertTrue(flag);
    }

    @Test
    void evalBad2() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            sub.eval("x = 1; a=4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'y' not found", outputStream.toString());
        assertTrue(flag);
    }
}
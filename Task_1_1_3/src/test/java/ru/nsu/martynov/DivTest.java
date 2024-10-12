package ru.nsu.martynov;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DivTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printNums1() {
        Div div = new Div(new Number(0), new Number(0));
        div.print();

        String ans = "(0.0 / 0.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums2() {
        Div div = new Div(new Number(1), new Number(2));
        div.print();

        String ans = "(1.0 / 2.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums3() {
        Div div = new Div(new Number(-3), new Number(4));
        div.print();

        String ans = "(-3.0 / 4.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums4() {
        Div div = new Div(new Number(5), new Number(-6));
        div.print();

        String ans = "(5.0 / -6.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums5() {
        Div div = new Div(new Number(-7), new Number(-8));
        div.print();

        String ans = "(-7.0 / -8.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNumsVars() {
        Div div = new Div(new Number(0), new Variable("aboba"));
        div.print();

        String ans = "(0.0 / aboba)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar1() {
        Div div = new Div(new Variable("x"), new Variable("x"));
        div.print();

        String ans = "(x / x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar2() {
        Div div = new Div(new Variable("xyz"), new Variable("abc"));
        div.print();

        String ans = "(xyz / abc)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar3() {
        Div div = new Div(new Variable("hihi"), new Variable("a"));
        div.print();

        String ans = "(hihi / a)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void derivative1() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        div.derivative("x").print();

        String ans = "(((1.0 * y) - (x * 0.0)) / (y * y))";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void evalGood1() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        assertEquals(120, div.eval("x = 30; y= 0.25"));
    }

    @Test
    void evalGood2() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        double res = div.eval("x = 33; y=8");
        double rounded = Math.round(res * 1000);
        rounded /= 1000;
        assertEquals(4.125, rounded);
    }

    @Test
    void evalBad() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            div.eval("x = 1 a= 4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
        assertTrue(flag);
    }
}
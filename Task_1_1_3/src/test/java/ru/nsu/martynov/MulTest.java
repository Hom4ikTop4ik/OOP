package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MulTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printNums1() {
        Mul mul = new Mul(new Number(0), new Number(0));
        mul.print();

        String ans = "(0.0 * 0.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums2() {
        Mul mul = new Mul(new Number(1), new Number(2));
        mul.print();

        String ans = "(1.0 * 2.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums3() {
        Mul mul = new Mul(new Number(-3), new Number(4));
        mul.print();

        String ans = "(-3.0 * 4.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums4() {
        Mul mul = new Mul(new Number(5), new Number(-6));
        mul.print();

        String ans = "(5.0 * -6.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums5() {
        Mul mul = new Mul(new Number(-7), new Number(-8));
        mul.print();

        String ans = "(-7.0 * -8.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNumsVars() {
        Mul mul = new Mul(new Number(0), new Variable("aboba"));
        mul.print();

        String ans = "(0.0 * aboba)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar1() {
        Mul mul = new Mul(new Variable("x"), new Variable("x"));
        mul.print();

        String ans = "(x * x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar2() {
        Mul mul = new Mul(new Variable("xyz"), new Variable("abc"));
        mul.print();

        String ans = "(xyz * abc)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar3() {
        Mul mul = new Mul(new Variable("hihi"), new Variable("a"));
        mul.print();

        String ans = "(hihi * a)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void derivative1() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        mul.derivative("x").print();

        String ans = "((1.0 * y) + (x * 0.0))";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void evalGood1() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(7.5, mul.eval("x = 0.25; y= 30"));
    }

    @Test
    void evalGood2() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        double res = mul.eval("x = .4; y=15");
        double rounded = Math.round(res * 1000);
        rounded /= 1000;
        assertEquals(6, rounded);
    }

    @Test
    void evalBad() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            mul.eval("x = 1 a= 4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
        assertTrue(flag);
    }
}
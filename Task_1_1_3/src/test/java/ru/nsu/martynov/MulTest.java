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

        String ans = "(0.0 * 0.0)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printNums2() {
        Mul mul = new Mul(new Number(1), new Number(2));

        String ans = "(1.0 * 2.0)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printNums3() {
        Mul mul = new Mul(new Number(-3), new Number(4));

        String ans = "(-3.0 * 4.0)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printNums4() {
        Mul mul = new Mul(new Number(5), new Number(-6));

        String ans = "(5.0 * -6.0)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printNums5() {
        Mul mul = new Mul(new Number(-7), new Number(-8));

        String ans = "(-7.0 * -8.0)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printNumsVars() {
        Mul mul = new Mul(new Number(0), new Variable("aboba"));

        String ans = "(0.0 * aboba)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printVar1() {
        Mul mul = new Mul(new Variable("x"), new Variable("x"));

        String ans = "(x * x)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printVar2() {
        Mul mul = new Mul(new Variable("xyz"), new Variable("abc"));

        String ans = "(xyz * abc)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void printVar3() {
        Mul mul = new Mul(new Variable("hihi"), new Variable("a"));

        String ans = "(hihi * a)";
        assertEquals(ans, mul.toString());
    }

    @Test
    void derivative1() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));

        String ans = "((1.0 * y) + (x * 0.0))";
        assertEquals(ans, mul.derivative("x").toString());
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

    @Test
    void simplifyNumbers() {
        Mul mul = new Mul(new Number(2), new Number(6));
        Expression sim = mul.simplify();
        Number num = new Number(12);
        assertEquals(sim, num);
    }

    @Test
    void simplifyZero() {
        Mul mul = new Mul(new Number(0), new Number(6));
        Expression sim = mul.simplify();
        Number num = new Number(0);
        assertEquals(sim, num);

        Mul mul2 = new Mul(new Number(2), new Number(0));
        Expression sim2 = mul2.simplify();
        Number num2 = new Number(0);
        assertEquals(sim2, num2);
    }

    @Test
    void simplifyOne() {
        Mul mul = new Mul(new Number(1), new Number(6));
        Expression sim = mul.simplify();
        Number num = new Number(6);
        assertEquals(sim, num);

        Mul mul2 = new Mul(new Number(2), new Number(1));
        Expression sim2 = mul2.simplify();
        Number num2 = new Number(2);
        assertEquals(sim2, num2);
    }
}

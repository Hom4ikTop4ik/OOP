package ru.nsu.martynov;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printNums1() {
        Add add = new Add(new Number(0), new Number(0));
        add.print();

        String ans = "(0.0 + 0.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums2() {
        Add add = new Add(new Number(1), new Number(2));
        add.print();

        String ans = "(1.0 + 2.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums3() {
        Add add = new Add(new Number(-3), new Number(4));
        add.print();

        String ans = "(-3.0 + 4.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums4() {
        Add add = new Add(new Number(5), new Number(-6));
        add.print();

        String ans = "(5.0 + -6.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNums5() {
        Add add = new Add(new Number(-7), new Number(-8));
        add.print();

        String ans = "(-7.0 + -8.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printNumsVars1() {
        Add add = new Add(new Number(0), new Variable("x"));
        add.print();

        String ans = "(0.0 + x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar1() {
        Add add = new Add(new Variable("x"), new Variable("x"));
        add.print();

        String ans = "(x + x)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar2() {
        Add add = new Add(new Variable("xyz"), new Variable("abc"));
        add.print();

        String ans = "(xyz + abc)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar3() {
        Add add = new Add(new Variable("hihi"), new Variable("a"));
        add.print();

        String ans = "(hihi + a)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void printVar4() {
        Add add = new Add(new Add(new Variable("a"), new Variable("b")), new Number(123));
        add.print();

        String ans = "((a + b) + 123.0)";
        assertEquals(ans, outputStream.toString());
    }

    @Test
    void derivative1() {
        Add add = new Add(new Number(2), new Number(3));
        add.derivative("x").print();
        assertEquals("(0.0 + 0.0)", outputStream.toString());
    }

    @Test
    void derivative2() {
        Add add = new Add(new Variable("x"), new Number(3));
        add.derivative("x").print();
        assertEquals("(1.0 + 0.0)", outputStream.toString());
    }

    @Test
    void derivative3() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        add.derivative("x").print();
        assertEquals("(1.0 + 0.0)", outputStream.toString());
    }

    @Test
    void evalGood1() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        assertEquals(9.0, add.eval("x = 5; y= 4"));
    }

    @Test
    void evalGood2() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        double res = add.eval("x = .353; y=4.397");
        double rounded = Math.round(res * 1000);
        rounded /= 1000;
        assertEquals(4.75, rounded);
    }

    @Test
    void evalBad1() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            add.eval("x = 1 a= 4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
        assertTrue(flag);
    }

    @Test
    void evalBad2() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        boolean flag = false;
        try {
            add.eval("x = 1; a=4");
        } catch (IllegalArgumentException e) {
            flag = true;
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'y' not found", outputStream.toString());
        assertTrue(flag);
    }
}
package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printEmpty() {
        try {
            Variable var = new Variable("");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Variable name is empty", outputStream.toString());
    }

    @Test
    void printBadName1() {
        try {
            Variable var = new Variable("abc123");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("'abc123' is not valid name — use letters", outputStream.toString());
    }

    @Test
    void printBadName2() {
        try {
            Variable var = new Variable("456");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("'456' is not valid name — use letters", outputStream.toString());
    }

    @Test
    void printBadName3() {
        try {
            Variable var = new Variable("xyz+-*/!@#$%^&*()");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        String print = "'xyz+-*/!@#$%^&*()' is not valid name — use letters";
        assertEquals(print, outputStream.toString());
    }

    @Test
    void printGood1() {
        Variable var = new Variable("x");

        assertEquals("x", var.toString());
    }

    @Test
    void printGood2() {
        Variable var = new Variable("abc");

        assertEquals("abc", var.toString());
    }


    @Test
    void derivativeGood0() {
        Variable var = new Variable("x");

        assertEquals("0.0", var.derivative("y").toString());
    }

    @Test
    void derivativeGood1() {
        Variable var = new Variable("abc");

        assertEquals("1.0", var.derivative("abc").toString());
    }


    @Test
    void evalBad1() {
        try {
            Variable var = new Variable("x");
            System.out.print(var.eval("y = 5"));
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
    }

    @Test
    void evalBad2() {
        try {
            Variable var = new Variable("x");
            System.out.print(var.eval("xx = 5"));
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
    }

    @Test
    void evalBad3() {
        try {
            Variable var = new Variable("x");
            System.out.print(var.eval("x abc 5"));
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
    }

    @Test
    void evalBad4() {
        try {
            Variable var = new Variable("x");
            System.out.print(var.eval("x = abc"));
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'x' not found", outputStream.toString());
    }

    @Test
    void evalGood() {
        Variable var = new Variable("HahA");
        System.out.print(var.eval("HahA = 5.75"));

        assertEquals("5.75", outputStream.toString());
    }
}

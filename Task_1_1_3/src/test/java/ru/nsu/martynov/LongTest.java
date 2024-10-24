package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printNums() {
        // (1 + 5*3 / 2) - 3.5
        Sub exp = new Sub(
                new Add(
                        new Number(1),
                        new Div(
                                new Mul(
                                        new Number(5),
                                        new Number(3)),
                                new Number(2))),
                new Number(3.5));

        String ans = "((1.0 + ((5.0 * 3.0) / 2.0)) - 3.5)";
        assertEquals(ans, exp.toString());
    }

    @Test
    void printNumsVars() {
        // (aboba - (1 + (x / y))) * apple
        Mul exp = new Mul(
                new Sub(
                        new Variable("aboba"),
                        new Add(
                                new Number(1),
                                new Div(
                                        new Variable("x"),
                                        new Variable("y")
                                )
                        )
                ),
                new Variable("apple")
        );

        String ans = "((aboba - (1.0 + (x / y))) * apple)";
        assertEquals(ans, exp.toString());
    }

    @Test
    void derivative1() {
        // (1 + 5*3 / 2) - 3.5
        Sub exp = new Sub(
                new Add(
                        new Number(1),
                        new Div(
                                new Mul(
                                        new Number(5),
                                        new Number(3)),
                                new Number(2))),
                new Number(3.5));
        String print = "((0.0 + (((((0.0 * 3.0) + (5.0 * 0.0)) * 2.0) "
                        + "- ((5.0 * 3.0) * 0.0)) / (2.0 * 2.0))) - 0.0)";
        assertEquals(print,  exp.derivative("x").toString());
    }

    @Test
    void evalGood1() {
        // (aboba - (1 + (x / y))) * apple
        // ( 3.33 - (1 + (5 / 4))) * 0 = 0
        Mul exp = new Mul(
                new Sub(
                        new Variable("aboba"),
                        new Add(
                                new Number(1),
                                new Div(
                                        new Variable("x"),
                                        new Variable("y")
                                )
                        )
                ),
                new Variable("apple")
        );
        assertEquals(0, exp.eval("aboba = 3.33; x = 5; apple=0.0; y= 4"));
    }

    @Test
    void evalEmpty() {
        Mul exp = new Mul(
                new Sub(
                        new Variable("aboba"),
                        new Add(
                                new Number(1),
                                new Div(
                                        new Variable("x"),
                                        new Variable("y")
                                )
                        )
                ),
                new Variable("apple")
        );
        try {
            exp.eval("");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'aboba' not found", outputStream.toString());
    }

    @Test
    void evalEmptyVar() {
        Mul exp = new Mul(
                new Sub(
                        new Variable("aboba"),
                        new Add(
                                new Number(1),
                                new Div(
                                        new Variable("x"),
                                        new Variable("y")
                                )
                        )
                ),
                new Variable("apple")
        );
        try {
            exp.eval("aboba=1;;;x=5;=3");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'y' not found", outputStream.toString());
    }

    @Test
    void evalEmptyExpression() {
        Mul exp = new Mul(
                new Sub(
                        new Variable("aboba"),
                        new Add(
                                new Number(1),
                                new Div(
                                        new Variable("x"),
                                        new Variable("y")
                                )
                        )
                ),
                new Variable("apple")
        );
        try {
            exp.eval("");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        assertEquals("Variable 'aboba' not found", outputStream.toString());
    }
}
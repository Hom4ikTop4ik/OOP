package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void parserTestGood1() {
        String exp = "((3+(2.5*x)) / (y - 7))";
        String print = "((3.0 + (2.5 * x)) / (y - 7.0))";
        Expression expression = Expression.parseString(exp);
        expression.print();

        assertEquals(print, outputStream.toString());
    }

    @Test
    void parserTestBad1() {
        String exp = "1..2185.21852";
        try {
            Expression.parseString(exp).print();
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Number must contain no more than one dot", outputStream.toString());
    }

    @Test
    void parserTestBad2() {
        String exp = "1.2YES5";
        try {
            Expression.parseString(exp).print();
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Should be operator, but it isn't", outputStream.toString());

        setUp();
        exp = "15Apples";
        try {
            Expression.parseString(exp).print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void parserTestEmpty() {
        String exp = "";
        try {
            Expression.parseString(exp).print();
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Expression can't be empty", outputStream.toString());
    }

    @Test
    void parserTestExtraBracket() {
        String exp = "(3+(2.5*x)))";
        String print = "(3.0 + (2.5 * x))";

        try {
            Expression expression = Expression.parseString(exp);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Should be operator, but it isn't", outputStream.toString());
    }

    @Test
    void parserTestEmptyVarOrNumber() {
        String exp = "(3+($*x)))";
        String print = "(3.0 + (2.5 * x))";
        try {
            Expression expression = Expression.parseString(exp);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("There isn't number or variable", outputStream.toString());
    }

    @Test
    void parserTestEmptyVarOrNumber2() {
        String exp = "(3+(2.5*&)))";
        String print = "(3.0 + (2.5 * x))";
        try {
            Expression expression = Expression.parseString(exp);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("There isn't number or variable", outputStream.toString());
    }


}
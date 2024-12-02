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

        assertEquals(print, expression.toString());
    }

    @Test
    void parserTestBad1() {
        String exp = "1..2185.21852";
        try {
            Expression.parseString(exp).toString();
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Number must contain no more than one dot", outputStream.toString());
    }

    @Test
    void parserTestBad2() {
        String exp = "1+1.2*5YES5";
        String ans = Expression.parseString(exp).toString();
        assertEquals("(1.0 + (1.2 * 5.0))", ans);

        setUp();
        exp = "15Apples";
        ans = Expression.parseString(exp).toString();
        assertEquals("15.0", ans);
    }

    @Test
    void parserTestEmpty() {
        String exp = "";
        try {
            Expression.parseString(exp).toString();
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        assertEquals("Expression can't be empty", outputStream.toString());
    }

    @Test
    void parserTestExtraBracket() {
        String exp = "(3+(2.5*x)))";
        String print = "(3.0 + (2.5 * x))";

        String a = Expression.parseString(exp).toString();
        System.out.print(a);

        assertEquals(print, outputStream.toString());
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

        assertEquals("Expected number or variable", outputStream.toString());
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

        assertEquals("Expected number or variable", outputStream.toString());
    }

    @Test
    void parserTestMany() {
        String exp = "1+2+3+4+5+6+a*b*c*d/e/f-xyz";
        String print = "(((((((1.0 + 2.0) + 3.0) + 4.0) + 5.0) + 6.0) + "
                     + "(((((a * b) * c) * d) / e) / f)) - xyz)";

        String a = Expression.parseString(exp).toString();
        System.out.print(a);

        assertEquals(print, outputStream.toString());
    }
}

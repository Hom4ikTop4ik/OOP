package ru.nsu.martynov;

import org.apache.commons.lang3.tuple.Pair;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for expressions.
 */
public interface Expression {
    /**
     * Takes the derivative of one variable and returns it as an expression.
     *
     * @param x — variable for derivative.
     * @return — new derived expression.
     */
     Expression derivative(String x);

    /**
     * Substitutes the values of variables and evaluates the expression.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return — result after substitution of values.
     */
    Double evalMap(Map<String, Double> vars);

    /**
     * Evaluates the expression using a string representation of variable assignments.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return — result after substitution of values.
     */
    Double eval(String vars);
}

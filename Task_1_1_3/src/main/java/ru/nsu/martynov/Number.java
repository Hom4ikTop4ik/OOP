package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents a constant number in the expression tree.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   and evaluate its value.
 */
public class Number extends AbstractExpression {
    private final double constNum;

    /**
     * Constructor.
     *
     * @param value — const value.
     */
    public Number(double value) {
        this.constNum = value;
    }

    /**
     * Convert const number to String.
     */
    @Override
    public String toString() {
        return Double.toString(this.constNum);
    }

    /**
     * Take the derivative constant.
     *
     * @param var — variable for derivative.
     * @return 0 because (const)' = 0.
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * Evaluate const.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return double constant because const doesn't depend on a variable.
     */
    @Override
    public Double evalMap(Map<String, Double> vars) {
        return this.constNum;
    }

    @Override
    public Double eval(String vars) {
        Map<String, Double> map = parse(vars);
        return evalMap(map);
    }

    @Override
    public Expression simplify() {
        return this; // Число уже упрощено
    }
}

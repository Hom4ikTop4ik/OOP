package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents a constant number in the expression tree.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   and evaluate its value.
 */
public class Number extends Expression {
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
     * Print const number.
     */
    @Override
    public void print() {
        System.out.print(this.constNum);
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
}

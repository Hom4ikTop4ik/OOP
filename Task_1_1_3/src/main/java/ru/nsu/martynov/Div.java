package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents the division of two mathematical expressions.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   evaluate its value.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * (Exp / Exp)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + " / " + this.right.toString() + ")";
    }

    /**
     * Derivative of the mul left and right.
     * x'*y - x*y'
     * (x / y)' = -----------
     * y^2
     *
     * @param var — variable for derivative.
     * @return new Expression: (left / right)'.
     */
    @Override
    public Expression derivative(String var) {
        return new Div(
                // x'*y - x*y'
                new Sub(
                        new Mul(this.left.derivative(var), this.right),
                        new Mul(this.left, this.right.derivative(var))
                ),
                // y^2
                new Mul(this.right, this.right)
        );
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        return this.left.evalMap(vars) / this.right.evalMap(vars);
    }

    @Override
    public Double eval(String vars) {
        Map<String, Double> map = parse(vars);
        return evalMap(map);
    }

}

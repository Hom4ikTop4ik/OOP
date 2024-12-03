package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents the division of two mathematical expressions.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   evaluate its value.
 */
public class Div extends AbstractExpression {
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

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        // Если оба выражения — числа, вычисляем результат
        if (l instanceof Number && r instanceof Number) {
            return new Number(((Number) l).eval("") / ((Number) r).eval(""));
        }

        // Если числитель 0, результат — 0
        if (l instanceof Number && ((Number) l).eval("") == 0) {
            return new Number(0);
        }

        // Если знаменатель 1, результат — числитель
        if (r instanceof Number && ((Number) r).eval("") == 1) {
            return l;
        }

        return new Div(l, r); // Вернуть упрощённое деление
    }
}

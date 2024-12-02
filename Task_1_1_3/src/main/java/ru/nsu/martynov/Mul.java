package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents a multiplication expression in the expression tree.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   and evaluate its value.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * (Exp * Exp)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + " * " + this.right.toString() + ")";
    }

    /**
     * Derivative of the mul left and right.
     * (x * y)' = x'*y + x*y'
     *
     * @param var — variable for derivative.
     * @return new Expression: (left + right)' = (left' + right').
     */
    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(this.left.derivative(var), this.right),
                new Mul(this.left, this.right.derivative(var))
        );
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        return this.left.evalMap(vars) * this.right.evalMap(vars);
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
            return new Number(((Number) l).eval("") * ((Number) r).eval(""));
        }

        // Если одно из выражений — 0, результат — 0
        if ((l instanceof Number && ((Number) l).eval("") == 0) ||
                (r instanceof Number && ((Number) r).eval("") == 0)) {
            return new Number(0);
        }

        // Если одно из выражений — 1, результат — другой множитель
        if (l instanceof Number && ((Number) l).eval("") == 1) {
            return r;
        }
        if (r instanceof Number && ((Number) r).eval("") == 1) {
            return l;
        }

        return new Mul(l, r); // Вернуть упрощённое умножение
    }
}

package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents a subtraction expression in the expression tree.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   and evaluate its value.
 */
public class Sub extends AbstractExpression {
    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * (Exp - Exp)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + " - " + this.right.toString() + ")";
    }

    /**
     * Derivative of the difference left and right.
     *
     * @param var — variable for derivative.
     * @return new Expression: (left - right)' = (left' - right').
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(this.left.derivative(var), this.right.derivative(var));
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        return this.left.evalMap(vars) - this.right.evalMap(vars);
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
            return new Number(((Number) l).eval("") - ((Number) r).eval(""));
        }

        // Если выражения одинаковы, результат — 0
        if (l.toString().equals(r.toString())) {
            return new Number(0);
        }

        return new Sub(l, r); // Вернуть упрощённое вычитание
    }
}

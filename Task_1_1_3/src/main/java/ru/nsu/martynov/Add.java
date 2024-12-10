package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents the addition of two mathematical expressions.
 * This class extends Expression class and implements methods:
 *   to print the expression;
 *   compute its derivative;
 *   evaluate its value.
 */
public class Add extends AbstractExpression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructor.
     *
     * @param left  — left term.
     * @param right — right term.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * (Exp + Exp)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + " + " + this.right.toString() + ")";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(this.left.derivative(var), this.right.derivative(var));
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        return this.left.evalMap(vars) + this.right.evalMap(vars);
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
            return new Number(((Number) l).eval("") + ((Number) r).eval(""));
        } else {
            return new Add(l, r); // Вернуть упрощённое сложение
        }
    }
}

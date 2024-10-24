package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents the addition of two mathematical expressions.
 * This class extends Expression class and implements methods:
 *   to print the expression;
 *   compute its derivative;
 *   evaluate its value.
 */
public class Add extends Expression {
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

        // Вынести print в отдельный класс Printer или ExpressionPrinter
//        iif (left instanceof Add) {
//            printAdd((Add)left);
//        }
    }

    /**
     * (Exp + Exp)
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + " + " + this.right.toString() + ")";
    }

    /**
     * Derivative of the sum left and right.
     *
     * @param var — variable for derivative.
     * @return new Expression: (left + right)' = (left' + right').
     */
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
}

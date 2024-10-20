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
     * Print '(', left term, '*', right term and ')'.
     */
    @Override
    public void print() {
        System.out.print("(");
        this.left.print();
        System.out.print(" * ");
        this.right.print();
        System.out.print(")");
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

}

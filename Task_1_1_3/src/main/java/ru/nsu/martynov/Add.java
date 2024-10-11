package ru.nsu.martynov;

import java.util.Map;

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
    }

    /**
     * Print '(', left term, '+', right term and ')'.
     */
    @Override
    public void print() {
        System.out.print("(");
        this.left.print();
        System.out.print(" + ");
        this.right.print();
        System.out.print(")");
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

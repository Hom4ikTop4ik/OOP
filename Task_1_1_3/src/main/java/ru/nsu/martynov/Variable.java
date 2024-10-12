package ru.nsu.martynov;

import java.util.Map;

public class Variable extends Expression {
    private final String variable;

    /**
     * Constructor.
     *
     * @param variable — variable in string.
     */
    public Variable(String variable) {
        for (Character c : variable.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                String print = "'" + variable + "' is not valid name — use letters";
                throw new IllegalArgumentException(print);
            }
        }

        if (variable.isEmpty()) {
            throw new IllegalArgumentException("Variable is empty");
        }

        this.variable = variable;
    }

    /**
     * Print string with variable.
     */
    @Override
    public void print() {
        System.out.print(this.variable);
    }

    /**
     * Take the derivative variable.
     *
     * @param var — variable for derivative.
     * @return 0 if variable isn't var, 1 otherwise.
     */
    @Override
    public Expression derivative(String var) {
        if (var.equals(this.variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        Double value = vars.get(this.variable);
        if (value == null) {
            throw new IllegalArgumentException("Variable '" + this.variable + "' not found");
        }
        return value;
    }

    @Override
    public Double eval(String vars) {
        Map<String, Double> map = parse(vars);
        return evalMap(map);
    }
}

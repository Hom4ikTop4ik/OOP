package ru.nsu.martynov;

import java.util.Map;

/**
 * Represents a variable in the expression tree.
 * This class extends Expression class and provides methods:
 *   to print the expression;
 *   compute its derivative;
 *   and evaluate its value.
 */
public class Variable extends Expression {
    final String name;

    /**
     * Constructor.
     *
     * @param variableName — variableName in string.
     */
    public Variable(String variableName) {
        for (Character c : variableName.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                throw new IllegalArgumentException(
                        "'" + variableName + "' is not valid name — use letters");
            }
        }

        if (variableName.isEmpty()) {
            throw new IllegalArgumentException("Variable name is empty");
        }

        this.name = variableName;
    }

    /**
     * Print string with variable.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Take the derivative variable.
     *
     * @param var — variable for derivative.
     * @return 0 if variable isn't var, 1 otherwise.
     */
    @Override
    public Expression derivative(String var) {
        if (var.equals(this.name)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public Double evalMap(Map<String, Double> vars) {
        Double value = vars.get(this.name);
        if (value == null) {
            throw new IllegalArgumentException("Variable '" + this.name + "' not found");
        }
        return value;
    }

    @Override
    public Double eval(String vars) {
        Map<String, Double> map = parse(vars);
        return evalMap(map);
    }
}

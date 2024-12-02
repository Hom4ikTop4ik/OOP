package ru.nsu.martynov;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for expressions.
 */
public abstract class Expression {
    /**
     * Print expression.
     */
    public abstract String toString();

    /**
     * Takes the derivative of one variable and returns it as an expression.
     *
     * @param x — variable for derivative.
     * @return — new derived expression.
     */
    abstract Expression derivative(String x);

    /**
     * Substitutes the values of variables and evaluates the expression.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return — result after substitution of values.
     */
    abstract Double evalMap(Map<String, Double> vars);

    /**
     * Evaluates the expression using a string representation of variable assignments.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return — result after substitution of values.
     */
    abstract Double eval(String vars);

    private static String doubleFromString(String str) {
        int cnt = 0;
        int dots = 0;
        for (Character c : str.toCharArray()) {
            if (dots > 1) {
                throw new IllegalArgumentException("Number must contain no more than one dot");
            }

            if (isDigit(c)) {
                cnt++;
            } else if (c == '.') {
                dots++;
                cnt++;
            } else {
                break;
            }
        }

        return str.substring(0, cnt);
    }

    private static String varFromString(String str) {
        int cnt = 0;
        for (Character c : str.toCharArray()) {
            if (isAlphabetic(c)) {
                cnt++;
            } else {
                break;
            }
        }

        return str.substring(0, cnt);
    }

    /**
     * Parse string of variable assignments into a map.
     *
     * @param varStr — the string containing variable assignments.
     * @return — map of variable names and their corresponding values.
     */
    public Map<String, Double> parse(String varStr) {
        if (varStr.isEmpty()) {
            return new HashMap<>();
        }

        String tmp = varStr.replaceAll(" ", "");
        String[] vars = tmp.split(";");
        Map<String, Double> map = new HashMap<>();

        for (String var : vars) {
            if (var.isEmpty()) {
                continue;
            }

            String[] tmpPair = var.split("=");
            if (tmpPair.length <= 1 || tmpPair[0].isEmpty()) {
                continue;
            }
            // I want to add support double numbers without a leading 0, for example: '.4' -> '0.4'
            if (tmpPair[1].charAt(0) == '.') {
                tmpPair[1] = '0' + tmpPair[1];
            }
            try {
                double num = Double.parseDouble(tmpPair[1]);
                map.put(tmpPair[0], num);
            } catch (NumberFormatException e) {
                System.err.println("Number format exception: " + tmpPair[1]);
            }
        }

        return map;
    }

    /**
     * Simple pair class to hold two related objects.
     *
     * @param <L> — type of the left element.
     * @param <R> — type of the right element.
     */
    public static class Pair<L, R> {
        public final L left;
        public final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

    public abstract Expression simplify();

    // Разбирает сложение и вычитание
    private static Pair<Expression, Integer> parseAdditionOrSubtraction(String expString) {
        Pair<Expression, Integer> pair = parseMultiplicationOrDivision(expString);
        Expression e1 = pair.left;
        expString = expString.substring(pair.right);
        int skip = pair.right;

        while (!expString.isEmpty()) {
            char op = expString.charAt(0);

            if (!"+-".contains(Character.toString(op))) {
                break; // Если символ не оператор умножения или деления, завершаем цикл
            }

            expString = expString.substring(1); // Пропускаем операцию
            skip++;

            // Проверка: после оператора должно быть число, переменная или '('
            if (expString.isEmpty()
                || (!Character.isDigit(expString.charAt(0))
                    && expString.charAt(0) != '('
                    && !Character.isLetter(expString.charAt(0)))) {
                throw new IllegalArgumentException("Expected number or variable");
            }

            Pair<Expression, Integer> pair2 = parseMultiplicationOrDivision(expString);
            Expression e2 = pair2.left;
            expString = expString.substring(pair2.right);
            skip += pair2.right;

            if (op == '+') {
                e1 = new Add(e1, e2);
            } else if (op == '-') {
                e1 = new Sub(e1, e2);
            }
        }

        return new Pair<>(e1, skip);
    }

    // Разбирает умножение и деление
    private static Pair<Expression, Integer> parseMultiplicationOrDivision(String expString) {
        Pair<Expression, Integer> pair = parseAtom(expString);
        Expression e1 = pair.left;
        expString = expString.substring(pair.right);
        int skip = pair.right;

        while (!expString.isEmpty()) {
            char op = expString.charAt(0);

            if (!"*/".contains(Character.toString(op))) {
                break; // Если символ не оператор умножения или деления, завершаем цикл
            }

            expString = expString.substring(1); // Пропускаем операцию
            skip++;

            // Проверка: после оператора должно быть число, переменная или '('
            if (expString.isEmpty()
                || (!Character.isDigit(expString.charAt(0))
                    && expString.charAt(0) != '('
                    && !Character.isLetter(expString.charAt(0)))) {
                throw new IllegalArgumentException("Expected number or variable");
            }

            Pair<Expression, Integer> pair2 = parseAtom(expString);
            Expression e2 = pair2.left;
            expString = expString.substring(pair2.right);
            skip += pair2.right;

            if (op == '*') {
                e1 = new Mul(e1, e2);
            } else if (op == '/') {
                e1 = new Div(e1, e2);
            }
        }

        return new Pair<>(e1, skip);
    }

    // Разбирает базовые элементы (число, переменную или выражение в скобках)
    private static Pair<Expression, Integer> parseAtom(String expString) {
        expString = expString.replaceAll(" ", "");
        int skip = 0;

        if (expString.isEmpty()) {
            throw new IllegalArgumentException("Expression can't be empty");
        }

        Expression e1;
        if (expString.charAt(0) == '(') {
            expString = expString.substring(1); // Пропускаем '('
            skip++;

            Pair<Expression, Integer> pair = parseAdditionOrSubtraction(expString);
            e1 = pair.left;
            expString = expString.substring(pair.right);
            skip += pair.right;

            if (expString.isEmpty() || expString.charAt(0) != ')') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            expString = expString.substring(1); // Пропускаем ')'
            skip++;
        } else {
            String token = doubleFromString(expString);
            boolean isNumber = !token.isEmpty();
            if (!isNumber) {
                token = varFromString(expString);
            }
            if (token.isEmpty()) {
                throw new IllegalArgumentException("Expected number or variable");
            }
            e1 = isNumber ? new Number(Double.parseDouble(token)) : new Variable(token);
            expString = expString.substring(token.length());
            skip += token.length();
        }

        return new Pair<>(e1, skip);
    }

    // Основная точка входа
    public static Expression parseString(String expString) {
        return parseAdditionOrSubtraction(expString).left;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Expression exp = (Expression) obj;
        return this.toString().equals(exp.toString());
    }
}

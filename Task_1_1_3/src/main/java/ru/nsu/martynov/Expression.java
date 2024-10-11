package ru.nsu.martynov;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

/**
 * Interface for expressions.
 */
public abstract class Expression {
    /**
     * Print expression.
     */
    abstract void print();

    /**
     * Takes the derivative of one variable and returns it as an expression.
     *
     * @param x — variable for derivative
     * @return new derived expression.
     */
    // (uv)' = u'v+ uv'
    // ((uv)t)' = ((u'v+ uv')t) + uvt' = u'vt + uv't + uvt'
    // (uvta)' = ((u'vt + uv't + uvt')a)' = (u'vt + uv't + uvt')a + (u'vt + uv't + uvt')a' = u'vta + uv'ta + uvt'a + uvta'
    abstract Expression derivative(String x);

    /**
     * Substitutes the values of variables and evaluates the expression.
     *
     * @param vars — string with all the variables that occur in the expression.
     * @return — result after substitution of values.
     */
    abstract Double evalMap(Map<String, Double> vars);

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
            } catch (NumberFormatException ignored) {
            }
        }

        return map;
    }

    public static class Pair<L, R> {
        public final L left;
        public final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

    private static Pair<Expression, Integer> parseStringHelper(String expString) {
        expString = expString.replaceAll(" ", "");

        Integer skip = 0;

        if (expString.isEmpty()) {
            throw new IllegalArgumentException("Expression can't be empty");
        }

        Expression e1;
        if (expString.charAt(0) == '(') {
            // skip '('
            expString = expString.substring(1);
            skip++;

            Pair<Expression, Integer> pair = parseStringHelper(expString);
            e1 = pair.left;
            expString = expString.substring(pair.right);
            skip += pair.right;

            // skip ')'
            expString = expString.substring(1);
            skip++;
        } else {
            boolean flag = true; // Число
            // пробуем вытащить из строки число
            String exp1 = doubleFromString(expString);
            if (exp1.isEmpty()) {
                // если нет, то обязано быть имя переменной
                exp1 = varFromString(expString);
                flag = false; // Переменная
            }
            if (exp1.isEmpty()) {
                throw new IllegalArgumentException("There isn't number or variable");
            }
            if (flag) {
                e1 = new Number(Double.parseDouble(exp1));
            } else {
                e1 = new Variable(exp1);
            }

            // skip number or variable
            expString = expString.substring(exp1.length());
            skip += exp1.length();
        }

        if (expString.isEmpty()) {
            return new Pair<Expression, Integer>(e1, 0);
        }
        Character op = expString.charAt(0);

        Expression e2 = new Variable("tmp");
        if ("+-*/".contains(op.toString())) {
            // skip operation symbol
            expString = expString.substring(1);
            skip++;

            if (expString.charAt(0) == '(') {
                // skip '('
                expString = expString.substring(1);
                skip++;

                Pair<Expression, Integer> pair = parseStringHelper(expString);
                e2 = pair.left;
                expString = expString.substring(pair.right);
                skip += pair.right;

                // skip ')'
                expString = expString.substring(1);
                skip++;
            } else {
                boolean flag = true; // Число
                // пробуем вытащить из строки число
                String exp2 = doubleFromString(expString);
                if (exp2.isEmpty()) {
                    // если нет, то обязано быть имя переменной
                    exp2 = varFromString(expString);
                    flag = false; // Переменная
                }
                if (exp2.isEmpty()) {
                    throw new IllegalArgumentException("There isn't number or variable");
                }
                if (flag) {
                    e2 = new Number(Double.parseDouble(exp2));
                } else {
                    e2 = new Variable(exp2);
                }

                // skip number or variable
                expString = expString.substring(exp2.length());
                skip += exp2.length();
            }
        }

        if (op == '+') {
            return new Pair(new Add(e1, e2), skip);
        } else if (op == '-') {
            return new Pair(new Sub(e1, e2), skip);
        } else if (op == '*') {
            return new Pair(new Mul(e1, e2), skip);
        } else if (op == '/') {
            return new Pair(new Div(e1, e2), skip);
        } else {
            throw new IllegalArgumentException("Should be operator, but it isn't");
        }
    }

    public static Expression parseString(String expString) {
        return parseStringHelper(expString).left;
    }
}

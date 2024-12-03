package ru.nsu.martynov;

public abstract class AbstractExpression implements Expression{
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Expression exp = (Expression) obj;
        return this.toString().equals(exp.toString());
    }
}

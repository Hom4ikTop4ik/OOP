package ru.nsu.martynov;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point move(Direction direction) {
        if (direction == null) {
            return this;
        }
        switch (direction) {
            case UP: return new Point(x, y - 1);
            case DOWN: return new Point(x, y + 1);
            case LEFT: return new Point(x - 1, y);
            case RIGHT: return new Point(x + 1, y);
            default: return this; // throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

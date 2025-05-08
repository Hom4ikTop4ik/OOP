package ru.nsu.martynov;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Определяет противоположное направление.
     */
    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return null; // невозможная ситуация
        }
    }
}

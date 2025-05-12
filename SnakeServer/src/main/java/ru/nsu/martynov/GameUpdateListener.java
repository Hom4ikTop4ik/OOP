package ru.nsu.martynov;

public interface GameUpdateListener {
    void onGameMapUpdated();
    void onSnakeWin(Snake snake, GameMap gameMap);
    void onSnakeDeath(Snake snake, GameMap gameMap);
}

package ru.nsu.martynov;

public interface GameUpdateListener {
    void onGameMapUpdated(GameMap gameMap);
    void onSnakeWin(Snake snake, GameMap gameMap);
    void onSnakeDeath(Snake snake, GameMap gameMap);
}

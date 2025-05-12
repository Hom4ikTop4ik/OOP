package ru.nsu.martynov;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameStateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public volatile List<List<Point>> playerSnakes = new ArrayList<>();
    public volatile List<List<Point>> botSnakes = new ArrayList<>();
    public volatile List<Point> food;
    public volatile List<Point> walls;
    public volatile int score;
    public volatile boolean gameOver;
    public volatile boolean gameWon;
    public volatile int width;
    public volatile int height;

    public GameStateDTO(GameMap gameMap, int id, boolean gameOver, boolean gameWon) {
        // Snakes
        // Snakes[0] is cur player
        Snake snakeWithID = gameMap.getSnakeById(id);
        if (snakeWithID == null) {
            return;
        }

        List<Point> snakeWithIDPoints = new ArrayList<Point>(snakeWithID.getBody());
        playerSnakes.add(snakeWithIDPoints);

        for (Snake snake : gameMap.getSnakes()) {
            if (snake == snakeWithID) {
                continue;
            }

            List<Point> snakePoints = new ArrayList<Point>(snake.getBody());
            playerSnakes.add(snakePoints);
        }

        // SnakeBots
        for (SnakeBot snakeBot : gameMap.getBots()) {
            List<Point> snakeBotPoints = new ArrayList<Point>(snakeBot.getBody());
            botSnakes.add(snakeBotPoints);
        }

        // other data
        food = gameMap.getFood();
        walls = gameMap.getWalls();
        score = snakeWithID.getBody().size();
        this.gameOver = gameOver;
        this.gameWon = gameWon;

        width = gameMap.getWidth();
        height = gameMap.getHeight();
    }
}
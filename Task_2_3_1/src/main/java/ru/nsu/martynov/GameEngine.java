package ru.nsu.martynov;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class GameEngine {

    private final Settings settings;
    private Timeline timeline;
    private final Random random = new Random();

    private GameMap gameMap;

    private GameStatusListener statusListener;
    private GameView gameView;

    public GameEngine(Settings settings) {
        this.settings = settings;

        // Инициализация GameMap с автоматическим созданием еды, стен и змеек
        this.gameMap = new GameMap(settings);
    }

    public void setStatusListener(GameStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    public void start(GameView gameView) {
        this.gameView = gameView;

        timeline = new Timeline(new KeyFrame(Duration.millis(settings.getMoveInterval()), event -> {
            update();
            gameView.drawGame();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private List<Point> getShuffledNeighbors(Point p) {
        List<Point> neighbors = Arrays.asList(
                new Point(p.getX() + 1, p.getY()),
                new Point(p.getX() - 1, p.getY()),
                new Point(p.getX(), p.getY() + 1),
                new Point(p.getX(), p.getY() - 1)
        );
        Collections.shuffle(neighbors, random);
        return neighbors;
    }

    private boolean isInsideMap(Point p, Settings settings) {
        return p.getX() >= 0 && p.getX() < settings.getWidth() &&
                p.getY() >= 0 && p.getY() < settings.getHeight();
    }

    public void tryMoveSnake(Snake snake) {
        if (snake.getDirection() == null) return;

        Point next = snake.getNextHead(snake.getDirection());

        if (gameMap.isOccupied(next) || (!settings.getTorus() && !isInsideMap(next, settings))) {
            gameMap.removeSnake(snake);
        }

        if (gameMap.getFood().contains(next)) {
            snake.grow();
            gameMap.removeFood(next);
            gameMap.spawnFood(1);

            gameMap.setMaxLength(Math.max(gameMap.getMaxLength(), snake.getBody().size()));
        } else {
            snake.move();
        }

        if (snake.getBody().size() >= settings.getWinLength()) {
            onSnakeWin();
        }
    }

    public void tryMoveBot(SnakeBot bot) {
        if (bot.getDirection() == null) return;

        Point next = bot.getNextHead(bot.getDirection());

        if (gameMap.isOccupied(next) || (!settings.getTorus() && !isInsideMap(next, settings))) {
            gameMap.removeBot(bot);
            gameMap.spawnBots(1);
        }

        if (gameMap.getFood().contains(next)) {
            bot.grow();
            gameMap.removeFood(next);
            gameMap.spawnFood(1);
        } else {
            bot.move();
        }

        if (bot.getBody().size() >= settings.getBotLength()) {
            gameMap.removeBot(bot);
            gameMap.spawnBots(1);
        }
    }

    public void update() {
        for (Snake snake : new ArrayList<>(gameMap.getSnakes())) {
            tryMoveSnake(snake);
        }
        for (SnakeBot bot : new ArrayList<>(gameMap.getBots())) {
            tryMoveBot(bot);
        }

        if (gameMap.getSnakes().isEmpty()) {
            onSnakeDeath();
        }
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    private void onSnakeDeath() {
        stop();
        if (statusListener != null) {
            statusListener.onGameOver();
        }
    }

    private void onSnakeWin() {
        stop();
        if (statusListener != null) {
            statusListener.onVictory();
        }
    }

    public interface GameStatusListener {
        void onGameOver();
        void onVictory();
    }
}

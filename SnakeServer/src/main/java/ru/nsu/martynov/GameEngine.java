package ru.nsu.martynov;

import java.util.*;

public class GameEngine {

    private final Settings settings;
    private volatile boolean running = false;
    private volatile Thread gameLoopThread = null;
    private final Random random = new Random();

    private final GameMap gameMap;

    private volatile GameUpdateListener updateListener;

    private volatile boolean serverAlreadyWorked = false;

    public synchronized boolean getServerAlreadyWorked() {
        return serverAlreadyWorked;
    }

    public synchronized void setServerAlreadyWorked(boolean serverAlreadyWorked) {
        this.serverAlreadyWorked = serverAlreadyWorked;
    }

    public void setUpdateListener(GameUpdateListener listener) {
        this.updateListener = listener;
    }

    public GameEngine(Settings settings) {
        this.settings = settings;

        // Инициализация GameMap с автоматическим созданием еды, стен и змеек
        this.gameMap = new GameMap(settings);
    }

    public void start() {
        running = true;
        gameLoopThread = new Thread(() -> {
            long interval = settings.getMoveInterval(); // миллисекунды
            while (running) {
                long startTime = System.currentTimeMillis();

                update();
                System.out.println(gameMap.getSnakes().size());
                if (updateListener != null) {
                    updateListener.onGameMapUpdated();
                }
                // Возможно: notify clients или обновить состояние в логике

                long elapsedTime = System.currentTimeMillis() - startTime;
                long sleepTime = interval - elapsedTime;

                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        gameLoopThread.start();
    }


    public void stop(boolean inside) {
        running = false;
        if (inside || gameLoopThread == null) {
            return;
        }

        gameLoopThread.interrupt();
        System.out.println("interrupted");
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

        synchronized (gameMap) {
            Point next = snake.getNextHead(snake.getDirection());

            if (gameMap.isOccupied(next) || (!settings.getTorus() && !isInsideMap(next, settings))) {
                killSnake(snake, false);
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
                killSnake(snake, true);
            }
        }
    }

    public void tryMoveBot(SnakeBot bot) {
        if (bot.getDirection() == null) return;

        synchronized (gameMap) {
            Point next = bot.getNextHead(bot.getDirection());

            if (gameMap.isOccupied(next) || (!settings.getTorus() && !isInsideMap(next, settings))) {
                gameMap.spawnBots(1, getNextBotID());
                gameMap.removeBot(bot);
            }

            if (gameMap.getFood().contains(next)) {
                bot.grow();
                gameMap.removeFood(next);
                gameMap.spawnFood(1);
            } else {
                bot.move();
            }

            if (bot.getBody().size() >= settings.getBotLength()) {
                gameMap.spawnBots(1, getNextBotID());
                gameMap.removeBot(bot);
            }
        }
    }

    public void update() {
        for (Snake snake : new ArrayList<>(gameMap.getSnakes())) {
            tryMoveSnake(snake);
        }

        for (SnakeBot bot : new ArrayList<>(gameMap.getBots())) {
            tryMoveBot(bot);
        }

//        if (gameMap.getSnakes().isEmpty()) {
////            onCloseGame();
//            updateListener.onGameMapUpdated(gameMap);
//        }
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    private void killSnake(Snake snake, boolean win) {
        if (win) {
            updateListener.onSnakeWin(snake, gameMap);
        } else {
            updateListener.onSnakeDeath(snake, gameMap);
        }
        gameMap.removeSnake(snake);
    }

    public int getNextClientID() {
        int nextID = -1;
        for (Snake snake : getGameMap().getSnakes()) {
            if (snake.getID() > nextID) {
                nextID = snake.getID();
            }
        }
        return nextID + 1;
    }

    public int getNextBotID() {
        int nextID = -1;
        for (SnakeBot bot : getGameMap().getBots()) {
            if (bot.getID() > nextID) {
                nextID = bot.getID();
            }
        }
        return nextID + 1;
    }
}

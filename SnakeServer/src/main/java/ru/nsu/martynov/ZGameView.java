//package ru.nsu.martynov;
//
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//import java.util.List;
//
//public class GameView {
//    private final Canvas gameCanvas;
//    private Settings settings;
//    private GameMap gameMap;
//
//    private static final Color[] SNAKE_COLORS = {
//            Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE, Color.BROWN
//    };
//
//    public GameView(Canvas gameCanvas) {
//        this.gameCanvas = gameCanvas;
//    }
//
//    public void initialize(Settings settings, GameEngine gameEngine) {
//        this.settings = settings;
//        this.gameMap = gameEngine.getGameMap();
//        drawGame();
//    }
//    public void initialize(Settings settings, GameMap gameMap) {
//        this.settings = settings;
//        this.gameMap = gameMap;
//        drawGame();
//    }
//
//    public void drawGame() {
//        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
//        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
//
//        // Рисуем сетку
//        gc.setStroke(Color.LIGHTGRAY);
//        for (int i = 0; i < settings.getWidth(); i++) {
//            for (int j = 0; j < settings.getHeight(); j++) {
//                gc.strokeRect(i * settings.getCellSize(), j * settings.getCellSize(),
//                        settings.getCellSize(), settings.getCellSize());
//            }
//        }
//
//        // Рисуем стены, если есть
//        gc.setFill(Color.DIMGRAY);
//        for (Point wall : gameMap.getWalls()) {
//            gc.fillRect(wall.getX() * settings.getCellSize(),
//                    wall.getY() * settings.getCellSize(),
//                    settings.getCellSize(), settings.getCellSize());
//        }
//
//        // Рисуем змей
//        List<Snake> snakes = gameMap.getSnakes();
//        for (int i = 0; i < snakes.size(); i++) {
//            Snake snake = snakes.get(i);
//            Color color = snake.getColor();
//            Color darkColor = snake.getDarkColor();
//
//            gc.setFill(color);
//            for (Point p : snake.getBody()) {
//                gc.fillRect(p.getX() * settings.getCellSize(),
//                        p.getY() * settings.getCellSize(),
//                        settings.getCellSize(), settings.getCellSize());
//            }
//
//            // Перерисуем голову змейки более тёмным оттенком
//            gc.setFill(darkColor);
//            Point head = snake.getHead();
//            gc.fillRect(head.getX() * settings.getCellSize(),
//                    head.getY() * settings.getCellSize(),
//                    settings.getCellSize(), settings.getCellSize());
//        }
//
//        // Рисуем ботов
//        List<SnakeBot> bots = gameMap.getBots();
//        for (int i = 0; i < bots.size(); i++) {
//            SnakeBot bot = bots.get(i);
//            Color color = bot.getColor();
//            Color darkColor = bot.getDarkColor();
//
//            gc.setFill(color);
//            for (Point p : bot.getBody()) {
//                gc.fillRect(p.getX() * settings.getCellSize(),
//                        p.getY() * settings.getCellSize(),
//                        settings.getCellSize(), settings.getCellSize());
//            }
//
//            // Перерисуем голову змейки более тёмным оттенком
//            gc.setFill(color.darker());
//            Point head = bot.getHead();
//            gc.fillRect(head.getX() * settings.getCellSize(),
//                    head.getY() * settings.getCellSize(),
//                    settings.getCellSize(), settings.getCellSize());
//        }
//
//        // Рисуем еду
//        gc.setFill(Color.RED);
//        for (Point food : gameMap.getFood()) {
//            gc.fillRect(food.getX() * settings.getCellSize(),
//                    food.getY() * settings.getCellSize(),
//                    settings.getCellSize(), settings.getCellSize());
//        }
//    }
//
//    public void drawNetworkGame(GameStateDTO gameStateDTO) {
//        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
//        int cellSize = settings.getCellSize();
//
//        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
//
//        // Рисуем сетку
//        gc.setStroke(Color.LIGHTGRAY);
//        for (int i = 0; i < settings.getWidth(); i++) {
//            for (int j = 0; j < settings.getHeight(); j++) {
//                gc.strokeRect(i * cellSize, j * cellSize, cellSize, cellSize);
//            }
//        }
//
//        // Рисуем стены
//        gc.setFill(Color.DIMGRAY);
//        for (Point wall : gameStateDTO.walls) {
//            gc.fillRect(wall.getX() * cellSize, wall.getY() * cellSize, cellSize, cellSize);
//        }
//
//        // Рисуем змей игроков
//        for (int i = 0; i < gameStateDTO.playerSnakes.size(); i++) {
//            List<Point> snake = gameStateDTO.playerSnakes.get(i);
//            if (snake.isEmpty()) continue;
//
//            Color color = Color.RED;  // Можно сделать цвета по игроку, если надо
//            if (i == 0) {
//                // our snake
//                color = Color.GREEN;
//            }
//            Color headColor = color.darker();
//
//            gc.setFill(color);
//            for (Point p : snake) {
//                gc.fillRect(p.getX() * cellSize, p.getY() * cellSize, cellSize, cellSize);
//            }
//
//            gc.setFill(headColor);
//            Point head = snake.get(0);
//            gc.fillRect(head.getX() * cellSize, head.getY() * cellSize, cellSize, cellSize);
//        }
//
//        // Рисуем ботов
//        for (int i = 0; i < gameStateDTO.botSnakes.size(); i++) {
//            List<Point> bot = gameStateDTO.botSnakes.get(i);
//            if (bot.isEmpty()) continue;
//
//            Color color = Color.BLUE;
//            Color headColor = color.darker();
//
//            gc.setFill(color);
//            for (Point p : bot) {
//                gc.fillRect(p.getX() * cellSize, p.getY() * cellSize, cellSize, cellSize);
//            }
//
//            gc.setFill(headColor);
//            Point head = bot.get(0);
//            gc.fillRect(head.getX() * cellSize, head.getY() * cellSize, cellSize, cellSize);
//        }
//
//        // Рисуем еду
//        gc.setFill(Color.RED);
//        for (Point food : gameStateDTO.food) {
//            gc.fillRect(food.getX() * cellSize, food.getY() * cellSize, cellSize, cellSize);
//        }
//    }
//}

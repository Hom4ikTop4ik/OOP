package ru.nsu.martynov;

import java.util.*;
import java.util.function.Predicate;

public class GameMap {
    private final List<Point> walls = new ArrayList<>();
    private final List<Point> food = new ArrayList<>();
    private final List<SnakeBot> bots = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();
    private int width;
    private int height;

    private final Settings settings;

    private int maxLength = 0;

    private final Random random = new Random();

    private int maxRes = 0;

    public GameMap(Settings settings) {
        this.settings = settings;
        this.width = settings.getWidth();
        this.height = settings.getHeight();
        initMap();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    private List<Point> getEmptyCells() {
        List<Point> emptyCells = new ArrayList<>();

        // Проходим по всему полю (width * height)
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Point p = new Point(x, y);
                // Проверяем, не занята ли клетка стенами, едой или телами ботов
                if (!isBusy(p)) {
                    emptyCells.add(p);
                }
            }
        }

        return emptyCells;
    }

    private Point getRandomEmptyCell() {
        List<Point> emptyCells = getEmptyCells();
        if (emptyCells.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return emptyCells.get(random.nextInt(emptyCells.size())); // Возвращаем случайную пустую клетку
    }

    private boolean isOccupiedByWalls(Point p) {
        return walls.contains(p);
    }

    private boolean isOccupiedByFood(Point p) {
        return food.contains(p);
    }

    private boolean isOccupiedBySnake(Point p) {
        // Проверяем, занята ли клетка телом какой-либо змейки
        for (Snake snake : snakes) {
            if (snake.getBody().contains(p)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOccupiedByBot(Point p) {
        // Проверяем, занята ли клетка хотя бы одним ботом
        for (SnakeBot bot : bots) {
            if (bot.getBody().contains(p)) {
                return true;
            }
        }
        return false;
    }


    public boolean isOccupied(Point p) {
        return isOccupiedByBot(p) || isOccupiedBySnake(p) || isOccupiedByWalls(p);
    }

    public boolean isBusy(Point p) {
        return isOccupiedByBot(p) || isOccupiedBySnake(p) || isOccupiedByWalls(p) || isOccupiedByFood(p);
    }

    private void initMap() {
        // Создаём стены
        // generateWalls();
        generateConnectedRandomWalls(10);
        // Создаём еду
        spawnFood(settings.getFoodCount());

        // Не создаём змей, их ServerEngine добавит

        // Создаём ботов
        spawnBots(settings.getBotCount());
    }

    private void generateWalls() {
        for (int x = 0; x < this.width; x++) {
            walls.add(new Point(x, 0));
            walls.add(new Point(x, this.height - 1));
        }
        for (int y = 0; y < this.height; y++) {
            walls.add(new Point(0, y));
            walls.add(new Point(this.width - 1, y));
        }
    }

    private void generateRandomWalls(int percentage) {
        int totalCells = this.width * this.height;
        int wallCount = (int) (totalCells * (percentage / 100.0));

        walls.clear();
        for (int i = 0; i < wallCount; i++) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);

            // Если клетка не является частью змейки или еды, добавляем стену
            Point point = new Point(x, y);
            if (!walls.contains(point)) {
                walls.add(point);
            }
        }
    }


    private void generateWallsTogether(int percentage) {
        int totalCells = this.width * this.height;
        int wallCount = (int) (totalCells * (percentage / 100.0));

        walls.clear();
        List<Point> possibleWallPositions = new ArrayList<>();

        // Пройдемся по всем клеткам карты, добавляя их в список потенциальных стен
        for (int x = 1; x < this.width - 1; x++) {
            for (int y = 1; y < this.height - 1; y++) {
                possibleWallPositions.add(new Point(x, y));
            }
        }

        // Сортируем возможные позиции стен случайным образом
        Collections.shuffle(possibleWallPositions);

        // Выбираем стенки, которые будут соседями друг с другом
        for (int i = 0; i < wallCount; i++) {
            Point current = possibleWallPositions.get(i);

            // Проверяем, что клетки рядом с текущей тоже могут стать стенами
            if (canAddWall(current)) {
                walls.add(current);
            }
        }
    }

    private void generateConnectedRandomWalls(int percantage) {
        int totalCells = this.width * this.height;
        int targetCount = (int) (totalCells * (percantage / 100.0));
        walls.clear();

        Set<Point> visited = new HashSet<>();
        int[] count = {0};

        while (count[0] < targetCount) {
            Point start = new Point(random.nextInt(this.width), random.nextInt(this.height));
            if (walls.contains(start) || !isInsideMap(start, settings)) continue;

            growWallRecursive(start, settings, count, targetCount, visited, 0);
        }
    }


    private void growWallRecursive(Point current, Settings settings, int[] count, int targetCount, Set<Point> visited, int depth) {
        if (count[0] >= targetCount || visited.contains(current)) return;
        visited.add(current);

        if (!isInsideMap(current, settings)) return;

        double baseProbability = 0.2; // вероятность продолжить на глубине 1
        double continueProbability = Math.pow(baseProbability, depth);

        if (random.nextDouble() > continueProbability) return;

        walls.add(current);
        count[0]++;

        for (Point neighbor : getShuffledNeighbors(current)) {
            growWallRecursive(neighbor, settings, count, targetCount, visited, depth + 1);
        }
    }



    private boolean isInsideMap(Point p, Settings settings) {
        return p.getX() > 0 && p.getX() < this.width - 1 &&
                p.getY() > 0 && p.getY() < this.height - 1;
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


    // Метод, проверяющий, можно ли добавить стену рядом с уже добавленными стенами
    private boolean canAddWall(Point point) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                Point neighbor = new Point(point.getX() + dx, point.getY() + dy);
                if (walls.contains(neighbor)) {
                    return true; // Стена будет рядом с другой стеной
                }
            }
        }
        return false; // Если рядом нет стен
    }


    public void removeWall(Point p) {
        walls.remove(p);
    }

    public void spawnFood(int count) {
        for (int i = 0; i < count; i++) {
            Point p = getRandomEmptyCell();
            food.add(p);
        }
    }

    public void removeFood(Point p) {
        food.remove(p);
    }

    public boolean spawnSnake(int ID) {
        if (getSnakeById(ID) != null) {
            return false;
        }
        Point p = getRandomEmptyCell();
        Snake snake = new Snake(p, ID);
        snakes.add(snake);
        return true;
    }

    public void removeSnake(Snake curSnake) {
        List<Snake> snakesCopy = new ArrayList<>(snakes);
        for (Snake snake : snakesCopy) {
            if (snake.getID() == curSnake.getID()) {
                snakes.remove(snake);
                maxRes = Math.max(maxRes, snake.getBody().size());
            }
        }
    }

    public void spawnBots(int count) {
        int pluser = getBots().size();
        for (int i = 0; i < count; i++) {
            Point p = getRandomEmptyCell();
            SnakeBot bot = new SnakeBot(p, pluser + i + 1, this);
            bots.add(bot);
        }
    }

    public void removeBot(SnakeBot curBot) {
        List<SnakeBot> botsCopy = new ArrayList<>(bots);
        for (SnakeBot bot : botsCopy) {
            if (bot.getID() == curBot.getID()) {
                bots.remove(bot);
            }
        }
    }

    public Snake getSnakeById(int id) {
        for (Snake snake : snakes) {
            if (snake.getID() == id) {
                return snake;
            }
        }
        return null;
    }

    public SnakeBot getBotById(int id) {
        for (SnakeBot bot : bots) {
            if (bot.getID() == id) {
                return bot;
            }
        }
        return null;
    }

    // === Стены ===
    public List<Point> getWalls() {
        return Collections.unmodifiableList(walls);
    }

    // === Еда ===
    public List<Point> getFood() {
        return Collections.unmodifiableList(food);
    }

    // === Боты ===
    public List<SnakeBot> getBots() {
        return Collections.unmodifiableList(bots);
    }

    // === Змейки ===
    public List<Snake> getSnakes() {
        return Collections.unmodifiableList(snakes);
    }

    public void removeDeadSnakes(Predicate<Snake> shouldRemove) {
        snakes.removeIf(shouldRemove);
    }

    public void removeDeadBots(Predicate<SnakeBot> shouldRemove) {
        bots.removeIf(shouldRemove);
    }

    // Очистка карты
    public void clear() {
        walls.clear();
        food.clear();
        bots.clear();
        snakes.clear();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

package ru.nsu.martynov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SnakeBot extends Snake {
    private final Random random = new Random();
    private GameMap gameMap;

    public SnakeBot(Point startPosition, int id, GameMap gameMap) {
        super(startPosition, id);
        randomDirection();
        this.gameMap = gameMap;
    }

    public void move() {
        super.move();
        updateDirection();
    }

    public void grow() {
        super.grow();
        updateDirection();
    }

    private void randomDirection() {
        Direction[] directions = Direction.values();
        Direction chosen = directions[random.nextInt(directions.length)];
        setDirection(chosen);
    }

    private void updateDirection() {
        Point head = getHead();  // Текущая голова бота
        Point closestFood = findClosestFood();

        if (closestFood == null) {
            // fallback: случайное безопасное направление
            Direction[] dirs = Direction.values();
            List<Direction> safe = Arrays.stream(dirs)
                    .filter(this::isSafeDirection)
                    .toList();
            if (!safe.isEmpty()) {
                setDirection(safe.get(random.nextInt(safe.size())));
            }
            return;
        }

        // Проверим все направления и найдём то, что ближе всего к еде
        Direction best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Direction dir : Direction.values()) {
            Point next = getNextHead(dir);
            if (!isSafe(next)) continue;

            int dist = manhattanDistance(next, closestFood);
            if (dist < minDistance) {
                minDistance = dist;
                best = dir;
            }
        }

        if (best != null) {
            setDirection(best);
        } else {
            // fallback: безопасное направление
            Direction[] dirs = Direction.values();
            List<Direction> safe = Arrays.stream(dirs)
                    .filter(this::isSafeDirection)
                    .toList();
            if (!safe.isEmpty()) {
                setDirection(safe.get(random.nextInt(safe.size())));
            }
        }
    }

    private Point findClosestFood() {
        Point head = getHead();
        return gameMap.getFood().stream()
                .min(Comparator.comparingInt(p -> manhattanDistance(p, head)))
                .orElse(null);
    }

    private int manhattanDistance(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private boolean isSafe(Point p) {
        return !gameMap.isOccupied(p)
                && gameMap.getSnakes().stream().noneMatch(s -> s.getBody().contains(p))
                && gameMap.getBots().stream().noneMatch(b -> b != this && b.getBody().contains(p));
    }

    private boolean isSafeDirection(Direction dir) {
        return isSafe(getNextHead(dir));
    }
}

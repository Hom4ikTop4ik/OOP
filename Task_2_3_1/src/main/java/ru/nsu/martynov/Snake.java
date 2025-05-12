package ru.nsu.martynov;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.paint.Color;

import java.util.Random;

public class Snake {
    private final LinkedList<Point> body = new LinkedList<>();
    private Direction direction = null;
    private DirectionHorVer lastDirection = null;
    private int id;
    private Color color;
    private Color darkColor;


    Random rand = new Random();

    public Snake(Point startPosition, int id) {
        body.add(startPosition);
        this.id = id;
        initColors(0, 127, 127, 255, "G");
    }

    public List<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }

    /**
     * Рассчитывает следующую позицию головы змейки без движения.
     */
    public Point getNextHead(Direction direction) {
        return getHead().move(direction);
    }


    /**
     * Обычное движение змейки: добавляем новую голову, убираем хвост.
     */
    public void move() {
        grow();
        body.removeLast();
    }

    /**
     * Рост змейки: добавляем новую голову без удаления хвоста.
     */
    public void grow() {
        Point nextHead = getNextHead(direction);
        body.addFirst(nextHead);

        if (this.direction == Direction.UP || direction == Direction.DOWN) {
            this.lastDirection = DirectionHorVer.VERTICAL;
        }
        else if (this.direction == Direction.LEFT || direction == Direction.RIGHT) {
            this.lastDirection = DirectionHorVer.HORIZONTAL;
        }
        else {
            this.lastDirection = null;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setLastDirection(DirectionHorVer directionHorVer) {
        this.lastDirection = directionHorVer;
    }

    public DirectionHorVer getLastDirection() {
        return lastDirection;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getDarkColor() {
        return darkColor;
    }
    public void setDarkColor(Color darkColor) {
        this.darkColor = darkColor;
    }


    void initColors(int min, int max, int minPrime, int maxPrime, String primeColor) {
        int between = max - min + 1;
        int betweenPrime = maxPrime - minPrime + 1;

        // Генерация случайных значений для каждого канала от min о max
        int r = (primeColor.equals("R")) ? rand.nextInt(betweenPrime) + minPrime : rand.nextInt(between) + min;
        int g = (primeColor.equals("G")) ? rand.nextInt(betweenPrime) + minPrime : rand.nextInt(between) + min;
        int b = (primeColor.equals("B")) ? rand.nextInt(betweenPrime) + minPrime : rand.nextInt(between) + min;

        // Создаём светлый цвет
        Color color = Color.rgb(r, g, b);
        setColor(color);

        // Создаём тёмный вариант, вычитая 50 из каждого канала (для тёмного цвета)
        Color darkColor = Color.rgb(Math.max(r - 50, 0), Math.max(g - 50, 0), Math.max(b - 50, 0));
        setDarkColor(darkColor);
    }
}

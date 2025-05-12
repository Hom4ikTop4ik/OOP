package ru.nsu.martynov;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final LinkedList<Point> body = new LinkedList<>();
    private volatile Direction direction = null;
    private volatile DirectionHorVer lastDirection = null;
    private volatile int id;

    public Snake(Point startPosition, int id) {
        body.add(startPosition);
        this.id = id;
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
}

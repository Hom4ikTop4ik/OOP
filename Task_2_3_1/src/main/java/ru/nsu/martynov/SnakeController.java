package ru.nsu.martynov;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class SnakeController {

    private final List<Snake> snakes;
    public SnakeController(List<Snake> snakes) {
        this.snakes = snakes;
    }

    public void handleKeyPressed(KeyEvent event, GameEngine gameEngine) {
        KeyCode code = event.getCode();

        for (int i = 0; i < SnakeControlKeys.values().length; i++) {
            KeyCode[] keys = SnakeControlKeys.values()[i].getKeys();
            Snake snake = gameEngine.getGameMap().getSnakeById(i + 1); // ID начинается с 1

            if (snake != null) {
                tryChangeDirection(snake, code, keys);
            }
        }
    }

    public void handleKeyPressedOnline(KeyEvent event, int type, ObjectOutputStream objectOutputStream) {
        if (snakes.isEmpty() || type < 0 || type >= SnakeControlKeys.values().length) {
            return;
        }

        KeyCode code = event.getCode();
        KeyCode[] keys = SnakeControlKeys.values()[type].getKeys();

        Snake snake = snakes.get(0);
        tryChangeDirection(snake, code, keys);

        if (snake.getDirection() != null) {
            // отправка на сервер
            try {
                objectOutputStream.writeObject(snake.getDirection()); // или обёртку, если нужно больше данных
                objectOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Ошибка при отправке направления: " + e.getMessage());
            }
        }
    }

    private void tryChangeDirection(Snake snake, KeyCode code, KeyCode[] keys) {
        if (snake.getBody().size() <= 1 || snake.getLastDirection() != DirectionHorVer.VERTICAL) {
            if (code == keys[0]) {
                snake.setDirection(Direction.UP);
            } else if (code == keys[1]) {
                snake.setDirection(Direction.DOWN);
            }
        }
        if (snake.getBody().size() <= 1 || snake.getLastDirection() != DirectionHorVer.HORIZONTAL) {
            if (code == keys[2]) {
                snake.setDirection(Direction.LEFT);
            } else if (code == keys[3]) {
                snake.setDirection(Direction.RIGHT);
            }
        }
    }

    public Direction getSnakeDirection(Snake inputSnake) {
        for (Snake snake : snakes) {
            if (inputSnake.getID() == snake.getID()) {
                return snake.getDirection();
            }
        }
        return null;
    }
}

package ru.nsu.martynov;

import java.io.Serializable;

public class SettingsDTO implements Serializable {
    private int width;
    private int height;
    private int foodCount;
    private int snakeCount;
    private int winLength;
    private int cellSize;
    private int moveInterval;
    private int botCount;
    private int botLen;

    private boolean torus = false;

    public SettingsDTO(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
        foodCount = settings.getFoodCount();
        snakeCount = settings.getSnakeCount();
        winLength = settings.getWinLength();
        cellSize = settings.getCellSize();
        moveInterval = settings.getMoveInterval();
        botCount = settings.getBotCount();
        botLen = settings.getBotLength();

        torus = settings.getTorus();
    }
}

package ru.nsu.martynov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Settings {
    private volatile int width;
    private volatile int height;
    private volatile int foodCount;
    private volatile int snakeCount;
    private volatile int winLength;
    private volatile int cellSize;
    private volatile int moveInterval;
    private volatile int botCount;
    private volatile int botLen;

    private boolean torus = false;

    // Конструктор по умолчанию с настройками по умолчанию
    public Settings() {
        this.width = 10;
        this.height = 10;
        this.foodCount = 5;
        this.winLength = 10;
        this.snakeCount = 1;
        this.cellSize = 20;
        this.moveInterval = 300;
        this.botCount = 2;
        this.botLen = 7;
    }

    // Метод для загрузки настроек из файла
    public void loadFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        try {
            this.width = Integer.parseInt(reader.readLine().trim());
            this.height = Integer.parseInt(reader.readLine().trim());
            this.foodCount = Integer.parseInt(reader.readLine().trim());
            this.snakeCount = Integer.parseInt(reader.readLine().trim());
            this.winLength = Integer.parseInt(reader.readLine().trim());
            this.cellSize = Integer.parseInt(reader.readLine().trim());
            this.moveInterval = Integer.parseInt(reader.readLine().trim());
            this.botCount = Integer.parseInt(reader.readLine().trim());
            this.botLen = Integer.parseInt(reader.readLine().trim());
        } catch (NumberFormatException | IOException e) {
            throw new IOException("Ошибка при чтении или парсинге файла настроек.", e);
        } finally {
            reader.close();
        }
    }

    // Геттеры и сеттеры для всех полей
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMoveInterval() {
        return moveInterval;
    }

    public void setMoveInterval(int moveInterval) {
        this.moveInterval = moveInterval;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public int getWinLength() {
        return winLength;
    }

    public void setWinLength(int winLength) {
        this.winLength = winLength;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getBotCount() {
        return botCount;
    }

    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }

    public int getBotLength() {
        return botLen;
    }

    public void setBotLen(int botCount) {
        this.botLen = botLen;
    }

    public void setSnakeCount(int snakeCnt) {
        this.snakeCount = snakeCnt;
    }

    public int getSnakeCount() {
        return snakeCount;
    }

    public void setTorus(boolean torus) {
        this.torus = torus;
    }

    public boolean getTorus() {
        return torus;
    }
}

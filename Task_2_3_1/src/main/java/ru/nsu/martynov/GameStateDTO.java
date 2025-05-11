package ru.nsu.martynov;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameStateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<List<Point>> playerSnakes = new ArrayList<>();
    public List<List<Point>> botSnakes = new ArrayList<>();
    public List<Point> food = new ArrayList<>();
    public List<Point> walls = new ArrayList<>();
    public int score = 0;
    public boolean gameOver = false;
    public boolean gameWon = false;
    public int width;
    public int height;
}
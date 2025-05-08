package ru.nsu.martynov;

import java.io.Serializable;
import java.util.List;

public class GameStateDTO implements Serializable {
    public List<List<Point>> playerSnakes;
    public List<List<Point>> botSnakes;
    public List<Point> food;
    public List<Point> walls;
    public int score;
    public boolean gameOver;
}
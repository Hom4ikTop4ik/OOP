package ru.nsu.martynov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Walls {
    private final List<Point> wallPoints = new ArrayList<>();

    public Walls(Settings settings) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("resources/walls.txt"));
            if (!lines.isEmpty() && lines.get(0).equals("RANDOM")) {
                generateRandomWalls(settings);
            } else {
                for (String line : lines) {
                    String[] parts = line.split(",");
                    wallPoints.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            generateRandomWalls(settings); // fallback
        }
    }

    private void generateRandomWalls(Settings settings) {
        Random random = new Random();
        int wallCount = (settings.getWidth() * settings.getHeight()) / 20;
        for (int i = 0; i < wallCount; i++) {
            wallPoints.add(new Point(random.nextInt(settings.getWidth()), random.nextInt(settings.getHeight())));
        }
    }

    public List<Point> getWalls() {
        return wallPoints;
    }

    public boolean isWall(Point p) {
        return wallPoints.contains(p);
    }
}

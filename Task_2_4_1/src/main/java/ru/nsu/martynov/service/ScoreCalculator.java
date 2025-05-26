package ru.nsu.martynov.service;

import ru.nsu.martynov.model.*;

import java.util.Map;

public class ScoreCalculator {

    public int calculate(Task task, TestResult result, Settings settings) {
        if (result.total() == 0) return -5;
        if (result.getPassed() == 0) return -6;

        double passRate = result.passRate();
        int rawScore = (int)(task.getMaxScore() * passRate);

        // Пример штрафа за неполное покрытие или плохой стиль можно сюда встроить
        return rawScore;
    }

    public void calculateCheckpoints(Student student, CourseConfig config) {
        // Пример: агрегировать баллы по задачам в каждом checkpoint и сохранять
        for (Checkpoint checkpoint : config.getCheckpoints()) {
            int sum = 0;
            for (String taskId : checkpoint.getTasks()) {
                Integer s = student.getScores().get(taskId);
                if (s != null) {
                    sum += s;
                }
            }
            student.addScore(checkpoint.getId(), sum);
        }
    }
}

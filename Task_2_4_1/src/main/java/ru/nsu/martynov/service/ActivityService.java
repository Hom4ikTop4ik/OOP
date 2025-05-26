package ru.nsu.martynov.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ActivityService {
    private final LocalDate courseStartDate;

    public ActivityService(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    /**
     * Получить даты коммитов в репозитории
     */
    public Set<LocalDate> getCommitDates(File repoDir) {
        Set<LocalDate> dates = new HashSet<>();
        try {
            Process process = new ProcessBuilder("git", "log", "--pretty=format:%ci")
                    .directory(repoDir)
                    .start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String datePart = line.substring(0, 10); // yyyy-MM-dd
                    LocalDate date = LocalDate.parse(datePart);
                    dates.add(date);
                }
            }
            process.waitFor();
        } catch (Exception e) {
            System.err.println("Ошибка при получении коммитов: " + e.getMessage());
        }
        return dates;
    }

    /**
     * Подсчитать количество активных недель, начиная с courseStartDate
     */
    public int countActiveWeeks(Set<LocalDate> commitDates) {
        Set<Long> activeWeeks = new HashSet<>();

        for (LocalDate commitDate : commitDates) {
            long weekIndex = ChronoUnit.WEEKS.between(courseStartDate, commitDate);
            if (weekIndex >= 0) {
                activeWeeks.add(weekIndex);
            }
        }
        return activeWeeks.size();
    }
}

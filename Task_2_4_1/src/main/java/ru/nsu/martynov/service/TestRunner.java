package ru.nsu.martynov.service;

import ru.nsu.martynov.model.TestResult;

import java.io.File;
import java.nio.file.Files;

public class TestRunner {

    public TestResult run(File repoDir) {
        try {
            Process process = new ProcessBuilder("gradlew.bat", "test", "--no-daemon")
                    .directory(repoDir)
                    .inheritIO()
                    .start();
            int exit = process.waitFor();

            File reportsDir = new File(repoDir, "build/test-results/test");
            if (reportsDir.exists() && reportsDir.isDirectory()) {
                File[] files = reportsDir.listFiles((dir, name) -> name.startsWith("TEST-") && name.endsWith(".xml"));
                if (files != null && files.length > 0) {
                    int totalPassed = 0;
                    int totalFailed = 0;
                    int totalSkipped = 0;

                    for (File report : files) {
                        String content = Files.readString(report.toPath());
                        totalPassed += countOccurrences(content, "testcase");
                        totalFailed += countOccurrences(content, "failure");
                        totalSkipped += countOccurrences(content, "skipped");
                    }

                    return new TestResult(totalPassed, totalFailed, totalSkipped);
                } else {
                    System.err.println("Отчёты не найдены в " + reportsDir.getAbsolutePath());
                }
            } else {
                System.err.println("Папка с отчётами не найдена: " + reportsDir.getAbsolutePath());
            }
            return new TestResult(0, 0, 0);

        } catch (Exception e) {
            System.err.println("Ошибка запуска тестов: " + e.getMessage());
            return new TestResult(0, 0, 0);
        }
    }

    private int countOccurrences(String str, String sub) {
        int count = 0, idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx++;
        }
        return count;
    }
}

package ru.nsu.martynov.report;

import ru.nsu.martynov.model.*;

public class ConsoleReportGenerator implements ReportGenerator {

    @Override
    public void generate(CourseConfig config) {
        System.out.println("Отчёт по курсу: " + config.getCourseName());

        for (Group group : config.getGroups()) {
            System.out.println("Группа: " + group.getName());

            for (Task task : config.getTasks()) {
                System.out.println("Лабораторная " + task.getId() + " (" + task.getName() + ")");
                System.out.println("Студент      | Сборка | Документация | Style guide | Тесты   | Доп. балл | Общий балл ");
                System.out.println("--------------------------------------------------------------------------");

                for (Student student : group.getStudents()) {
                    Boolean buildOk = student.getBuildResults().getOrDefault(task.getId(), false);
                    Boolean docOk = student.getDocumentationResults().getOrDefault(task.getId(), false);
                    Boolean styleOk = student.getStyleResults().getOrDefault(task.getId(), false);
                    TestResult testResult = student.getTestResults().get(task.getId());
                    int bonus = student.getBonusScores().getOrDefault(task.getId(), 0);
                    int score = student.getScores().getOrDefault(task.getId(), 0);

                    String tests = testResult == null ? "0/0/0" : testResult.getPassed() + "/" + testResult.getFailed() + "/" + testResult.getSkipped();

                    System.out.printf("%-12s | %-6s | %-12s | %-11s | %-7s | %-9d | %-10d%n",
                            student.getName(),
                            boolToSign(buildOk),
                            boolToSign(docOk),
                            boolToSign(styleOk),
                            tests,
                            bonus,
                            score);
                }
                System.out.println();
            }

            // Общая статистика по группе
            System.out.println("Общая статистика группы " + group.getName());
            System.out.print("Студент      | ");
            for (Task task : config.getTasks()) {
                System.out.print(task.getId() + " | ");
            }
            System.out.println("Сумма | Активность | Оценка");
            System.out.println("-----------------------------------------------------");

            for (Student student : group.getStudents()) {
                System.out.printf("%-12s | ", student.getName());

                int sum = 0;
                for (Task task : config.getTasks()) {
                    int sc = student.getScores().getOrDefault(task.getId(), 0);
                    sum += sc;
                    System.out.printf("%-4d | ", sc);
                }

                double activityPercent = student.getActivityPercent();
                int finalScore = (int) Math.round(sum * activityPercent / 100.0);

                System.out.printf("%-5d | %-10f | %-6d%n", sum, activityPercent, finalScore);
            }
            System.out.println();
        }
    }

    private String boolToSign(Boolean val) {
        return val != null && val ? "+" : "-";
    }
}

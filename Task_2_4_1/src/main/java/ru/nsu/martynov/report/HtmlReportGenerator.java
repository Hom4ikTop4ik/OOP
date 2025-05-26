package ru.nsu.martynov.report;

import ru.nsu.martynov.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HtmlReportGenerator implements ReportGenerator {

    @Override
    public void generate(CourseConfig config) {
        File outputDir = new File("student-test-reports/html");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File reportFile = new File(outputDir, "report_" + timestamp + ".html");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            writer.write("<html><head><meta charset='UTF-8'><title>Отчёт по курсу " + config.getCourseName() + "</title></head><body>");
            writer.write("<h1>Отчёт по курсу: " + config.getCourseName() + "</h1>");

            for (Group group : config.getGroups()) {
                writer.write("<h2>Группа " + group.getName() + "</h2>");

                for (Task task : config.getTasks()) {
                    writer.write("<h3>Лабораторная " + task.getId() + " (" + task.getName() + ")</h3>");
                    writer.write("<table border='1' cellpadding='5' cellspacing='0'>");
                    writer.write("<tr><th>Студент</th><th>Сборка</th><th>Документация</th><th>Style guide</th><th>Тесты</th><th>Доп. балл</th><th>Общий балл</th></tr>");

                    for (Student student : group.getStudents()) {
                        String build = boolToSign(student.getBuildResults().getOrDefault(task.getId(), false));
                        String doc = boolToSign(student.getDocumentationResults().getOrDefault(task.getId(), false));
                        String style = boolToSign(student.getStyleResults().getOrDefault(task.getId(), false));

                        TestResult tr = student.getTestResults().get(task.getId());
                        String tests = tr == null ? "0/0/0" : tr.getPassed() + "/" + tr.getFailed() + "/" + tr.getSkipped();

                        int bonus = student.getBonusScores().getOrDefault(task.getId(), 0);
                        int score = student.getScores().getOrDefault(task.getId(), 0);

                        writer.write("<tr>");
                        writer.write("<td>" + student.getName() + "</td>");
                        writer.write("<td>" + build + "</td>");
                        writer.write("<td>" + doc + "</td>");
                        writer.write("<td>" + style + "</td>");
                        writer.write("<td>" + tests + "</td>");
                        writer.write("<td>" + bonus + "</td>");
                        writer.write("<td>" + score + "</td>");
                        writer.write("</tr>");
                    }
                    writer.write("</table><br>");
                }

                writer.write("<h3>Общая статистика группы " + group.getName() + "</h3>");
                writer.write("<table border='1' cellpadding='5' cellspacing='0'>");
                writer.write("<tr><th>Студент</th>");
                for (Task task : config.getTasks()) {
                    writer.write("<th>" + task.getId() + "</th>");
                }
                writer.write("<th>Сумма</th><th>Активность</th><th>Оценка</th></tr>");

                for (Student student : group.getStudents()) {
                    writer.write("<tr>");
                    writer.write("<td>" + student.getName() + "</td>");

                    int sum = 0;
                    for (Task task : config.getTasks()) {
                        int sc = student.getScores().getOrDefault(task.getId(), 0);
                        sum += sc;
                        writer.write("<td>" + sc + "</td>");
                    }

                    writer.write("<td>" + sum + "</td>");

                    double activityPercent = student.getActivityPercent();
                    writer.write("<td>" + activityPercent + "%</td>");

                    int finalScore = (int) Math.round(sum * activityPercent / 100.0);
                    writer.write("<td>" + finalScore + "</td>");

                    writer.write("</tr>");
                }

                writer.write("</table><br>");
            }

            writer.write("</body></html>");
            System.out.println("HTML-отчёт сохранён: " + reportFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Ошибка записи HTML отчёта: " + e.getMessage());
        }
    }

    private String boolToSign(Boolean val) {
        return val != null && val ? "+" : "-";
    }
}

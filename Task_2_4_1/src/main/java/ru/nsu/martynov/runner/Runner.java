package ru.nsu.martynov.runner;

import ru.nsu.martynov.model.*;
import ru.nsu.martynov.report.ReportGenerator;
import ru.nsu.martynov.service.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Set;

public class Runner {
    private final CourseConfig config;
    private final File workspaceDir = new File("workspace");

    private final GitService gitService = new GitService();
    private final BuildService buildService;
    private final StyleChecker styleChecker;
    private final TestRunner testRunner = new TestRunner();
    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    private final ActivityService activityService;

    public Runner(CourseConfig config) {
        this.config = config;
        this.buildService = new BuildService(config.getSettings());
        this.styleChecker = new StyleChecker(config.getSettings().getCheckstyleConfigFile());

        // Например, дата старта курса
        LocalDate courseStartDate = LocalDate.of(2025, 2, 1);
        this.activityService = new ActivityService(courseStartDate);
    }

    public void execute() {
        if (!workspaceDir.exists()) {
            workspaceDir.mkdirs();
        }

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {
                System.out.println("Обработка студента: " + student.getName());

                File studentRepo = new File(workspaceDir, student.getGithub());
                if (!studentRepo.exists()) {
                    boolean cloned = cloneRepo(student.getRepo(), studentRepo);
                    if (!cloned) {
                        System.err.println("Не удалось клонировать репозиторий " + student.getRepo());
                        continue;
                    }
                }

                Set<LocalDate> commitDates = activityService.getCommitDates(studentRepo);
                int activeWeeks = activityService.countActiveWeeks(commitDates);
                System.out.println("  Активных недель: " + activeWeeks);

                for (Task task : config.getTasks()) {
                    File taskDir = new File(studentRepo, task.getId());

                    if (!taskDir.exists()) {
                        System.err.println("    Задание не найдено: " + taskDir.getAbsolutePath());
                        continue;
                    }

                    boolean built = buildService.build(taskDir);
                    if (!built) {
                        System.err.println("    Сборка не удалась");
                        continue;
                    }

                    boolean styleOk = styleChecker.checkStyle(taskDir);
                    if (!styleOk) {
                        System.err.println("    Стиль не соответствует Google Java Style");
                        continue;
                    }

                    // Передаём github студента во второй параметр
                    TestResult result = testRunner.run(taskDir);

//                    TestResult result = testRunner.run(taskDir, student.getGithub());

//                    File studentReportDir = new File("student-test-reports/" + student.getGithub() + "/" + task.getId());
//                    TestResult result = testRunner.run(taskDir, studentReportDir);

                    int baseScore = scoreCalculator.calculate(task, result, config.getSettings());

                    // Бонус за активность, максимум +5 баллов
                    int activityBonus = Math.min(activeWeeks, 5);

                    int finalScore = baseScore + activityBonus;

                    student.addScore(task.getId(), finalScore);
                    System.out.println("    Балл: " + finalScore + " (базовый: " + baseScore + ", бонус за активность: " + activityBonus + ")");
                }

                scoreCalculator.calculateCheckpoints(student, config);
            }
        }

        ReportGenerator generator = new ReportGenerator();
        generator.generate(config);
    }

    private boolean cloneRepo(String repoUrl, File dir) {
        try {
            Process process = new ProcessBuilder("git", "clone", repoUrl, dir.getAbsolutePath())
                    .inheritIO()
                    .start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            System.err.println("Ошибка клонирования: " + e.getMessage());
            return false;
        }
    }
}

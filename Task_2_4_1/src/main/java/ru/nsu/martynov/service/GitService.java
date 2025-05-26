package ru.nsu.martynov.service;

import ru.nsu.martynov.model.Student;

import java.io.File;
import java.util.List;

public class GitService {

    private final File baseDir = new File("repos");

    public void cloneRepos(List<Student> students) {
        if (!baseDir.exists()) baseDir.mkdirs();

        for (Student student : students) {
            try {
                String repoUrl = student.getRepo();
                File studentDir = new File(baseDir, student.getGithub());
                if (studentDir.exists()) {
                    System.out.println("Repo already exists: " + studentDir.getName());
                    continue;
                }

                Process clone = new ProcessBuilder("git", "clone", repoUrl, studentDir.getAbsolutePath())
                        .inheritIO()
                        .start();
                int result = clone.waitFor();
                if (result != 0) {
                    System.err.println("Failed to clone: " + repoUrl);
                }
            } catch (Exception e) {
                System.err.println("Error cloning repo: " + e.getMessage());
            }
        }
    }

    /**
     * Обновляет все локальные репозитории студентов.
     * Для каждого существующего репо выполняет принудительное обновление:
     * git fetch origin
     * git reset --hard origin/main (замените main на нужную ветку)
     */
    public void updateRepos(List<Student> students) {
        for (Student student : students) {
            File studentDir = new File(baseDir, student.getGithub());
            if (!studentDir.exists()) {
                System.out.println("Repo not found for update: " + studentDir.getName());
                continue;
            }

            try {
                // git fetch origin
                Process fetch = new ProcessBuilder("git", "fetch", "origin")
                        .directory(studentDir)
                        .inheritIO()
                        .start();
                int fetchResult = fetch.waitFor();
                if (fetchResult != 0) {
                    System.err.println("Failed to fetch origin in repo: " + studentDir.getName());
                    continue;
                }

                // git reset --hard origin/main
                Process reset = new ProcessBuilder("git", "reset", "--hard", "origin/main")
                        .directory(studentDir)
                        .inheritIO()
                        .start();
                int resetResult = reset.waitFor();
                if (resetResult != 0) {
                    System.err.println("Failed to reset repo to origin/main: " + studentDir.getName());
                } else {
                    System.out.println("Repo updated (reset hard) successfully: " + studentDir.getName());
                }

            } catch (Exception e) {
                System.err.println("Error updating repo " + studentDir.getName() + ": " + e.getMessage());
            }
        }
    }

    public File getStudentRepoDir(String github) {
        return new File(baseDir, github);
    }
}

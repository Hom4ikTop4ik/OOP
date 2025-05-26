package ru.nsu.martynov.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private String github;
    private String name;
    private String repo;

    // taskId -> earned points
    private Map<String, Integer> scores = new HashMap<>();

    public Student(String github, String name, String repo) {
        this.github = github;
        this.name = name;
        this.repo = repo;
    }

    // --- Getters ---
    public String getGithub() {
        return github;
    }

    public String getName() {
        return name;
    }

    public String getRepo() {
        return repo;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    // --- Setters ---
    public void setGithub(String github) {
        this.github = github;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public void addScore(String taskId, int score) {
        scores.put(taskId, score);
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(github, student.github);
    }

    @Override
    public int hashCode() {
        return Objects.hash(github);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Student{" +
                "github='" + github + '\'' +
                ", name='" + name + '\'' +
                ", repo='" + repo + '\'' +
                ", scores=" + scores +
                '}';
    }
}

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

    private Map<String, Boolean> buildResults = new HashMap<>();
    private Map<String, Boolean> documentationResults = new HashMap<>();
    private Map<String, Boolean> styleResults = new HashMap<>();
    private Map<String, TestResult> testResults = new HashMap<>();
    private Map<String, Integer> bonusScores = new HashMap<>();
    private double activityPercent;

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

    public Map<String, Boolean> getBuildResults() {
        return buildResults;
    }

    public Map<String, Boolean> getDocumentationResults() {
        return documentationResults;
    }

    public Map<String, Boolean> getStyleResults() {
        return styleResults;
    }

    public Map<String, TestResult> getTestResults() {
        return testResults;
    }

    public Map<String, Integer> getBonusScores() {
        return bonusScores;
    }

    public double getActivityPercent() {
        return activityPercent;
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
    public void setBuildResults(Map<String, Boolean> buildResults) {
        this.buildResults = buildResults;
    }

    public void setDocumentationResults(Map<String, Boolean> documentationResults) {
        this.documentationResults = documentationResults;
    }

    public void setStyleResults(Map<String, Boolean> styleResults) {
        this.styleResults = styleResults;
    }

    public void setTestResults(Map<String, TestResult> testResults) {
        this.testResults = testResults;
    }

    public void setBonusScores(Map<String, Integer> bonusScores) {
        this.bonusScores = bonusScores;
    }

    public void setActivityPercent(double activityPercent) {
        this.activityPercent = activityPercent;
    }

    // Add methods

    public void addScore(String taskId, int score) {
        scores.put(taskId, score);
    }

    public void addBuildResult(String taskId, boolean result) {
        buildResults.put(taskId, result);
    }

    public void addDocumentationResult(String taskId, boolean result) {
        documentationResults.put(taskId, result);
    }

    public void addStyleResult(String taskId, boolean result) {
        styleResults.put(taskId, result);
    }

    public void addTestResult(String taskId, TestResult result) {
        testResults.put(taskId, result);
    }

    public void addBonusScore(String taskId, int bonus) {
        bonusScores.put(taskId, bonus);
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

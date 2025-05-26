package ru.nsu.martynov.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private String id;
    private String title;
    private int maxScore;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;

    public Task(String id, String title, int maxScore, LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.title = title;
        this.maxScore = maxScore;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }

    // --- Setters ---
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setSoftDeadline(LocalDate softDeadline) {
        this.softDeadline = softDeadline;
    }

    public void setHardDeadline(LocalDate hardDeadline) {
        this.hardDeadline = hardDeadline;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", maxScore=" + maxScore +
                ", softDeadline=" + softDeadline +
                ", hardDeadline=" + hardDeadline +
                '}';
    }
}

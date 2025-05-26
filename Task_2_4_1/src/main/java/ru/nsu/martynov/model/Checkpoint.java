package ru.nsu.martynov.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Checkpoint {
    private String id;
    private String name;
    private LocalDate date;
    private List<String> tasks;

    public Checkpoint(String id, String name, LocalDate date, List<String> tasks) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.tasks = tasks;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<String> getTasks() {
        return tasks;
    }

    // --- Setters ---
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkpoint)) return false;
        Checkpoint that = (Checkpoint) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Checkpoint{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", tasks=" + tasks +
                '}';
    }
}

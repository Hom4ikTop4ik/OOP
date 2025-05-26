package ru.nsu.martynov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseConfig {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Checkpoint> checkpoints = new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();
    private Settings settings;

    // --- Добавление элементов ---
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    // --- Getters ---
    public List<Task> getTasks() {
        return tasks;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public Settings getSettings() {
        return settings;
    }

    // --- Setters ---
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    // --- equals & hashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseConfig)) return false;
        CourseConfig that = (CourseConfig) o;
        return Objects.equals(tasks, that.tasks) &&
                Objects.equals(groups, that.groups) &&
                Objects.equals(settings, that.settings) &&
                Objects.equals(assignments, that.assignments) &&
                Objects.equals(checkpoints, that.checkpoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, groups, assignments, checkpoints, settings);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "CourseConfig{" +
                "tasks=" + tasks +
                ", groups=" + groups +
                ", checkpoints=" + checkpoints +
                ", assignments=" + assignments +
                ", settings=" + settings +
                '}';
    }
}

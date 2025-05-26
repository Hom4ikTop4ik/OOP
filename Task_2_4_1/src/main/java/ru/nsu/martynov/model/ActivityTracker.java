package ru.nsu.martynov.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityTracker {
    public static class ActivityEntry {
        private LocalDateTime timestamp;
        private String studentGithub;
        private String action;
        private String taskId;
        private String description;

        public ActivityEntry(LocalDateTime timestamp, String studentGithub, String action, String taskId, String description) {
            this.timestamp = timestamp;
            this.studentGithub = studentGithub;
            this.action = action;
            this.taskId = taskId;
            this.description = description;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getStudentGithub() {
            return studentGithub;
        }

        public String getAction() {
            return action;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "[" + timestamp + "] " + studentGithub + " " + action +
                    " (" + taskId + "): " + description;
        }
    }

    private List<ActivityEntry> log = new ArrayList<>();

    public void record(String studentGithub, String action, String taskId, String description) {
        log.add(new ActivityEntry(
                LocalDateTime.now(),
                studentGithub,
                action,
                taskId,
                description
        ));
    }

    public List<ActivityEntry> getLog() {
        return log;
    }

    public void printLog() {
        for (ActivityEntry entry : log) {
            System.out.println(entry);
        }
    }
}

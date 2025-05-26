package ru.nsu.martynov.model;

public class Assignment {
    private String studentGithub;
    private String taskId;

    public Assignment(String studentGithub, String taskId) {
        this.studentGithub = studentGithub;
        this.taskId = taskId;
    }

    public String getStudentGithub() {
        return studentGithub;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setStudentGithub(String studentGithub) {
        this.studentGithub = studentGithub;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

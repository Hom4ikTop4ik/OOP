package ru.nsu.martynov.dsl;

import groovy.lang.Closure;
import groovy.lang.Script;
import ru.nsu.martynov.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class ConfigBaseScript extends Script {
    private final CourseConfig courseConfig = new CourseConfig();

    public void tasks(Closure<?> closure) {
        Object taskDelegate = new Object() {
            public void task(Map<String, Object> args) {
                Task task = new Task(
                        (String) args.get("id"),
                        (String) args.get("title"),
                        (Integer) args.get("maxScore"),
                        LocalDate.parse((String) args.get("softDeadline")),
                        LocalDate.parse((String) args.get("hardDeadline"))
                );
                courseConfig.addTask(task);
            }
        };

        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(taskDelegate);
        closure.call();
    }

    public void groups(Closure<?> closure) {
        Object groupsDelegate = new Object() {
            public void group(Map<String, Object> args, Closure<?> groupClosure) {
                Group group = new Group((String) args.get("name"));

                Object studentsDelegate = new Object() {
                    public void student(Map<String, Object> studentArgs) {
                        Student student = new Student(
                                (String) studentArgs.get("github"),
                                (String) studentArgs.get("name"),
                                (String) studentArgs.get("repo")
                        );
                        group.addStudent(student);
                    }
                };

                groupClosure.setResolveStrategy(Closure.DELEGATE_FIRST);
                groupClosure.setDelegate(studentsDelegate);
                groupClosure.call();

                courseConfig.addGroup(group);
            }
        };

        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(groupsDelegate);
        closure.call();
    }

    public void settings(Closure<?> closure) {
        Settings settings = new Settings();

        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(new Object() {
            public void latePenaltyPerDay(double penalty) {
                settings.setLatePenaltyPerDay(penalty);
            }

            public void autoCheckEnabled(boolean enabled) {
                settings.setAutoCheckEnabled(enabled);
            }

            public void reviewTimeoutDays(int days) {
                settings.setReviewTimeoutDays(days);
            }

            public void checkstyleConfig(String path) {
                settings.setCheckstyleConfigFile(path);
            }
        });

        closure.call();
        courseConfig.setSettings(settings);
    }

    public void checkpoints(Closure<?> closure) {
        Object delegate = new Object() {
            public void checkpoint(Map<String, Object> args) {
                String id = (String) args.get("id");
                String name = (String) args.get("name");
                LocalDate date = LocalDate.parse((String) args.get("date"));
                @SuppressWarnings("unchecked")
                List<String> tasks = (List<String>) args.get("tasks");

                Checkpoint cp = new Checkpoint(id, name, date, tasks);
                courseConfig.addCheckpoint(cp);
            }
        };

        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(delegate);
        closure.call();
    }

    public void assignments(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void assign(Map<String, Object> args) {
                Assignment assignment = new Assignment(
                        (String) args.get("github"),
                        (String) args.get("task")
                );
                courseConfig.addAssignment(assignment);
            }
        });
        closure.call();
    }

    public CourseConfig getCourseConfig() {
        return courseConfig;
    }
}

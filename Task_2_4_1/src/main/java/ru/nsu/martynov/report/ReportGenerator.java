//package ru.nsu.martynov.report;
//
//import ru.nsu.martynov.model.*;
//
//public class ReportGenerator {
//    public void generate(CourseConfig config) {
//        System.out.println("==== Course Report ====");
//        System.out.println("Groups:");
//        for (Group group : config.getGroups()) {
//            System.out.println("  Group: " + group.getName());
//            for (Student student : group.getStudents()) {
//                System.out.println("    - " + student.getName() + " (" + student.getGithub() + ")");
//            }
//        }
//
//        System.out.println("\nTasks:");
//        for (Task task : config.getTasks()) {
//            System.out.println("  - " + task.getId() + ": " + task.getTitle() + " (" + task.getMaxScore() + " pts)");
//            System.out.println("    Soft deadline: " + task.getSoftDeadline());
//            System.out.println("    Hard deadline: " + task.getHardDeadline());
//        }
//
//        Settings settings = config.getSettings();
//        if (settings != null) {
//            System.out.println("\nSettings:");
//            System.out.println("  Late penalty per day: " + settings.getLatePenaltyPerDay());
//            System.out.println("  Auto check enabled: " + settings.isAutoCheckEnabled());
//            System.out.println("  Review timeout (days): " + settings.getReviewTimeoutDays());
//        }
//    }
//}

package ru.nsu.martynov.report;

import ru.nsu.martynov.model.CourseConfig;

public interface ReportGenerator {
    void generate(CourseConfig config);
}
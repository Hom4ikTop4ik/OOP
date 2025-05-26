package ru.nsu.martynov.service;

import ru.nsu.martynov.model.Settings;

import java.io.File;
import java.io.IOException;

public class BuildService {

    private final Settings settings;

    public BuildService(Settings settings) {
        this.settings = settings;
    }

    public boolean build(File repoDir) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    isWindows() ? "gradlew.bat" : "./gradlew",
                    "build",
                    "--no-daemon"
            );
            pb.directory(repoDir);
            pb.inheritIO();
            Process process = pb.start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            System.err.println("Build failed: " + e.getMessage());
            return false;
        }
    }

    public boolean checkStyle(File repoDir) {
        try {
            ProcessBuilder pb;
            if (settings.getCheckstyleConfigFile() != null && !settings.getCheckstyleConfigFile().isBlank()) {
                pb = new ProcessBuilder(
                        isWindows() ? "gradlew.bat" : "./gradlew",
                        "checkstyleMain",
                        "-PcheckstyleConfigPath=" + settings.getCheckstyleConfigFile(),
                        "--no-daemon"
                );
            } else {
                pb = new ProcessBuilder(
                        isWindows() ? "gradlew.bat" : "./gradlew",
                        "checkstyleMain",
                        "--no-daemon"
                );
            }

            pb.directory(repoDir);
            pb.inheritIO();
            Process process = pb.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Checkstyle failed: " + e.getMessage());
            return false;
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}

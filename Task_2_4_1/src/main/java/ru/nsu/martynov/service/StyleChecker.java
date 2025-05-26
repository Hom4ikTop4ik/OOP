////package ru.nsu.martynov.service;
////
////import java.io.File;
////
////public class StyleChecker {
////    public boolean checkStyle(File repoDir) {
////        try {
////            Process process = new ProcessBuilder("gradlew.bat", "checkstyleMain", "--no-daemon")
////                    .directory(repoDir)
////                    .inheritIO()
////                    .start();
////            return process.waitFor() == 0;
////        } catch (Exception e) {
////            System.err.println("Style check failed: " + e.getMessage());
////            return false;
////        }
////    }
////}
//
//package ru.nsu.martynov.service;
//
//import java.io.*;
//import java.util.*;
//
//public class StyleChecker {
//    private final String checkstyleConfigPath;
//
//    public StyleChecker(String checkstyleConfigPath) {
//        if (checkstyleConfigPath == null || checkstyleConfigPath.isEmpty()) {
//            this.checkstyleConfigPath = "lib/google_checks.xml"; // путь по умолчанию
//        } else {
//            this.checkstyleConfigPath = checkstyleConfigPath;
//        }
//    }
//
//    public boolean checkStyle(File repoDir) {
//        try {
//            File checkstyleJar = new File("lib/checkstyle.jar");
//            File configFile = new File(checkstyleConfigPath);
//
//            if (!checkstyleJar.exists()) {
//                System.err.println("    Ошибка: checkstyle.jar не найден по пути " + checkstyleJar.getAbsolutePath());
//                return false;
//            }
//
//            if (!configFile.exists()) {
//                System.err.println("    Ошибка: конфигурационный файл стиля не найден по пути " + configFile.getAbsolutePath());
//                return false;
//            }
//
//            List<String> command = List.of(
//                    "java", "-jar",
//                    checkstyleJar.getAbsolutePath(),
//                    "-c", configFile.getAbsolutePath(),
//                    repoDir.getAbsolutePath()
//            );
//
//            Process process = new ProcessBuilder(command)
//                    .redirectErrorStream(true)
//                    .start();
//
//            String output = new String(process.getInputStream().readAllBytes());
//            int exitCode = process.waitFor();
//
//            if (exitCode == 0) {
//                System.out.println("    Стиль: OK");
//                return true;
//            } else {
//                System.err.println("    Нарушения стиля:\n" + output);
//                return false;
//            }
//
//        } catch (Exception e) {
//            System.err.println("    Ошибка при проверке стиля: " + e.getMessage());
//            return false;
//        }
//    }
//}

package ru.nsu.martynov.service;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.*;
import org.xml.sax.InputSource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class StyleChecker {

    private final String checkstyleConfigPath;

    public StyleChecker(String checkstyleConfigPath) {
        if (checkstyleConfigPath == null || checkstyleConfigPath.isEmpty()) {
            this.checkstyleConfigPath = "lib/google_checks.xml"; // путь по умолчанию
        } else {
            this.checkstyleConfigPath = checkstyleConfigPath;
        }
    }

    public boolean checkStyle(File repoDir) {
        try {
            File configFile = new File(checkstyleConfigPath);
            if (!configFile.exists()) {
                System.err.println("    Ошибка: конфигурационный файл стиля не найден по пути " + configFile.getAbsolutePath());
                return false;
            }

            // Загружаем конфигурацию Checkstyle
            InputSource inputSource = new InputSource(configFile.toURI().toString());
            Configuration config = ConfigurationLoader.loadConfiguration(
                    inputSource,
                    new PropertiesExpander(System.getProperties()),
                    ConfigurationLoader.IgnoredModulesOptions.EXECUTE
            );

            // Создаём Checker
            Checker checker = new Checker();
            checker.setModuleClassLoader(Checker.class.getClassLoader());

            // Добавляем listener для вывода результатов
            checker.addListener(new AuditListener() {
                @Override
                public void auditStarted(AuditEvent event) {}

                @Override
                public void auditFinished(AuditEvent event) {}

                @Override
                public void fileStarted(AuditEvent event) {}

                @Override
                public void fileFinished(AuditEvent event) {}

                @Override
                public void addError(AuditEvent event) {
                    System.err.printf("Ошибка в файле %s (строка %d): %s%n",
                            event.getFileName(),
                            event.getLine(),
                            event.getMessage());
                }

                @Override
                public void addException(AuditEvent event, Throwable throwable) {
                    System.err.printf("Исключение при проверке файла %s: %s%n",
                            event.getFileName(),
                            throwable.getMessage());
                }
            });

            checker.configure(config);

            // Получаем список файлов для проверки — все .java из repoDir рекурсивно
            File[] files = findJavaFiles(repoDir);
            if (files.length == 0) {
                System.out.println("    Нет Java файлов для проверки.");
                checker.destroy();
                return true;
            }

            int errors = checker.process(List.of(files));
            checker.destroy();

            if (errors == 0) {
                System.out.println("    Стиль: OK");
                return true;
            } else {
                System.err.println("    Нарушения стиля: найдено " + errors + " ошибок.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("    Ошибка при проверке стиля: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Помогает найти все .java файлы рекурсивно в директории
    private static File[] findJavaFiles(File dir) {
        return dir.listFiles(file -> {
            if (file.isDirectory()) return true;
            return file.isFile() && file.getName().endsWith(".java");
        });
    }
}

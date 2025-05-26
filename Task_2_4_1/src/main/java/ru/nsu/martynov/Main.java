package ru.nsu.martynov;

import ru.nsu.martynov.loader.ConfigLoader;
import ru.nsu.martynov.model.CourseConfig;
import ru.nsu.martynov.runner.Runner;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String configPath;

        if (args.length == 0) {
            // Используем путь по умолчанию для удобства запуска из IDE
            configPath = "config/config.groovy";
            System.out.println("⚠️  Путь к конфигурации не указан, используется по умолчанию: " + configPath);
        } else if (args.length == 1) {
            configPath = args[0];
        } else {
            System.err.println("Program use only the first parameter. Usage: java -jar app.jar [path/to/config.groovy]");
            return;
        }

        try {
            File configFile = new File(configPath);
            if (!configFile.exists()) {
                System.err.println("Файл не найден: " + configFile.getAbsolutePath());
                System.exit(1);
                return;
            }

            ConfigLoader loader = new ConfigLoader();
            CourseConfig config = loader.loadConfiguration(configFile);

            System.out.println("✅ Конфигурация загружена:");
            System.out.println("  Задач: " + config.getTasks().size());
            System.out.println("  Групп: " + config.getGroups().size());

            Runner runner = new Runner(config);
            runner.execute();

        } catch (Exception e) {
            System.err.println("❌ Ошибка при загрузке конфигурации:");
            e.printStackTrace();
        }
    }
}

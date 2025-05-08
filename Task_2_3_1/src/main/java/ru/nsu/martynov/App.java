package ru.nsu.martynov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загрузка FXML файла для настроек
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settingsView.fxml"));
        Parent root = loader.load();

        // Создание объекта настроек (по умолчанию)
        Settings settings = new Settings();

        // Инициализация контроллера и передача настроек
        SettingsController controller = loader.getController();
        controller.initialize(settings, primaryStage);

        // Устанавливаем сцену и показываем ее
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Настройки игры Змейка");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

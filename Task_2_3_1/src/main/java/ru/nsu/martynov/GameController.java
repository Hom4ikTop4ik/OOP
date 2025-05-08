package ru.nsu.martynov;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import java.io.IOException;
import java.util.Optional;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private GameEngine gameEngine;
    private GameView gameView;
    private SnakeController snakeController;
    private Settings settings;

    public void initialize(Settings settings) {

        this.settings = settings;
        this.gameEngine = new GameEngine(settings);
        this.gameView = new GameView(gameCanvas);

        // Настройка канваса
        gameCanvas.setWidth(settings.getWidth() * settings.getCellSize());
        gameCanvas.setHeight(settings.getHeight() * settings.getCellSize());

        gameView.initialize(settings, gameEngine);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        // Инициализируем SnakeController с текущими змеями и настройками
        snakeController = new SnakeController(gameEngine.getGameMap().getSnakes(), settings);

        // Установка слушателя победы/поражения
        gameEngine.setStatusListener(new GameEngine.GameStatusListener() {
            @Override
            public void onGameOver() {
                Platform.runLater(GameController.this::showGameOver);
            }

            @Override
            public void onVictory() {
                Platform.runLater(GameController.this::showVictory);
            }
        });

        // Обработка клавиш
        gameCanvas.setOnKeyPressed(this::handleKeyPress);

        gameEngine.start(gameView);
        gameView.drawGame();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        snakeController.handleKeyPressed(event, gameEngine);
    }


    private void showGameOver() {
        int maxLength = gameEngine.getGameMap().getMaxLength();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы проиграли!");
        alert.setContentText("Максимальная длина змейки: " + maxLength + "\n\nХотите сыграть ещё раз?");

        ButtonType restart = new ButtonType("Новая игра");
        ButtonType menu = new ButtonType("В меню");
        ButtonType exit = new ButtonType("Выход");

        alert.getButtonTypes().setAll(restart, menu, exit);

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == restart) {
                restartGame();
            } else if (buttonType == menu) {
                returnToMenu(stage);
            } else {
                Platform.exit();
            }
        });
    }

    private void showVictory() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!");
        alert.setHeaderText("Поздравляем!");
        alert.setContentText("Вы достигли длины " + settings.getWinLength() + "\n\nХотите сыграть ещё раз?");

        ButtonType restart = new ButtonType("Новая игра");
        ButtonType menu = new ButtonType("В меню");
        ButtonType exit = new ButtonType("Выход");

        alert.getButtonTypes().setAll(restart, menu, exit);

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == restart) {
                restartGame();
            } else if (buttonType == menu) {
                returnToMenu(stage);
            } else {
                Platform.exit();
            }
        });
    }

    private void restartGame() {
        gameEngine.stop();
        gameEngine = new GameEngine(settings);
        gameView.initialize(settings, gameEngine);

        gameEngine.setStatusListener(new GameEngine.GameStatusListener() {
            @Override
            public void onGameOver() {
                Platform.runLater(GameController.this::showGameOver);
            }

            @Override
            public void onVictory() {
                Platform.runLater(GameController.this::showVictory);
            }
        });

        gameEngine.start(gameView);
        gameCanvas.requestFocus();
    }

    private void returnToMenu(Stage currentStage) {
        try {
            gameEngine.stop();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/settingsView.fxml"));
            Parent root = loader.load();

            SettingsController controller = loader.getController();
            controller.initialize(settings, currentStage);

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Настройки игры Змейка");
        } catch (IOException e) {
            e.printStackTrace();

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Ошибка");
            error.setHeaderText("Не удалось загрузить меню");
            error.setContentText("Попробуйте перезапустить приложение.");
            error.showAndWait();

            Platform.exit();
        }
    }
}

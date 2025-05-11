//package ru.nsu.martynov;
//
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.Socket;
//import java.util.Optional;
//
//public class OnlineController {
//    @FXML
//    private Canvas gameCanvas;
//
//    private GameMap gameMap;
//    private GameView gameView;
//    private SnakeController snakeController;
//    private Settings settings;
//
//    public void initialize(Settings settings, Socket socket) {
//        this.settings = settings;
//        this.gameMap = new GameMap(settings);
//        this.gameView = new GameView(gameCanvas);
//
//        // Настройка канваса
//        gameCanvas.setWidth(settings.getWidth() * settings.getCellSize());
//        gameCanvas.setHeight(settings.getHeight() * settings.getCellSize());
//
//        gameView.initialize(settings, gameMap);
//        gameCanvas.setFocusTraversable(true);
//        gameCanvas.requestFocus();
//
//        // Инициализируем SnakeController с текущими змеями и настройками
//        snakeController = new SnakeController(gameMap.getSnakes());
//
//        // Обработка клавиш
//        gameCanvas.setOnKeyPressed(this::handleKeyPress);
//
//        startNetworkGame(socket, gameView);
//    }
//
//    public void startNetworkGame(Socket socket, GameView gameView) {
//        new Thread(() -> {
//            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
//                while (true) {
//                    GameStateDTO dto = (GameStateDTO) in.readObject(); // получаем сериализованное состояние
//
//                    if (dto.gameOver) {
//                        socket.close();
//                        if (dto.score >= settings.getWinLength()) {
//                            Platform.runLater(showVictory(dto.score));
//                        }
//                        else {
//                            Platform.runLater(showGameOver(dto.score));
//                        }
//                        return;
//                    }
//
//                    Platform.runLater(() -> {
//                        gameView.drawNetworkGame(dto); // рисуем, ты можешь адаптировать drawGame
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Соединение потеряно: " + e.getMessage());
//                Platform.exit(); // вернуться в меню
//            }
//        }).start();
//    }
//
//
//    @FXML
//    private void handleKeyPress(KeyEvent event) {
//        snakeController.handleKeyPressedOnline(event, 0);
//    }
//
//
//    private Runnable showGameOver(int score) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Игра окончена");
//        alert.setHeaderText("Вы проиграли!");
//        alert.setContentText("Максимальная длина змейки: " + score + "\n\nХотите сыграть ещё раз?");
//
//        ButtonType restart = new ButtonType("Новая игра");
//        ButtonType menu = new ButtonType("В меню");
//        ButtonType exit = new ButtonType("Выход");
//
//        alert.getButtonTypes().setAll(restart, menu, exit);
//
//        Stage stage = (Stage) gameCanvas.getScene().getWindow();
//        Optional<ButtonType> result = alert.showAndWait();
//
//        result.ifPresent(buttonType -> {
//            if (buttonType == menu) {
//                returnToMenu(stage);
//            } else {
//                Platform.exit();
//            }
//        });
//        return null;
//    }
//
//    private Runnable showVictory(int score) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Победа!");
//        alert.setHeaderText("Поздравляем!");
//        alert.setContentText("Вы достигли длины " + score + "\n\nХотите сыграть ещё раз?");
//
//        ButtonType menu = new ButtonType("В меню");
//        ButtonType exit = new ButtonType("Выход");
//
//        alert.getButtonTypes().setAll(menu, exit);
//
//        Stage stage = (Stage) gameCanvas.getScene().getWindow();
//        Optional<ButtonType> result = alert.showAndWait();
//
//        result.ifPresent(buttonType -> {
//            if (buttonType == menu) {
//                returnToMenu(stage);
//            } else {
//                Platform.exit();
//            }
//        });
//        return null;
//    }
//
//    private void returnToMenu(Stage currentStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/settingsView.fxml"));
//            Parent root = loader.load();
//
//            SettingsController controller = loader.getController();
//            controller.initialize(settings, currentStage);
//
//            Scene scene = new Scene(root);
//            currentStage.setScene(scene);
//            currentStage.setTitle("Настройки игры Змейка");
//        } catch (IOException e) {
//            e.printStackTrace();
//
//            Alert error = new Alert(Alert.AlertType.ERROR);
//            error.setTitle("Ошибка");
//            error.setHeaderText("Не удалось загрузить меню");
//            error.setContentText("Попробуйте перезапустить приложение.");
//            error.showAndWait();
//
//            Platform.exit();
//        }
//    }
//}

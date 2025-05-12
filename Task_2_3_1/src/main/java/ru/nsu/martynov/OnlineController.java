package ru.nsu.martynov;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class OnlineController {
    @FXML
    private Canvas gameCanvas;

    private GameMap gameMap;
    private GameView gameView;
    private SnakeController snakeController;
    private Settings settings;

    private Socket socket;

    private boolean resize = true;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public void initialize(Settings settings, Socket socket) {
        this.settings = settings;
        this.gameMap = new GameMap(settings);
        this.gameView = new GameView(gameCanvas);

        // Настройка канваса
        gameCanvas.setWidth(settings.getWidth() * settings.getCellSize());
        gameCanvas.setHeight(settings.getHeight() * settings.getCellSize());

        gameView.initialize(settings, gameMap);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        // Инициализируем SnakeController с текущими змеями и настройками
        snakeController = new SnakeController(gameMap.getSnakes());

        // Обработка клавиш
        gameCanvas.setOnKeyPressed(this::handleKeyPress);

        this.socket = socket;

        Platform.runLater(() -> {
            // Проверяем, что сцена существует, прежде чем пытаться получить окно
            if (gameCanvas.getScene() != null) {
                Stage currentStage = (Stage) gameCanvas.getScene().getWindow();
                // Настроим обработчик закрытия окна
                currentStage.setOnCloseRequest(event -> {
                    // Тут вы можете добавить логику для очистки ресурсов и закрытия соединений
                    System.out.println("Закрытие окна...");

                    // Например, закрытие сокета или завершение потока
                    if (socket != null && !socket.isClosed()) {
                        disconnect();
                    }

                    // Здесь можно добавить другие действия, такие как сохранение данных, закрытие соединений и т.д.
                    Platform.exit(); // Закрыть приложение
                });
            } else {
                // Если сцена еще не установлена, можно обработать это иначе
                System.out.println("Сцена еще не установлена");
            }
        });


        GameStateDTO dtoEmpty = new GameStateDTO();
        gameView.drawNetworkGame(dtoEmpty);

        // Закрытие сокета при закрытии окна
//        Stage stage = (Stage) gameCanvas.getScene().getWindow();
//        stage.setOnCloseRequest(event -> {
//            try {
//                if (socket != null && !socket.isClosed()) {
//                    socket.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        startNetworkGame(socket, gameView);
    }

    public void startNetworkGame(Socket socket, GameView gameView) {
        new Thread(() -> {
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Object object = objectInputStream.readObject();
                    if (object instanceof String) {
                        System.out.println((String) object);
                    } else {
                        GameStateDTO dto = (GameStateDTO) object; // получаем сериализованное состояние

                        if (dto.gameOver) {
                            socket.close();
                            if (dto.gameWon) {
                                Platform.runLater(showVictory(dto.score));
                            } else {
                                Platform.runLater(showGameOver(dto.score));
                            }
                            return;
                        }

                        Platform.runLater(() -> {
                            gameView.drawNetworkGame(dto); // рисуем, ты можешь адаптировать drawGame
                        });

//                        if (resize) {
//                            gameCanvas.setWidth(dto.width * settings.getCellSize());
//                            gameCanvas.setHeight(dto.height * settings.getCellSize());
//                            resize = false;
//                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Соединение потеряно: " + e.getMessage());
                Platform.exit(); // вернуться в меню
            } finally {
                disconnect(); // корректное закрытие
                Platform.exit(); // только после disconnect()
            }
        }).start();
    }

    private void disconnect() {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (Exception ignored) {}

        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (Exception ignored) {}

        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception ignored) {}

        System.out.println("Клиент отключился.");
    }



    @FXML
    private void handleKeyPress(KeyEvent event) {
        snakeController.handleKeyPressedOnline(event, 0, objectOutputStream);
    }


    private Runnable showGameOver(int score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы проиграли!");
        alert.setContentText("Максимальная длина змейки: " + score + "\n\nХотите сыграть ещё раз?");

        ButtonType restart = new ButtonType("Новая игра");
        ButtonType menu = new ButtonType("В меню");
        ButtonType exit = new ButtonType("Выход");

        alert.getButtonTypes().setAll(restart, menu, exit);

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == menu) {
                returnToMenu(stage);
            } else {
                Platform.exit();
            }
        });
        return null;
    }

    private Runnable showVictory(int score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!");
        alert.setHeaderText("Поздравляем!");
        alert.setContentText("Вы достигли длины " + score + "\n\nХотите сыграть ещё раз?");

        ButtonType menu = new ButtonType("В меню");
        ButtonType exit = new ButtonType("Выход");

        alert.getButtonTypes().setAll(menu, exit);

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == menu) {
                returnToMenu(stage);
            } else {
                Platform.exit();
            }
        });
        return null;
    }

    private void returnToMenu(Stage currentStage) {
        try {
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

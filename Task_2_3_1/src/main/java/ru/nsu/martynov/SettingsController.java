package ru.nsu.martynov;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SettingsController {
    @FXML
    public TextField snakeCountField;
    @FXML
    public TextField botCountField;
    @FXML
    public TextField botLengthField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField speedField;
    @FXML
    private TextField foodCountField;
    @FXML
    private TextField winLengthField;
    @FXML
    private TextField cellSizeField;
    @FXML
    private TextField configPathField;
    @FXML
    private TextField ipField;

    @FXML
    private Button loadDefaultButton;
    @FXML
    private Button loadConfigButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button startGameButton;

    private Settings settings;
    private Stage primaryStage;

    public void initialize(Settings settings, Stage primaryStage) {
        this.settings = settings;
        this.primaryStage = primaryStage;
        loadSettingsToUI();
    }

    private void loadSettingsToUI() {
        widthField.setText(String.valueOf(settings.getWidth()));
        heightField.setText(String.valueOf(settings.getHeight()));
        foodCountField.setText(String.valueOf(settings.getFoodCount()));
        snakeCountField.setText(String.valueOf(settings.getSnakeCount()));
        winLengthField.setText(String.valueOf(settings.getWinLength()));
        cellSizeField.setText(String.valueOf(settings.getCellSize()));
        speedField.setText(String.valueOf(settings.getMoveInterval()));
        botCountField.setText(String.valueOf(settings.getBotCount()));
        botLengthField.setText(String.valueOf(settings.getBotLength()));
    }

    private void saveSettingsFromUI() {
        try {
            settings.setWidth(Integer.parseInt(widthField.getText()));
            settings.setHeight(Integer.parseInt(heightField.getText()));
            settings.setFoodCount(Integer.parseInt(foodCountField.getText()));
            settings.setSnakeCount(Integer.parseInt(snakeCountField.getText()));
            settings.setWinLength(Integer.parseInt(winLengthField.getText()));
            settings.setCellSize(Integer.parseInt(cellSizeField.getText()));
            settings.setMoveInterval(Integer.parseInt(speedField.getText()));
            settings.setBotCount(Integer.parseInt(botCountField.getText()));
            settings.setBotLen(Integer.parseInt(botLengthField.getText()));
        } catch (NumberFormatException e) {
            showAlert("Ошибка ввода", "Пожалуйста, введите правильные числовые значения.");
        }
    }

    @FXML
    private void onLoadDefaultConfig() {
        settings = new Settings();
        loadSettingsToUI();
    }

    @FXML
    private void onLoadConfigFromFile() {
        String filePath = configPathField.getText();
        if (!filePath.isEmpty()) {
            try {
                settings.loadFromFile(filePath);
                loadSettingsToUI();
            } catch (IOException e) {
                showAlert("Ошибка загрузки файла", "Не удалось загрузить конфигурацию из файла.");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, укажите путь к файлу.");
        }
    }

    @FXML
    private void onSaveConfig() {
        saveSettingsFromUI();
        showAlert("Сохранение", "Конфигурация успешно сохранена!");
    }

    @FXML
    private void onStartGame() throws Exception {
        saveSettingsFromUI();

        // Загрузка игрового интерфейса
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameView.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.initialize(settings);

        Scene gameScene = new Scene(root,
                settings.getWidth() * settings.getCellSize(),
                settings.getHeight() * settings.getCellSize());

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Змейка");
        primaryStage.show();
    }

    @FXML
    private void onServerConnect() throws Exception {
        saveSettingsFromUI();

        String ip = ipField.getText();
        Socket socket = new Socket(ip, 1620);

        sendSettings(ip);
        getSettings(ip);

        // Загрузка игрового интерфейса
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onlineView.fxml"));
        Parent root = loader.load();

        OnlineController onlineController = loader.getController();
        onlineController.initialize(settings, socket);

        Scene gameScene = new Scene(root,
                settings.getWidth() * settings.getCellSize(),
                settings.getHeight() * settings.getCellSize());

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Змейка");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void sendSettings(String ip) {

    }

    private void getSettings(String ip) {

    }
}

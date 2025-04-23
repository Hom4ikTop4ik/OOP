package ru.nsu.martynov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button btn;
    private int x = 0;
    @FXML
    private void click(ActionEvent event) {
        btn.setText(String.format("%d", x++));
    }
}
